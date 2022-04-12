package toy.lms.member.dto;

import lombok.*;

import java.time.LocalDateTime;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {

  @NonNull
  private String accountId;

  @NonNull
  private String nameK;

  private String nameE;

  @NonNull
  private char sex;

  @NonNull
  private String email;

  @NonNull
  private String education;

  @NonNull
  private String telNum;

  private LocalDateTime date;

  private String image;
}
