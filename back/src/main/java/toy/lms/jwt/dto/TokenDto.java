package toy.lms.jwt.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TokenDto {
  private String accessToken;
  private String refreshToken;
  private Long accessTokenExpiresIn;
}
