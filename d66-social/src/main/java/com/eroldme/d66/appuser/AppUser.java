package com.eroldme.d66.appuser;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

/**
 * @author Mompati 'Patco' Keetile
 * @created 19-12-2022 @ 11:54
 */
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder(builderMethodName = "NewUser")
@Entity
public class AppUser {
  @Id
  @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
  @GeneratedValue(generator = "user_id_seq", strategy = GenerationType.IDENTITY)
  private Long userId;
  @NotBlank(message = "Username is required.")
  private String username;
  @NotBlank(message = "Password is required.")
  private String password;
  @Email
  @NotBlank(message = "Email is required.")
  private String email;
  private Instant createdAt;
  private Boolean enabled;
}
