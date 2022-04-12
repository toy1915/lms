package toy.lms.jwt.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class JwtResponseDTO implements Serializable {
  private static final long serialVersionUID = 8518385568450475362L;

  private String jwtToken;
}
