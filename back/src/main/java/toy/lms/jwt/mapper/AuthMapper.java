package toy.lms.jwt.mapper;

import org.apache.ibatis.annotations.Mapper;
import toy.lms.jwt.dto.CustomUserDetails;
import toy.lms.jwt.dto.UserInfoDTO;
import toy.lms.member.dto.MemberDTO;


@Mapper
public interface AuthMapper {

  CustomUserDetails selectUserById(String accountId);

  int insertUserInfo(UserInfoDTO userInfoDTO);

  int selectCntDuplicateId(String accountId);

  int updateUserInfo(MemberDTO memberDTO);

}
