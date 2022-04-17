package toy.lms.jwt.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toy.lms.jwt.dto.LoginRequestDto;
import toy.lms.jwt.dto.TokenDto;

@SpringBootTest
public class AuthServiceTest {

  @Autowired
  private AuthService authService;

  @Test
  void login() {
    LoginRequestDto info = new LoginRequestDto();
    info.setPassword("abc1234!");
    info.setAccountId("admin3");

    TokenDto token = authService.login(info);
    System.out.println(token.getAccessToken());
  }

}
