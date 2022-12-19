package com.eroldme.d66.subreddit.vote;

import com.eroldme.d66.appuser.AppUser;
import com.eroldme.d66.constant.VoteType;
import com.eroldme.d66.subreddit.post.Post;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

/**
 * @author Mompati 'Patco' Keetile
 * @created 19-12-2022 @ 11:54
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Vote {
  @Id
  @SequenceGenerator(name = "vote_id_seq", sequenceName = "vote_id_seq", allocationSize = 1)
  @GeneratedValue(generator = "vote_id_seq", strategy = GenerationType.IDENTITY)
  private Long voteId;
  private VoteType voteType;
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "postId", referencedColumnName = "postId")
  private Post post;
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "userId", referencedColumnName = "userId")
  private AppUser appUser;
}
