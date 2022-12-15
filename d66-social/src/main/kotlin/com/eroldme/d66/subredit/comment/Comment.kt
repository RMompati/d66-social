package com.eroldme.d66.subredit.comment

import com.eroldme.d66.subredit.post.Post
import com.eroldme.d66.user.User
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.NotEmpty

/**
 * @author Mompati 'Patco' Keetile
 * @created 06-12-2022 @ 13:57
 *
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
class Comment(
    @Id
    @SequenceGenerator(name = "comment_id", sequenceName = "post_id", allocationSize = 1)
    @GeneratedValue(generator = "comment_id", strategy = GenerationType.IDENTITY)
    private val id: Long,
    @NotEmpty
    private val text: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    private val post: Post,
    private val createdDate: Instant,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private val user: User
)