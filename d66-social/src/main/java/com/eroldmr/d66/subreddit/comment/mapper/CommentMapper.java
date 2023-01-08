package com.eroldmr.d66.subreddit.comment.mapper;

import com.eroldmr.d66.appuser.AppUser;
import com.eroldmr.d66.subreddit.comment.Comment;
import com.eroldmr.d66.subreddit.comment.dto.CommentDto;
import com.eroldmr.d66.subreddit.post.Post;

/**
 * @author Mompati 'Patco' Keetile
 * @created 08-01-2023 @ 15:10
 */
public class CommentMapper {

  public static Comment mapToComment(CommentDto commentDto, Post post, AppUser appUser) {

    return Comment
        .NewComment()
        .text(commentDto.getText())
        .appUser(appUser)
        .post(post)
        .build();
  }

  public static CommentDto mapToDto(Comment comment) {
    return CommentDto
        .NewDto()
        .id(comment.getId())
        .text(comment.getText())
        .postId(comment.getPost().getPostId())
        .username(comment.getAppUser().getUsername())
        .build();
  }
}
