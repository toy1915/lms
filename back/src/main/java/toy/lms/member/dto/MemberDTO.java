package toy.lms.member.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

  @ApiModelProperty(value = "사용자 ID", example = "admin", required = true)
  @NonNull
  private String accountId;

  @ApiModelProperty(value = "사용자 Password", example = "admin123$", required = true)
  @NonNull
  private String password;

  @ApiModelProperty(value = "대기, 수강,수료, 탈퇴", example = "대기", required = true)
  @NonNull
  private String state;

  @ApiModelProperty(value = "1: 관리자, 2: 강사, 3: 매니저, 4: 학생", example = "1", required = true)
  @NonNull
  private String roleId;

  @ApiModelProperty(value = "분류?", example = "", required = false)
  private String keyword;

  @NonNull
  @ApiModelProperty(value = "계정 생성일자", example = "2022-01-01 11:10:09")
  @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
  private LocalDateTime registerDate ;

  @ApiModelProperty(value = "계정 생성일자", example = "2022-01-01 11:10:09")
  @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
  private LocalDateTime updateDate;

}
