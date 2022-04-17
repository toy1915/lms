package toy.lms.jwt.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@ToString
public class CustomUserDetails implements UserDetails {
  private String accountId;
  private String password;
  private String nameK;
  private String roleId;
  private String roleName;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Set<GrantedAuthority> roles = new HashSet<>();
    for (String role : roleId.split(",")) {
      roles.add(new SimpleGrantedAuthority(role));
    }
    return roles;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.accountId;
  }

  // 계정 만료 여부
  @Override
  public boolean isAccountNonExpired() {
    return true; // true -> 만료되지 않았음
  }

  // 계정 잠금 여부
  @Override
  public boolean isAccountNonLocked() {
    return true; // true -> 잠금되지 않았음
  }

  // 패스워드의 만료 여부
  @Override
  public boolean isCredentialsNonExpired() {
    return true; // true -> 만료되지 않았음
  }

  // 계정 사용 가능 여부
  @Override
  public boolean isEnabled() {
    return true; // true -> 사용 가능
  }
}
