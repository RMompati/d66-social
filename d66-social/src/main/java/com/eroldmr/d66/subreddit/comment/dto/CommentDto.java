package com.eroldmr.d66.subreddit.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mompati 'Patco' Keetile
 * @created 05-01-2023 @ 13:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "NewDto")
public class CommentDto {
  private Long id;
  private Long postId;
  private String text;
  private String username;
}
