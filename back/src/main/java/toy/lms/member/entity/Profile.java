package toy.lms.member.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Profile {

  @Id
  private String accountId;
  private String nameK;
  private String nameE;
  private String sex;
  private String email;
  private String education;
  private String telNum;
  private String birth;
  private String image;

}
