package com.eroldme.d66.subredit.vote

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author Mompati 'Patco' Keetile
 * @created 06-12-2022 @ 14:10
 */
@Repository
interface VoteRepository : JpaRepository<Vote, Long>
