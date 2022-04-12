package toy.lms.jwt.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Getter @Setter
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

  @NonNull
  private String accountId;

  @NonNull
  private String password;

  @NonNull
  private String state;

  @NonNull
  private String roleId;

  @NonNull
  private String keyword;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate registerDate;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate updateDate;


  @Builder
  public CustomUserDetails(@NonNull String accountId,
                           @NonNull String password,
                           @NonNull String state,
                           @NonNull String roleId,
                           @NonNull String keyword,
                           LocalDate registerDate, LocalDate updateDate) {
    this.accountId = accountId;
    this.password = password;
    this.state = state;
    this.roleId = roleId;
    this.keyword = keyword;
    this.registerDate = registerDate == null ? LocalDate.now() : registerDate;
    this.updateDate = updateDate == null ? LocalDate.now() : updateDate;
  }

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
