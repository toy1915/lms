package toy.lms.jwt.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import toy.lms.common.constants.ResultMap;
import toy.lms.jwt.dto.LoginRequestDto;
import toy.lms.jwt.service.AuthService;

import javax.validation.Valid;


@Api(tags = {"로그인 및 회원가입"})
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/unknown")
public class AuthController {
  private final AuthService authService;

  private final BCryptPasswordEncoder bc;

  @PostMapping("/login")
  @Operation(summary = "사용자 로그인", description = "사용자 계정으로 로그인 합니다.")
  public ResultMap login(
          @ApiParam(value = "로그인 객체") @Valid @RequestBody LoginRequestDto requestDto
  ) {
    log.info("{}", requestDto);
    ResultMap result = new ResultMap();
    try{
      authService.login(requestDto, result);
      result.setSuccess();
    } catch (Exception e){
      e.printStackTrace();
      result.setFailure();
    }

    return result;
  }

//  @Operation(summary = "사용자 로그인", description = "사용자 계정으로 로그인 합니다.")
//  @PostMapping("/update")
//  public ResultMap updateUserInfomation(@RequestBody MemberDTO memberDTO) {
//    return userLoginService.modifyUser(memberDTO);
//  }
//
//  @ApiOperation(value = "신규 회원 가입", notes = "사용자 정보 입력 필요.")
//  @PostMapping("/signup")
//  public ResultMap newMember(@RequestBody UserInfoDTO userInfoDTO) {
//    return userLoginService.registerUser(userInfoDTO);
//  }
}
