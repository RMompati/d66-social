package com.eroldmr.d66.subreddit.vote.mapper;

import com.eroldmr.d66.appuser.AppUser;
import com.eroldmr.d66.subreddit.post.Post;
import com.eroldmr.d66.subreddit.vote.Vote;
import com.eroldmr.d66.subreddit.vote.dto.VoteDto;

/**
 * @author Mompati 'Patco' Keetile
 * @created 08-01-2023 @ 15:15
 */
public class VoteMapper {
  public static Vote mapToVote(VoteDto voteDto, Post post, AppUser appUser) {
    return Vote
        .NewVote()
        .post(post)
        .appUser(appUser)
        .voteType(voteDto.getVoteType())
        .build();
  }

  public static VoteDto mapToDto(Vote vote) {
    return VoteDto
        .NewDto()
        .voteId(vote.getVoteId())
        .voteType(vote.getVoteType())
        .postId(vote.getPost().getPostId())
        .build();
  }
}
