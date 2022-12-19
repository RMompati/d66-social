package com.eroldme.d66.subreddit.subreddit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mompati 'Patco' Keetile
 * @created 19-12-2022 @ 12:34
 */
@Repository
public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
}
