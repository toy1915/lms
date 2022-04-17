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
import toy.lms.common.exception.JwtException;
import toy.lms.jwt.dto.TokenDto;
import toy.lms.jwt.dto.TokenRequestDto;
import toy.lms.jwt.handler.TokenUtil;
import toy.lms.jwt.dto.LoginRequestDto;
import toy.lms.jwt.dto.UserInfoDto;
import toy.lms.jwt.mapper.AuthMapper;
import toy.lms.member.dto.MemberDTO;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
  private final AuthMapper authMapper;
  private final TokenUtil jwtTokenUtil;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public TokenDto login(LoginRequestDto loginRequestDto) {
    // Login ID/PW를 기반으로 AuthenticationToken 생성
    UsernamePasswordAuthenticationToken authenticationToken = loginRequestDto.toAuthentication();

    // 실제로 검증 (사용자 비밀번호 체크)
    // JwtUserDetailsService 의 loadUserByUsername 메서드가 실행
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

    // 인증 정보를 기반으로 JWT 토큰 생성
    return jwtTokenUtil.generateToken(authentication);
  }


  public TokenDto reissue(TokenRequestDto tokenRequestDto){
    Map<String, Object> result = new HashMap<>();

    if (jwtTokenUtil.validateToken(tokenRequestDto.getRefreshToken()))
      throw new JwtException("Refresh Token 이 유효하지 않습니다.");

    Authentication authentication = jwtTokenUtil.getAuthentication(tokenRequestDto.getAccessToken());

    return jwtTokenUtil.generateToken(authentication);
  }


  public String validateDuplicateId(String accountId) {
    if (authMapper.selectCntDuplicateId(accountId) == 0)
      return "사용 가능한 ID 입니다.";
    else
      return "이미 존재하는 회원입니다.";
  }


  public void registerUser(UserInfoDto userInfo) {
    userInfo.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
    authMapper.insertUserInfo(userInfo);
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
