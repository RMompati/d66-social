package com.eroldmr.d66.subreddit.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mompati 'Patco' Keetile
 * @created 04-01-2023 @ 13:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "NewDto")
public class PostDto {

  private Long postId;
  private String postName;
  private String url;
  private String description;
  private String username;
  private String subredditName;
}
