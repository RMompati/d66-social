package com.eroldmr.d66.subreddit.comment;

import com.eroldmr.d66.appuser.AppUser;
import com.eroldmr.d66.subreddit.post.Post;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import static javax.persistence.FetchType.LAZY;

/**
 * @author Mompati 'Patco' Keetile
 * @created 19-12-2022 @ 11:52
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "NewComment")
@Entity
public class Comment {
  @Id
  @SequenceGenerator(name = "comment_id_seq", sequenceName = "comment_id_seq", allocationSize = 1)
  @GeneratedValue(generator = "comment_id_seq", strategy = GenerationType.IDENTITY)
  private Long id;
  @NotEmpty
  private String text;
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "postId", referencedColumnName = "postId")
  private Post post;
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "userId", referencedColumnName = "userId")
  private AppUser appUser;
}
