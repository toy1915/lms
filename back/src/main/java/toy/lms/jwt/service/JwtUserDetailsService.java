package toy.lms.jwt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import toy.lms.jwt.dto.CustomUserDetails;
import toy.lms.jwt.mapper.AuthMapper;


@Service
@Slf4j
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

  private final AuthMapper authMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("### LoadUserByUsername ###");

    CustomUserDetails user = authMapper.selectUserById(username);

    if (user == null) {
      throw new UsernameNotFoundException("username " + username + " not found");
    }

    return user;
  }

}
