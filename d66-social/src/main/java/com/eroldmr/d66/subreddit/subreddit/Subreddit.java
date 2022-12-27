package com.eroldmr.d66.subreddit.subreddit;

import com.eroldmr.d66.appuser.AppUser;
import com.eroldmr.d66.subreddit.post.Post;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.time.Instant;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

/**
 * @author Mompati 'Patco' Keetile
 * @created 19-12-2022 @ 11:53
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Subreddit {
  @Id
  @SequenceGenerator(name = "subreddit_id_seq", sequenceName = "subreddit_id_seq", allocationSize = 1)
  @GeneratedValue(generator = "subreddit_id_seq", strategy = GenerationType.IDENTITY)
  private Long id;
  @NotBlank(message = "Community name is required.")
  private String name;
  @NotBlank(message = "Description is required.")
  private String description;
  @OneToMany(fetch = LAZY)
  private List<Post> posts;
  private Instant createOn;
  @ManyToOne(fetch = LAZY)
  private AppUser appUser;
}
