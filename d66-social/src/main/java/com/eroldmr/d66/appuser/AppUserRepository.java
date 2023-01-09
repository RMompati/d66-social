package com.eroldmr.d66.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author Mompati 'Patco' Keetile
 * @created 19-12-2022 @ 12:41
 */
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

  Optional<AppUser> findByUsername(String username);

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query("UPDATE AppUser a SET a.enabled = true WHERE a.email =?1")
  public void enableUserByEmail(String email);
}
