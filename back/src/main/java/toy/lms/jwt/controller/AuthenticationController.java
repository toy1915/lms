package toy.lms.jwt.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import toy.lms.common.constants.ResultMap;
import toy.lms.jwt.dto.JwtRequestDTO;
import toy.lms.jwt.dto.UserInfoDTO;
import toy.lms.jwt.service.UserLoginService;
import toy.lms.member.dto.MemberDTO;

import java.lang.reflect.Member;


@Api(tags = {"로그인 및 회원가입"})
@RestController
@RequestMapping("/unknown")
public class AuthenticationController {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


  @Autowired
  private UserLoginService userLoginService;

  @Autowired
  private BCryptPasswordEncoder bc;

  @ApiOperation(value = "사용자 로그인", notes = "아아디, 패스워드 필요.")
  @PostMapping("/login")
  public ResultMap createAuthenticationToken(@RequestBody JwtRequestDTO authRequest) {
    return userLoginService.findByUser(authRequest.getUsername(), authRequest.getPassword());
  }

  @ApiOperation(value = "사용자 정보 수정", notes = "사용자 정보 필요.")
  @PostMapping("/update")
  public ResultMap updateUserInfomation(@RequestBody MemberDTO memberDTO) {
    return userLoginService.modifyUser(memberDTO);
  }

  @ApiOperation(value = "신규 회원 가입", notes = "사용자 정보 입력 필요.")
  @PostMapping("/signup")
  public ResultMap newMember(@RequestBody UserInfoDTO userInfoDTO) {
    return userLoginService.registerUser(userInfoDTO);
  }


}
