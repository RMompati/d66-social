package com.eroldmr.d66.subreddit.subreddit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mompati 'Patco' Keetile
 * @created 03-01-2023 @ 15:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "NewDto")
public class SubredditDto {
  private Long id;
  private String name;
  private String description;
  private Integer numberOfPosts;
}
