package com.eroldme.d66.subreddit.post;

import com.eroldme.d66.appuser.AppUser;
import com.eroldme.d66.subreddit.subreddit.Subreddit;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.time.Instant;

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
public class Post {
  @Id
  @SequenceGenerator(name = "post_id_seq", sequenceName = "post_id_seq", allocationSize = 1)
  @GeneratedValue(generator = "post_id_seq", strategy = GenerationType.IDENTITY)
  private Long postId;
  @NotBlank(message = "Post Name cannot be empty or Null.")
  private String post;
  @Nullable
  private String url;
  @Nullable
  @Lob
  private String description;
  private Integer voteCount;
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "userId", referencedColumnName  = "userId")
  private AppUser appUser;
  private Instant createdDate;
  @ManyToOne(fetch = LAZY) @JoinColumn(name ="id", referencedColumnName = "id")
  private Subreddit subreddit;
}
