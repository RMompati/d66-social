package com.eroldmr.d66.subreddit.subreddit;

import com.eroldmr.d66.exception.D66SocialException;
import com.eroldmr.d66.response.D66Response;
import com.eroldmr.d66.security.AuthenticatedUserService;
import com.eroldmr.d66.subreddit.subreddit.dto.SubredditDto;
import com.eroldmr.d66.subreddit.subreddit.mapper.SubredditMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import static com.eroldmr.d66.subreddit.subreddit.mapper.SubredditMapper.mapDtoToSubreddit;
import static com.eroldmr.d66.subreddit.subreddit.mapper.SubredditMapper.mapToDto;
import static java.lang.String.format;
import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.*;

/**
 * @author Mompati 'Patco' Keetile
 * @created 03-01-2023 @ 15:20
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SubredditService {

  private final SubredditRepository subredditRepository;
  private final AuthenticatedUserService authenticatedUserService;

  @Transactional
  public D66Response save(SubredditDto subredditDto) {
    return D66Response
            .respond()
            .timestamp(now())
            .statusCode(CREATED.value())
            .status(CREATED)
            .message("Subreddit created successfully.")
            .username(authenticatedUserService.getUsername())
            .data(of(
                    "subreddit",
                    mapToDto(
                        subredditRepository.save(
                            mapDtoToSubreddit(subredditDto, authenticatedUserService.getAuthenticatedPrincipal()))
                    )))
            .build();
  }

  @Transactional
  public D66Response getAllSubreddits() {
    return D66Response
            .respond()
            .timestamp(now())
            .statusCode(OK.value())
            .status(OK)
            .message("Subreddits fetched.")
            .data(of(
                    "subreddits",
                    subredditRepository
                            .findAll()
                            .stream()
                            .map(SubredditMapper::mapToDto)
                            .collect(Collectors.toList())
            ))
            .build();
  }

  @Transactional
  public D66Response getSubreddit(Long id) {
    Subreddit subreddit = subredditRepository.findById(id).orElseThrow(
            () -> new D66SocialException(format("Subreddit with id(%d) not found.", id), NOT_FOUND)
    );
    return D66Response
            .respond()
            .timestamp(now())
            .statusCode(OK.value())
            .status(OK)
            .message("Subreddit fetched.")
            .data(of("subreddit", mapToDto(subreddit)))
            .build();
  }
}
