package toy.lms.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toy.lms.common.constants.ResultMap;
import toy.lms.member.entity.Profile;
import toy.lms.member.repository.MemberRepository;


@RestController
@RequestMapping("/member")
public class MemberController {

  @Autowired
  private MemberRepository memberRepository;

  @GetMapping("/list")
  public ResultMap testMethod() {
    ResultMap result = new ResultMap();

    try {
      result.setList(memberRepository.findAll());
      result.setSuccess();
    } catch (Exception e) {
      e.printStackTrace();
      result.setFailure();
    }

    return result;
  }

}
