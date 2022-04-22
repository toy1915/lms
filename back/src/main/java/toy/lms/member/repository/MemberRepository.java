package toy.lms.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.lms.member.entity.Profile;


public interface MemberRepository extends JpaRepository<Profile, String> {

}
