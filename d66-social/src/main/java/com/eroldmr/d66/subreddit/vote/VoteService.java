package com.eroldmr.d66.subreddit.vote;

import com.eroldmr.d66.appuser.AppUser;
import com.eroldmr.d66.appuser.AppUserRepository;
import com.eroldmr.d66.constant.VoteType;
import com.eroldmr.d66.exception.D66SocialException;
import com.eroldmr.d66.response.D66Response;
import com.eroldmr.d66.security.AuthenticatedUserService;
import com.eroldmr.d66.subreddit.post.Post;
import com.eroldmr.d66.subreddit.post.PostRepository;
import com.eroldmr.d66.subreddit.vote.dto.VoteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.eroldmr.d66.constant.VoteType.UP_VOTE;
import static com.eroldmr.d66.subreddit.vote.mapper.VoteMapper.mapToDto;
import static com.eroldmr.d66.subreddit.vote.mapper.VoteMapper.mapToVote;
import static java.lang.String.format;
import static java.time.Instant.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.ALREADY_REPORTED;
import static org.springframework.http.HttpStatus.CREATED;

/**
 * @author Mompati 'Patco' Keetile
 * @created 08-01-2023 @ 12:52
 */
@Service
@RequiredArgsConstructor
public class VoteService {

  private final VoteRepository voteRepository;
  private final PostRepository postRepository;
  private final AppUserRepository appUserRepository;
  private final AuthenticatedUserService authenticatedUserService;

  @Transactional
  public D66Response save(VoteDto voteDto) {
    AppUser appUser = authenticatedUserService.getAuthenticatedPrincipal();
    Post post = postRepository.findById(voteDto.getPostId())
        .orElseThrow(() -> new D66SocialException(format("Post with id (%d) not found.", voteDto.getPostId())));

    Optional<Vote> oldVote = voteRepository.findByPostAndAppUser(post, appUser);
    if (oldVote.isPresent() && oldVote.get().getVoteType().equals(voteDto.getVoteType())) {
      throw new D66SocialException(format("Already '%s'.", voteString(voteDto.getVoteType())), ALREADY_REPORTED);
    }
    updatePostVotes(post, voteDto.getVoteType());

    Vote vote = voteRepository.save(mapToVote(voteDto, post, appUser));
    postRepository.save(post);


    return D66Response
        .respond()
        .timestamp(now())
        .statusCode(CREATED.value())
        .status(CREATED)
        .message("Vote saved.")
        .data(of("vote", mapToDto(vote)))
        .build();
  }

  private void updatePostVotes(Post post, VoteType newVote) {
    if (UP_VOTE.equals(newVote)) {
      post.setVoteCount(post.getVoteCount() + 1);
    } else {
      post.setVoteCount(post.getVoteCount() - 1);
    }

  }

  public String voteString(VoteType voteType) {
    return (UP_VOTE.equals(voteType)) ? "Up Voted" : "Down Voted";
  }
}
