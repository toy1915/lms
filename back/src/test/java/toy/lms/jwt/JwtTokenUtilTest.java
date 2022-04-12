package toy.lms.jwt;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import toy.lms.jwt.dto.CustomUserDetails;

import java.time.LocalDateTime;

@DisplayName("JwtTokenUtil Test")
public class JwtTokenUtilTest {

  private JwtTokenUtil jwtTokenUtil;

  @BeforeEach
  void setUp() {
    jwtTokenUtil = new JwtTokenUtil();
  }

  @Test
  @DisplayName("토큰 생성 및 복호화 테스트")
  void tokenTest() {
    LocalDateTime now = LocalDateTime.now();


//    final UserDetails user =
//        UserDetails.builder()
  }
}
