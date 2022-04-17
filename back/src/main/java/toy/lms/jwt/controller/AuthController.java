package toy.lms.jwt.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import toy.lms.common.constants.ResultMap;
import toy.lms.jwt.dto.LoginRequestDto;
import toy.lms.jwt.dto.TokenRequestDto;
import toy.lms.jwt.dto.UserInfoDto;
import toy.lms.jwt.dto.ValidateIdDto;
import toy.lms.jwt.service.AuthService;
import toy.lms.member.dto.MemberDTO;

import javax.validation.Valid;
import javax.xml.transform.Result;


@Api(tags = {"로그인 및 회원가입"})
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {
  private final AuthService authService;

  private final BCryptPasswordEncoder bc;


  @PostMapping("/login")
  @Operation(summary = "사용자 로그인", description = "사용자 계정으로 로그인 합니다.")
  public ResultMap login(
          @ApiParam(value = "로그인 객체") @Valid @RequestBody LoginRequestDto requestDto
  ) {
    ResultMap result = new ResultMap();
    try{
      result.setData(authService.login(requestDto));
      result.setSuccess();
    } catch (Exception e){
      e.printStackTrace();
      result.setFailure();
    }

    return result;
  }

  @PostMapping("/reissue")
  @Operation(summary = "accessToken 재요청", description = "accessToken 만료 시 refreshToken 으로 accessToken 재요청")
  public ResultMap reissue(
          @ApiParam(value = "토큰 객체") @Valid @RequestBody TokenRequestDto requestDto
  ) {
    ResultMap result = new ResultMap();
    try{
      result.setData(authService.reissue(requestDto));
      result.setSuccess();
    } catch (Exception e){
      e.printStackTrace();
      result.setFailure();
    }

    return result;
  }

  @ApiOperation(value = "ID 중복 확인", notes = "사용자 회원 가입 중 ID 중복 체크 시 사용.")
  @PostMapping("/validate/id")
  public ResultMap validateId(
        @ApiParam(value = "사용자 ID") @RequestBody ValidateIdDto validateId
  ) {
    ResultMap result = new ResultMap();
    try {
      result.setMessage(authService.validateDuplicateId(validateId.getAccountId()));
      result.setSuccess();
    } catch (Exception e) {
      e.printStackTrace();
      result.setFailure();
    }

    return result;
  }

  @ApiOperation(value = "신규 회원 가입", notes = "사용자 정보 입력 필요.")
  @PostMapping("/signup")
  public ResultMap newMember(
      @RequestBody UserInfoDto userInfo
  ) {
    ResultMap result = new ResultMap();
    try {
      authService.registerUser(userInfo);
      result.setSuccess();
      result.setMessage("가입이 완료되었습니다.");
    } catch (Exception e) {
      e.printStackTrace();
      result.setFailure();
    }

    return result;
  }



}
