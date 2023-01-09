package com.eroldmr.d66.subreddit.vote;

import com.eroldmr.d66.response.D66Response;
import com.eroldmr.d66.subreddit.vote.dto.VoteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mompati 'Patco' Keetile
 */
@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
public class VoteController {

  private final VoteService voteService;

  public ResponseEntity<D66Response> vote(@RequestBody VoteDto voteDto) {
    return ResponseEntity.ok(voteService.save(voteDto));
  }
}
