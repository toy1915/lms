package toy.lms.jwt.handler;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import toy.lms.jwt.dto.TokenDto;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class TokenUtil{
  private static final String AUTHORITIES_KEY = "auth";
  private static final String USER_DATA_KEY = "info";
  private static final long ACCESS_TOKEN_VALIDITY = 1000 * 60 * 60 * 3; // 3시간

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


    return TokenDto.builder()
            .accessToken(accessToken)
            .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
            .build();
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
   * @param userDetails
   * @return Boolean
   */
  public Boolean validateToken(String token, UserDetails userDetails) {
    final String userName = getClaimFromToken(token, Claims::getSubject);
    return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
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
