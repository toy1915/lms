package toy.lms.jwt.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.userdetails.UserDetails;
import toy.lms.member.dto.MemberDTO;


@Mapper
public interface UserLoginMapper {

  UserDetails selectUserById(String accountId);

  int insertUserInfo(MemberDTO memberDTO);

  int selectCntDuplicateId(String accountId);

  int updateUserInfo(MemberDTO memberDTO);

}
