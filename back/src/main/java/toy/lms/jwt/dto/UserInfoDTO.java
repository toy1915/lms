package toy.lms.jwt.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {

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

  @ApiModelProperty(value = "분류?", example = "")
  private String keyword;

  @NonNull
  @ApiModelProperty(value = "계정 생성일자", example = "2022-01-01 11:10:09")
  @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
  private LocalDateTime registerDate ;

  @ApiModelProperty(value = "계정 생성일자", example = "2022-01-01 11:10:09")
  @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
  private LocalDateTime updateDate;

  @ApiModelProperty(value = "사용자 명", example = "홍길동", required = true)
  @NonNull
  private String nameK;

  @ApiModelProperty(value = "", example = "")
  private String nameE;

  @ApiModelProperty(value = "", example = "")
  @NonNull
  private String sex;

  @ApiModelProperty(value = "남성 (M), 여성 (F)", example = "M")
  @NonNull
  private String email;

  @ApiModelProperty(value = "수업명", example = "컴퓨터 공학")
  @NonNull
  private String education;

  @ApiModelProperty(value = "전화번호", example = "010-1231-4267")
  @NonNull
  private String telNum;

  @ApiModelProperty(value = "생년월일", example = "1997-11-11")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate birth;

  @ApiModelProperty(value = "이미지", example = "")
  private String image;
}
