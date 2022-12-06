package com.eroldme.d66.user.register.token

import com.eroldme.d66.user.User
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import java.time.Instant
import javax.persistence.*
import javax.persistence.FetchType.LAZY

/**
 * @author Mompati 'Patco' Keetile
 * @since 06-12-2022 @ 14:11
 *
 *
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "token")
class VerificationToken(
    @Id
    @SequenceGenerator(name = "token_id", sequenceName = "post_id", allocationSize = 1)
    @GeneratedValue(generator = "token_id", strategy = GenerationType.IDENTITY)
    private val id: Long,
    private val token: String,
    @OneToMany(fetch = LAZY)
    private val user: User,
    private val expiryDate: Instant
)