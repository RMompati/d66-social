package com.eroldme.d66.user

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

/**
 * @author Mompati 'Patco' Keetile
 * @created 06-12-2022 @ 13:54
 *
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
class User(
    @Id
    @SequenceGenerator(name = "user_id", sequenceName = "post_id", allocationSize = 1)
    @GeneratedValue(generator = "user_id", strategy = GenerationType.IDENTITY)
    private val userId: Long,
    @NotBlank(message = "Username is required")
    private val username: String,
    @NotBlank(message = "Password is required")
    private val password: String,
    @Email
    @NotBlank(message = "Email is required")
    private val email: String,
    private val created: Instant,
    private val enabled: Boolean
)