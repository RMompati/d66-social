package com.eroldme.d66.subredit.post

import com.eroldme.d66.subredit.subredit.Subreddit
import com.eroldme.d66.user.User
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.lang.Nullable
import java.time.Instant
import javax.persistence.*
import javax.persistence.FetchType.LAZY
import javax.validation.constraints.NotBlank

/**
 * @author Mompati 'Patco' Keetile
 * @since 06-12-2022 @ 13:57
 *
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
class Post(
    @Id
    @SequenceGenerator(name = "post_id", sequenceName = "post_id", allocationSize = 1)
    @GeneratedValue(generator = "post_id", strategy = GenerationType.IDENTITY)
    private val postId: Long,
    @NotBlank(message = "Post Name cannot be empty or Null.")
    private val postName: String,
    @Nullable
    private val url: String,
    @Nullable
    @Lob
    private val description: String,
    private val voteCount: Int,
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId", referencedColumnName  = "userId")
    private val user: User,
    private val createdDate: Instant,
    @ManyToOne(fetch = LAZY) @JoinColumn(name ="id", referencedColumnName = "id")
    private val subreddit: Subreddit
)