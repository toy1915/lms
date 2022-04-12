package toy.lms.jwt.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import toy.lms.jwt.mapper.UserLoginMapper;


@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  private final UserLoginMapper userLoginMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    LOGGER.info("### LoadUserByUsername ###");

    UserDetails user = userLoginMapper.selectUserById(username);

    if (user == null) {
      throw new UsernameNotFoundException("username " + username + " not found");
    }

    return user;
  }

}
