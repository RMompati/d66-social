package com.eroldmr.d66.appuser;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

/**
 * @author Mompati 'Patco' Keetile
 * @created 19-12-2022 @ 11:54
 */
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder(builderMethodName = "NewUser")
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "unique_username", columnNames = "username"),
        @UniqueConstraint(name = "unique_email", columnNames = "email"),
})
public class AppUser implements UserDetails {
  @Id
  @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
  @GeneratedValue(generator = "user_id_seq", strategy = GenerationType.IDENTITY)
  private Long userId;
  @NotBlank(message = "First name is required.")
  private String firstName;
  @NotBlank(message = "Last name is required.")
  private String lastName;
  @NotBlank(message = "Password is required.")
  private String password;
  @NotBlank(message = "Username is required.")
  private String username;
  @Email
  @NotBlank(message = "Email is required.")
  private String email;
  private Instant createdAt;
  private Boolean enabled;
  private Boolean locked;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Set.of(new SimpleGrantedAuthority("USER"));
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !locked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }
}
