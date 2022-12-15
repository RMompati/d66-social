package com.eroldme.d66.subredit.subredit

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author Mompati 'Patco' Keetile
 * @created 06-12-2022 @ 14:13
 */
@Repository
interface SubredditRepository : JpaRepository<Subreddit, Long>
