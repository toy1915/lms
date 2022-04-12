package toy.lms.jwt.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.userdetails.UserDetails;
import toy.lms.jwt.dto.UserInfoDTO;
import toy.lms.member.dto.MemberDTO;


@Mapper
public interface UserLoginMapper {

  UserDetails selectUserById(String accountId);

  int insertUserInfo(UserInfoDTO userInfoDTO);

  int selectCntDuplicateId(String accountId);

  int updateUserInfo(MemberDTO memberDTO);

}
