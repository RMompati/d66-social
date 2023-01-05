package com.eroldmr.d66.subreddit.comment;

import com.eroldmr.d66.appuser.AppUser;
import com.eroldmr.d66.subreddit.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Mompati 'Patco' Keetile
 * @created 19-12-2022 @ 12:40
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findAllByPost(Post post);

  List<Comment> findAllByAppUser(AppUser appUser);
}
