package com.eroldme.d66.user.register.token

import com.eroldme.d66.user.ApplicationUser
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import java.time.Instant
import javax.persistence.*
import javax.persistence.FetchType.LAZY

/**
 * @author Mompati 'Patco' Keetile
 * @created 06-12-2022 @ 14:11
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
    @SequenceGenerator(name = "token_id_seq", sequenceName = "token_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "token_id_seq", strategy = GenerationType.IDENTITY)
    private val id: Long,
    private val token: String,
    @ManyToOne(fetch = LAZY)
    private val applicationUser: ApplicationUser,
    private val expiryDate: Instant
)