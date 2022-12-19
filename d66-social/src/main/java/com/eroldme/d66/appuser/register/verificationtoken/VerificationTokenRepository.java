package com.eroldme.d66.appuser.register.verificationtoken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mompati 'Patco' Keetile
 * @created 19-12-2022 @ 14:37
 */
@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
}
