package com.eroldmr.d66.subreddit.post;

import com.eroldmr.d66.response.D66Response;
import com.eroldmr.d66.subreddit.post.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mompati 'Patco' Keetile
 * @created 04-01-2023 @ 13:50
 */
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;

  @PostMapping
  public ResponseEntity<D66Response> createPost(@RequestBody PostDto postDto) {
    return ResponseEntity.ok(postService.save(postDto));
  }

  @GetMapping
  public ResponseEntity<D66Response> getAllPosts() {
    return ResponseEntity.ok(postService.getAllPosts());
  }

  @GetMapping("/{id}")
  public ResponseEntity<D66Response> getPost(@PathVariable Long id) {
    return ResponseEntity.ok(postService.getPostById(id));
  }

  @GetMapping("/by-subreddit/{id}")
  public ResponseEntity<D66Response> getPostsBySubreddit(@PathVariable Long id) {
    return ResponseEntity.ok(postService.getAllPostsBySubreddit(id));
  }

  @GetMapping("/by-user/{username}")
  public ResponseEntity<D66Response> getPostsByUser(@PathVariable String username) {
    return ResponseEntity.ok(postService.getAllPostsBySubreddit(username));
  }
}
