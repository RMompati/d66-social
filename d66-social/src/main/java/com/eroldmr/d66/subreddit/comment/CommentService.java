package com.eroldmr.d66.subreddit.comment;

import com.eroldmr.d66.appuser.AppUser;
import com.eroldmr.d66.appuser.AppUserRepository;
import com.eroldmr.d66.exception.D66SocialException;
import com.eroldmr.d66.response.D66Response;
import com.eroldmr.d66.security.AuthenticatedUserService;
import com.eroldmr.d66.subreddit.comment.dto.CommentDto;
import com.eroldmr.d66.subreddit.comment.mapper.CommentMapper;
import com.eroldmr.d66.subreddit.post.Post;
import com.eroldmr.d66.subreddit.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.eroldmr.d66.subreddit.comment.mapper.CommentMapper.mapToComment;
import static com.eroldmr.d66.subreddit.comment.mapper.CommentMapper.mapToDto;
import static java.lang.String.format;
import static java.time.Instant.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * @author Mompati 'Patco' Keetile
 * @created 05-01-2023 @ 14:07
 */
@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;
  private final AppUserRepository appUserRepository;
  private final AuthenticatedUserService authenticatedUserService;


  @Transactional
  public D66Response save(CommentDto commentDto) {
    Post post = postRepository.findById(commentDto.getPostId())
        .orElseThrow(() -> new D66SocialException(format("Post with id (%d) not found.", commentDto.getPostId())));
    Comment comment = mapToComment(commentDto, post, authenticatedUserService.getAuthenticatedPrincipal());
    
    return D66Response
            .respond()
            .timestamp(now())
            .statusCode(CREATED.value())
            .status(CREATED)
            .username(commentDto.getUsername())
            .message("Comment saved.")
            .data(of("comment", mapToDto(commentRepository.save(comment))))
            .build();
  }

  @Transactional(readOnly = true)
  public D66Response getAllPostComments(Long postId) {
    Post post = postRepository
            .findById(postId)
            .orElseThrow(() -> new D66SocialException(format("Post with id (%d) not found.", postId)));
    List<Comment> comments = commentRepository.findAllByPost(post);
    return D66Response
            .respond()
            .timestamp(now())
            .statusCode(OK.value())
            .status(OK)
            .message("Post's comments fetched.")
            .data(of("comments", comments.stream().map(CommentMapper::mapToDto).collect(Collectors.toList())))
            .build();
  }

  @Transactional(readOnly = true)
  public D66Response getAllCommentsByUsername(String username) {
    AppUser appUser = appUserRepository.findByUsername(username)
            .orElseThrow(() -> new D66SocialException(format("User with username '%s' not found.", username)));

    List<Comment> comments = commentRepository.findAllByAppUser(appUser);
    return D66Response
            .respond()
            .timestamp(now())
            .statusCode(OK.value())
            .status(OK)
            .message("User's comments fetched.")
            .data(of("comments", comments.stream().map(CommentMapper::mapToDto).collect(Collectors.toList())))
            .build();
  }
}
