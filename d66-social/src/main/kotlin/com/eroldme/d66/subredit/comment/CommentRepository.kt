package com.eroldme.d66.subredit.comment

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author Mompati 'Patco' Keetile
 * @created 06-12-2022 @ 13:58
 */
@Repository
interface CommentRepository : JpaRepository<Comment, Long>
