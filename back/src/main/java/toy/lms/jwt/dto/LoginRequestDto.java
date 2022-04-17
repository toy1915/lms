package toy.lms.jwt.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

  @ApiParam(value = "아이디", required = true)
  @ApiModelProperty(example = "아이디")
  @NotNull(message = "아이디를 입력해주세요.")
  private String accountId;

  @ApiParam(value = "비밀번호", required = true)
  @ApiModelProperty(example = "비밀번호")
  @NotNull(message = "비밀번호를 입력해주세요.")
  private String password;

  public UsernamePasswordAuthenticationToken toAuthentication(){
    return new UsernamePasswordAuthenticationToken(accountId, password);
  }
}
