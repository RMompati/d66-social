package com.eroldmr.d66.subreddit.subreddit.mapper;

import com.eroldmr.d66.appuser.AppUser;
import com.eroldmr.d66.subreddit.post.Post;
import com.eroldmr.d66.subreddit.subreddit.Subreddit;
import com.eroldmr.d66.subreddit.subreddit.dto.SubredditDto;

import static java.time.LocalDateTime.now;
import static java.util.Collections.emptyList;

/**
 * @author Mompati 'Patco' Keetile
 * @created 08-01-2023 @ 15:04
 */
public class SubredditMapper {

  public static Subreddit mapDtoToSubreddit(SubredditDto subredditDto, AppUser appUser) {
    return Subreddit
        .NewSubreddit()
        .createdOn(now())
        .appUser(appUser)
        .posts(emptyList())
        .name(subredditDto.getName())
        .description(subredditDto.getDescription())
        .build();
  }

  public static SubredditDto mapToDto(Subreddit subreddit) {
    return SubredditDto
        .NewDto()
        .id(subreddit.getId())
        .name(subreddit.getName())
        .description(subreddit.getDescription())
        .numberOfPosts(subreddit.getPosts().size())
        .build();
  }
}
