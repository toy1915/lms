package toy.lms.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {
  private static final long serialVersionUID = 8240571880674472156L;

  private static final long JWT_TOKEN_VALIDITY = 1000L * 60L * 60L * 3L; // 3시간

  @Value("${spring.jwt.secret}")
  private String secretKey;

  /**
   * Member 정보를 담은 JWT 토큰을 생성
   * JWT 발급자, 만료, 제목 및 ID와 같은 토큰의 클레임(payload의 한 조각)을 정의
   * @param userDetails
   * @return String JWT Token
   */
  public String generateJwtToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();

    return Jwts.builder()
        .setClaims(claims)                        // 클레임, 토큰에 토함될 정보
        .setHeader(createJwtHeader())             // JWT Header
        .setSubject(userDetails.getUsername())
        .setIssuedAt(
            new Date(System.currentTimeMillis())
        )
        .setExpiration(
            new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY)
        )
        .signWith(getSignKey(), SignatureAlgorithm.HS512)
        .compact(); // 직렬화 (JWT 압축)
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
        .setSigningKey(getSignKey())
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

  /**
   * JWT 토큰 암호화
   * @return
   */
  private Key getSignKey(){
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

}
