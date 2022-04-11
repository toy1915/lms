package toy.lms.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequestDTO implements Serializable {
  private static final long serialVersionUID = 8931243825062361766L;

  private String username;
  private String password;
}
