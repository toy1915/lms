package toy.lms.member.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toy.lms.member.entity.Profile;

import java.util.List;

@SpringBootTest
public class MemberRepositoryTest {

  @Autowired
  private MemberRepository memberRepository;

  @Test
  void printMemberList() {
    List<Profile> member = memberRepository.findAll();
    System.out.println("print Member List");
    System.out.println(member.size());
    member.forEach(System.out::println);
  }

}
