package com.eroldme.d66.subredit.post

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author Mompati 'Patco' Keetile
 * @created 06-12-2022 @ 13:57
 */
@Repository
interface PostRepository : JpaRepository<Post, Long>
