package com.eroldme.d66.subredit.subredit

import com.eroldme.d66.subredit.post.Post
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import java.time.Instant
import javax.persistence.*
import javax.persistence.FetchType.LAZY
import javax.validation.constraints.NotBlank

/**
 * @author Mompati 'Patco' Keetile
 * @since 06-12-2022 @ 14:12
 *
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
class Subreddit(
    @Id
    @SequenceGenerator(name = "subreddit_id", sequenceName = "post_id", allocationSize = 1)
    @GeneratedValue(generator = "subreddit_id", strategy = GenerationType.IDENTITY)
    private val id: Long,
    @NotBlank(message = "Community name is required.")
    private val name: String,
    @NotBlank(message = "Description name is required.")
    private val description: String,
    @OneToMany( fetch = LAZY)
    private val posts: List<Post>,
    private val createdDate: Instant,
//    TODO: @ManyToOne(fetch = LAZY) private val user: User
)