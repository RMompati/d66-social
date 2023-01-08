package com.eroldmr.d66.subreddit.post;

import com.eroldmr.d66.appuser.AppUser;
import com.eroldmr.d66.appuser.AppUserRepository;
import com.eroldmr.d66.exception.D66SocialException;
import com.eroldmr.d66.response.D66Response;
import com.eroldmr.d66.security.AuthenticatedUserService;
import com.eroldmr.d66.subreddit.post.dto.PostDto;
import com.eroldmr.d66.subreddit.post.mapper.PostMapper;
import com.eroldmr.d66.subreddit.subreddit.Subreddit;
import com.eroldmr.d66.subreddit.subreddit.SubredditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.eroldmr.d66.subreddit.post.mapper.PostMapper.mapToDto;
import static com.eroldmr.d66.subreddit.post.mapper.PostMapper.mapToPost;
import static java.lang.String.format;
import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * @author Mompati 'Patco' Keetile
 * @created 04-01-2023 @ 13:50
 */
@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;
  private final SubredditRepository subredditRepository;
  private final AuthenticatedUserService authenticatedUserService;
  private final AppUserRepository appUserRepository;

  @Transactional
  public D66Response save(PostDto postDto) {
    Subreddit subreddit = subredditRepository.findByName(postDto.getSubredditName())
        .orElseThrow(
            () -> new D66SocialException(format(
                "Subreddit with name '%s' does not exist.", postDto.getSubredditName()))
        );

    Post post = postRepository.save(mapToPost(postDto, subreddit, authenticatedUserService.getAuthenticatedPrincipal()));
    post.getSubreddit().getPosts().add(post);
    subredditRepository.save(post.getSubreddit());

    PostDto savedPostDto = mapToDto(post);
    return D66Response
            .respond()
            .timestamp(now())
            .statusCode(CREATED.value())
            .status(CREATED)
            .message(format("Post created under subreddit '%s'", postDto.getSubredditName()))
            .username(authenticatedUserService.getUsername())
            .data(of("post", savedPostDto))
            .build();
  }

  @Transactional(readOnly = true)
  public D66Response getAllPosts() {
    return D66Response
            .respond()
            .timestamp(now())
            .statusCode(OK.value())
            .status(OK)
            .message("Posts fetched.")
            .username(authenticatedUserService.getUsername())
            .data(of("posts",
                    postRepository
                            .findAll()
                            .stream()
                            .map(PostMapper::mapToDto)
                            .collect(Collectors.toList())
            ))
            .build();
  }

  @Transactional(readOnly = true)
  public D66Response getPostById(Long id) {
    Post post = postRepository
            .findById(id)
            .orElseThrow(() -> new D66SocialException(format("Post with id (%d) not found.", id)));

    return D66Response
            .respond()
            .timestamp(now())
            .statusCode(OK.value())
            .status(OK)
            .message("Post fetched by id")
            .username(authenticatedUserService.getUsername())
            .data(of("post", mapToDto(post)))
            .build();
  }

  @Transactional(readOnly = true)
  public D66Response getAllPostsBySubreddit(Long id) {

    Subreddit subreddit = subredditRepository.findById(id)
            .orElseThrow(() -> new D66SocialException(format("Subreddit with id (%d) not found.", id)));

    List<Post> posts = postRepository.findAllBySubreddit(subreddit);

    return D66Response
            .respond()
            .timestamp(now())
            .statusCode(OK.value())
            .status(OK)
            .message("Posts fetched by subreddit.")
            .username(authenticatedUserService.getUsername())
            .data(of("posts", posts.stream().map(PostMapper::mapToDto).collect(Collectors.toList())))
            .build();
  }

  @Transactional(readOnly = true)
  public D66Response getAllPostsBySubreddit(String username) {
    AppUser appUser = appUserRepository.findAppUserByEmail(username)
            .orElseThrow(() -> new D66SocialException(format("User with username '%s' not found.", username)));

    List<Post> posts = postRepository.findAllByAppUser(appUser);

    return D66Response
            .respond()
            .timestamp(now())
            .statusCode(OK.value())
            .status(OK)
            .message("Posts fetched by username.")
            .username(authenticatedUserService.getUsername())
            .data(of("posts", posts.stream().map(PostMapper::mapToDto).collect(Collectors.toList())))
            .build();
  }
}
