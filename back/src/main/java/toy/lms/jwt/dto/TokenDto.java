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
public class TokenDto implements Serializable {
  private String accessToken;
  private Long accessTokenExpiresIn;
}
