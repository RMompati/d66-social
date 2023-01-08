package com.eroldmr.d66.subreddit.vote;

import com.eroldmr.d66.appuser.AppUser;
import com.eroldmr.d66.subreddit.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

  Optional<Vote> findByPostAndAppUser(Post post, AppUser appUser);

}
