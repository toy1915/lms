package toy.lms.jwt;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import toy.lms.jwt.handler.TokenUtil;

import java.time.LocalDateTime;

@DisplayName("JwtTokenUtil Test")
public class TokenUtilTest {

  private TokenUtil jwtTokenUtil;

  @BeforeEach
  void setUp() {
//    jwtTokenUtil = new TokenUtil();
  }

  @Test
  @DisplayName("토큰 생성 및 복호화 테스트")
  void tokenTest() {
    LocalDateTime now = LocalDateTime.now();


//    final UserDetails user =
//        UserDetails.builder()
  }
}
