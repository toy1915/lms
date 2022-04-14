package toy.lms.jwt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.lms.common.constants.ResultMap;
import toy.lms.jwt.handler.TokenUtil;
import toy.lms.jwt.dto.LoginRequestDto;
import toy.lms.jwt.dto.UserInfoDTO;
import toy.lms.jwt.mapper.AuthMapper;
import toy.lms.member.dto.MemberDTO;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
  private final AuthMapper authMapper;
  private final TokenUtil jwtTokenUtil;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public ResultMap login(LoginRequestDto loginRequestDto, ResultMap result) {
    // Login ID/PW를 기반으로 AuthenticationToken 생성
    UsernamePasswordAuthenticationToken authenticationToken = loginRequestDto.toAuthentication();

    // 실제로 검증 (사용자 비밀번호 체크)
    // JwtUserDetailsService 의 loadUserByUsername 메서드가 실행
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

    // 인증 정보를 기반으로 JWT 토큰 생성
    result.put("token", jwtTokenUtil.generateToken(authentication));

    return result;
  }


  public ResultMap modifyUser(MemberDTO newInfo) {
    ResultMap resultMap = new ResultMap();

    try {
      authMapper.updateUserInfo(newInfo);

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
      if (authMapper.selectCntDuplicateId(userInfoDTO.getAccountId()) > 0) {
        resultMap.setFailure();
        resultMap.setMessage("이미 존재하는 ID 입니다.");
        return resultMap;
      }

      log.info(userInfoDTO.toString());
      userInfoDTO.setPassword(bCryptPasswordEncoder.encode(userInfoDTO.getPassword()));
      authMapper.insertUserInfo(userInfoDTO);
      resultMap.setSuccess();
      resultMap.setMessage("가입이 완료되었습니다.");

    } catch (Exception e) {
      resultMap.setFailure();
      resultMap.setMessage(e.getMessage());
    }

    return resultMap;
  }


//  private void authenticate(String username, String password) {
//    try {
//      authMapper.authenticate(
//          new UsernamePasswordAuthenticationToken(username, password)
//      );
//    } catch (DisabledException e) {
//      log.info("USER_DISABLED ", e);
//    } catch (BadCredentialsException e) {
//      log.info("INVALID_CREDENTIALS ", e);
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  }

}
