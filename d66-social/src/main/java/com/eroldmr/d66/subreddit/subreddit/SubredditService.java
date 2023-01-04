package com.eroldmr.d66.subreddit.subreddit;

import com.eroldmr.d66.subreddit.subreddit.dto.SubredditDto;
import com.eroldmr.d66.utils.D66Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;
import static java.util.Collections.emptyList;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * @author Mompati 'Patco' Keetile
 * @created 03-01-2023 @ 15:20
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SubredditService {

  private final SubredditRepository subredditRepository;

  @Transactional
  public D66Response save(SubredditDto subredditDto) {
    return D66Response
            .respond()
            .statusCode(CREATED.value())
            .status(CREATED)
            .message("Subreddit created successfully.")
            .data(of(
                    "subreddit",
                    mapToDto(subredditRepository.save(mapDtoToSubreddit(subredditDto)))))
            .build();
  }

  @Transactional
  public D66Response getAll() {
    return D66Response
            .respond()
            .statusCode(OK.value())
            .status(OK)
            .message("Subreddits fetched.")
            .data(of(
                    "subreddits",
                    subredditRepository
                            .findAll()
                            .stream()
                            .map(this::mapToDto)
                            .collect(Collectors.toList())
            ))
            .build();
  }

  private Subreddit mapDtoToSubreddit(SubredditDto subredditDto) {
    return Subreddit
            .NewSubreddit()
            .name(subredditDto.getName())
            .description(subredditDto.getDescription())
            .posts(emptyList())
            .createdOn(now())
            .build();
  }

  private SubredditDto mapToDto(Subreddit subreddit) {
    return SubredditDto
            .NewDto()
              .id(subreddit.getId())
              .name(subreddit.getName())
              .description(subreddit.getDescription())
              .numberOfPosts(subreddit.getPosts().size())
            .build();
  }
}
