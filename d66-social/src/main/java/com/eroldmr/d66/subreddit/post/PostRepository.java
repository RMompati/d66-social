package com.eroldmr.d66.subreddit.post;

import com.eroldmr.d66.appuser.AppUser;
import com.eroldmr.d66.subreddit.subreddit.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
  * @author Mompati 'Patco' Keetile
  * @created 19-12-2022 @ 12:39
  *
  *
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
  List<Post> findAllByAppUser(AppUser appUser);
  List<Post> findAllBySubreddit(Subreddit subreddit);
}
