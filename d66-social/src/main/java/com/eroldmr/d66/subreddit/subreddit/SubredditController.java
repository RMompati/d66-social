package com.eroldmr.d66.subreddit.subreddit;

import com.eroldmr.d66.subreddit.subreddit.dto.SubredditDto;
import com.eroldmr.d66.utils.D66Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mompati 'Patco' Keetile
 * @created 03-01-2023 @ 15:15
 */
@RestController
@RequestMapping("/api/subreddit")
@RequiredArgsConstructor
@Slf4j
public class SubredditController {

  private final SubredditService subredditService;

  @PostMapping
  public ResponseEntity<D66Response> createSubreddit(@RequestBody SubredditDto subredditDto) {
    return ResponseEntity.ok(subredditService.save(subredditDto));
  }

  @GetMapping
  public ResponseEntity<D66Response> getSubreddits() {
    return ResponseEntity.ok(subredditService.getAllSubreddits());
  }

  @GetMapping("/{id}")
  public ResponseEntity<D66Response> getSubreddit(@PathVariable Long id) {
    return ResponseEntity.ok(subredditService.getSubreddit(id));
  }
}
