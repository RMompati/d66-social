package com.eroldmr.d66.subreddit.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mompati 'Patco' Keetile
 * @created 19-12-2022 @ 12:40
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
