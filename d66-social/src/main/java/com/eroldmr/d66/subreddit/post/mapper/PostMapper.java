package com.eroldmr.d66.subreddit.post.mapper;

import com.eroldmr.d66.appuser.AppUser;
import com.eroldmr.d66.subreddit.post.Post;
import com.eroldmr.d66.subreddit.post.dto.PostDto;
import com.eroldmr.d66.subreddit.subreddit.Subreddit;

/**
 * @author Mompati 'Patco' Keetile
 * @created 08-01-2023 @ 14:51
 */
public class PostMapper {

  public static Post mapToPost(PostDto postDto, Subreddit subreddit, AppUser appUser) {
    return Post
        .NewPost()
        .appUser(appUser)
        .subreddit(subreddit)
        .url(postDto.getUrl())
        .postName(postDto.getPostName())
        .description(postDto.getDescription())
        .build();
  }

  public static PostDto mapToDto(Post post) {
    return PostDto
        .NewDto()
        .url(post.getUrl())
        .postId(post.getPostId())
        .postName(post.getPostName())
        .description(post.getDescription())
        .username(post.getAppUser().getUsername())
        .subredditName(post.getSubreddit().getName())
        .build();
  }
}
