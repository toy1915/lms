package toy.lms.jwt.handler;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import toy.lms.common.exception.JwtException;
import toy.lms.jwt.dto.CustomUserDetails;
import toy.lms.jwt.dto.TokenDto;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TokenUtil{
  private static final String AUTHORITIES_KEY = "auth";
  private static final String USER_DATA_KEY = "info";
  private static final long ACCESS_TOKEN_VALIDITY = 1000 * 60 * 60 * 3; // 3시간
  private static final long REFRESH_TOKEN_VALIDITY = 1000 * 60 * 60 * 24 * 7; // 7일

  private final Key key;
  public TokenUtil(@Value("${spring.jwt.secret}") String secretKey){
    byte[] bytes = Decoders.BASE64.decode(secretKey);
    this.key = Keys.hmacShaKeyFor(bytes);
  }

  /**
   * Member 정보를 담은 JWT 토큰을 생성
   * JWT 발급자, 만료, 제목 및 ID와 같은 토큰의 클레임(payload의 한 조각)을 정의
   * @param authentication
   * @return TokenDto
   */
  public TokenDto generateToken(Authentication authentication) {
    long now = (new Date()).getTime();

    Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_VALIDITY);
    String accessToken = Jwts.builder()
                        .claim(AUTHORITIES_KEY, authentication)
                        .claim(USER_DATA_KEY, authentication.getPrincipal())
                        .setSubject(authentication.getName())
                        .setExpiration(accessTokenExpiresIn)
                        .signWith(key, SignatureAlgorithm.HS512)
                        .compact(); // 직렬화 (JWT 압축)

    Date refreshTokenExpiresIn = new Date(now + REFRESH_TOKEN_VALIDITY);
    String refreshToken = Jwts.builder()
                         .signWith(key, SignatureAlgorithm.HS512)
                         .setExpiration(refreshTokenExpiresIn)
                         .compact();

    return TokenDto.builder()
                   .accessToken(accessToken)
                   .refreshToken(refreshToken)
                   .build();
  }

  // 토큰 복호화
  public Authentication getAuthentication(String accessToken){
    Claims claims = parseClaims(accessToken);
    if(claims.get(AUTHORITIES_KEY) == null){
      throw new JwtException("권한 정보가 없는 토큰입니다.");
    }

    // 클레임에서 권한 정보 가져오기
    Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString()
                                                               .split(","))
                                                               .map(SimpleGrantedAuthority::new)
                                                               .collect(Collectors.toList());

    Map<String, Object> info = (Map<String, Object>) claims.get("info");
    CustomUserDetails principal = new CustomUserDetails();
    principal.setRoleName((String) info.get("roleName"));
    principal.setNameK((String) info.get("nameK"));
    principal.setAccountId((String) info.get("accountId"));

    return new UsernamePasswordAuthenticationToken(principal, "", authorities);
  }

  private Claims parseClaims(String accessToken) {
    try {
      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }

  /**
   * JWT Token에서 username 획득.
   * @param token
   * @return String user Id
   */
  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  /**
   * JWT Token 만료 여부
   * @param token
   * @return Boolean
   */
  public Boolean validateToken(String token) {
//    final String userName = getClaimFromToken(token, Claims::getSubject);
//    return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
      log.info("잘못된 JWT 서명입니다.");
    } catch (ExpiredJwtException e) {
      log.info("만료된 JWT 토큰입니다.");
    } catch (UnsupportedJwtException e) {
      log.info("지원되지 않는 JWT 토큰입니다.");
    } catch (IllegalArgumentException e) {
      log.info("JWT 토큰이 잘못되었습니다.");
    }
    return false;
  }

  /**
   * JWT Header 설정, HS512 사용
   * @return
   */
  private Map<String, Object> createJwtHeader() {
    Map<String, Object> header = new HashMap<String, Object>();
    header.put("typ", "JWT");
    header.put("alg", "HS512");
    header.put("regDate", System.currentTimeMillis());
    return header;
  }

  /**
   * JWT 토큰 권한 검사
   * @param token
   * @param claimsResolver
   * @param <T>
   * @return
   */
  private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();
    return claimsResolver.apply(claims);
  }

  /**
   * JWT 토큰 날짜 만료 여부
   * @param token
   * @return
   */
  private Boolean isTokenExpired(String token) {
    final Date expiration = getClaimFromToken(token, Claims::getExpiration);
    return expiration.before(new Date());
  }

// ----------------- 생성자로 이동
//  /**
//   * JWT 토큰 암호화
//   * @return
//   */
//  private Key getSignKey(){
//    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//    return Keys.hmacShaKeyFor(keyBytes);
//  }

}
