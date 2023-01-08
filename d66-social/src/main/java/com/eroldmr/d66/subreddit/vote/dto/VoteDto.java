package com.eroldmr.d66.subreddit.vote.dto;

import com.eroldmr.d66.constant.VoteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mompati 'Patco' Keetile
 * @created 08-01-2023 @ 12:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "NewDto")
public class VoteDto {
  private Long voteId;
  private VoteType voteType;
  private Long postId;
  private String username;
}
