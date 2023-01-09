package com.eroldmr.d66.subreddit.post.mapper;

import com.eroldmr.d66.appuser.AppUser;
import com.eroldmr.d66.subreddit.comment.CommentRepository;
import com.eroldmr.d66.subreddit.post.Post;
import com.eroldmr.d66.subreddit.post.dto.PostDto;
import com.eroldmr.d66.subreddit.subreddit.Subreddit;
import com.eroldmr.d66.subreddit.vote.VoteRepository;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

import static java.time.LocalDateTime.now;

/**
 * @author Mompati 'Patco' Keetile
 * @created 08-01-2023 @ 14:51
 */
@Component
@RequiredArgsConstructor
public class PostMapper {

  private final CommentRepository commentRepository;
  private final VoteRepository voteRepository;

  public Post mapToPost(PostDto postDto, Subreddit subreddit, AppUser appUser) {
    return Post
            .NewPost()
            .voteCount(0)
            .appUser(appUser)
            .createdDate(now())
            .subreddit(subreddit)
            .url(postDto.getUrl())
            .postName(postDto.getPostName())
            .description(postDto.getDescription())
            .build();
  }

  public PostDto mapToDto(Post post) {
    return PostDto
            .NewDto()
            .url(post.getUrl())
            .duration(duration(post))
            .postId(post.getPostId())
            .postName(post.getPostName())
            .voteCount(post.getVoteCount())
            .commentCount(commentCount(post))
            .description(post.getDescription())
            .username(post.getAppUser().getUsername())
            .subredditName(post.getSubreddit().getName())
            .build();
  }

  private Integer commentCount(Post post) {
    return commentRepository.findAllByPost(post).size();
  }
  private String duration(Post post) {
    ZoneId zoneId = ZoneId.systemDefault();

    return TimeAgo.using(post.getCreatedDate().atZone(zoneId).toInstant().toEpochMilli());
  }
}
