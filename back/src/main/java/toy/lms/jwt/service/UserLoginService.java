package toy.lms.jwt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.lms.common.constants.ResultMap;
import toy.lms.jwt.JwtTokenUtil;
import toy.lms.jwt.dto.UserInfoDTO;
import toy.lms.jwt.mapper.UserLoginMapper;
import toy.lms.member.dto.MemberDTO;

@Service
public class UserLoginService {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private UserLoginMapper userLoginMapper;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;


  public ResultMap findByUser(String userId, String userPw) {
    ResultMap resultMap = new ResultMap();

    try {
      final UserDetails userDetails = userLoginMapper.selectUserById(userId);
      LOGGER.info(">> " + userPw + ", " + userDetails.getPassword());

      if (!bCryptPasswordEncoder.matches(userPw, userDetails.getPassword())) {

        LOGGER.info(">> " + bCryptPasswordEncoder.matches(userDetails.getPassword(), userPw));
        LOGGER.info(">> " + bCryptPasswordEncoder.matches(userPw, userDetails.getPassword()));

        resultMap.setFailure();
        resultMap.setMessage("비밀번호가 일치하지 않습니다.");

        return resultMap;
      }

      final String token = jwtTokenUtil.generateJwtToken(userDetails);
      authenticate(userId, userPw);

      resultMap.setSuccess();
      resultMap.setMessage(token);
    } catch (Exception e) {
      resultMap.setFailure();
      resultMap.setMessage("로그인 진행 중 문제가 발생하였습니다.");
    }

    return resultMap;
  }


  public ResultMap modifyUser(MemberDTO newInfo) {
    ResultMap resultMap = new ResultMap();

    try {
      userLoginMapper.updateUserInfo(newInfo);

      resultMap.setSuccess();
      resultMap.setMessage("회원정보 변경 성공.");
    } catch(Exception e) {
      resultMap.setFailure();
      resultMap.setMessage("회원정보 변경 중 문제가 발생하였습니다.");
    }

    return resultMap;
  }


  @Transactional
  public ResultMap registerUser(UserInfoDTO userInfoDTO) {
    ResultMap resultMap = new ResultMap();

    try {
      if (userLoginMapper.selectCntDuplicateId(userInfoDTO.getAccountId()) > 0) {
        resultMap.setFailure();
        resultMap.setMessage("이미 존재하는 ID 입니다.");
        return resultMap;
      }

      LOGGER.info(userInfoDTO.toString());
      userInfoDTO.setPassword(bCryptPasswordEncoder.encode(userInfoDTO.getPassword()));
      userLoginMapper.insertUserInfo(userInfoDTO);
      resultMap.setSuccess();
      resultMap.setMessage("가입이 완료되었습니다.");

    } catch (Exception e) {
      resultMap.setFailure();
      resultMap.setMessage(e.getMessage());
    }

    return resultMap;
  }


  private void authenticate(String username, String password) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password)
      );
    } catch (DisabledException e) {
      LOGGER.info("USER_DISABLED ", e);
    } catch (BadCredentialsException e) {
      LOGGER.info("INVALID_CREDENTIALS ", e);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
