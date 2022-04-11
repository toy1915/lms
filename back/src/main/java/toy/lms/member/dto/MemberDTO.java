package toy.lms.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

  @NonNull
  private String accountId;

  @NonNull
  private String password;

  @NonNull
  private String state;

  @NonNull
  private String roleId;

  @NonNull
  private String keyword;

  @NonNull
  @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
  private LocalDateTime registerDate ;

  @NonNull
  @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
  private LocalDateTime updateDate;

}
