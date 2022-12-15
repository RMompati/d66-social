package com.eroldme.d66.user;

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author Mompati 'Patco' Keetile
 * @created 06-12-2022 @ 13:54
 */
@Repository
interface UserRepository : JpaRepository<User, Long>
