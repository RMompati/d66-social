package com.eroldmr.d66.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mompati 'Patco' Keetile
 * @created 19-12-2022 @ 12:41
 */
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
}
