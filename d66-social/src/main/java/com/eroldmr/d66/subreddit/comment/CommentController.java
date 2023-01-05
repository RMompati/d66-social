package com.eroldmr.d66.subreddit.comment;

import com.eroldmr.d66.response.D66Response;
import com.eroldmr.d66.subreddit.comment.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mompati 'Patco' Keetile
 * @created 05-01-2023 @ 14:40
 */
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @PostMapping
  public ResponseEntity<D66Response> createComment(@RequestBody CommentDto commentDto) {
    return ResponseEntity.ok(commentService.save(commentDto));
  }

  @GetMapping("/by-post/{postId}")
  public ResponseEntity<D66Response> getPostComments(@PathVariable Long postId) {
    return ResponseEntity.ok(commentService.getAllPostComments(postId));
  }

  @GetMapping("/by-user/{username}")
  public ResponseEntity<D66Response> getPostComments(@PathVariable String username) {
    return ResponseEntity.ok(commentService.getAllCommentsByUsername(username));
  }
}
