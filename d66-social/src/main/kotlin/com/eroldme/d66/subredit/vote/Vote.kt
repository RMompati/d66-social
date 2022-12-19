package com.eroldme.d66.subredit.vote

import com.eroldme.d66.subredit.post.Post
import com.eroldme.d66.subredit.vote.constant.VoteType
import com.eroldme.d66.user.ApplicationUser
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import javax.persistence.*
import javax.persistence.FetchType.LAZY

/**
 * @author Mompati 'Patco' Keetile
 * @created 06-12-2022 @ 14:10
 *
 *
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
class Vote(
    @Id
    @SequenceGenerator(name = "vote_id_seq", sequenceName = "vote_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "vote_id_seq", strategy = GenerationType.IDENTITY)
    private val voteId: Long,
    private val voteType: VoteType,
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    private val post: Post,
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private val applicationUser: ApplicationUser
)