package com.eroldmr.d66.security;

import com.eroldmr.d66.appuser.AppUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author Mompati 'Patco' Keetile
 * @created 05-01-2023 @ 09:52
 */
@Service
public class AuthenticatedUserService {
  public Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }
  public void setAuthentication(Authentication authentication) {
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  public AppUser getAuthenticatedPrincipal() {
    return (AppUser) getAuthentication().getPrincipal();
  }

  public String getUsername() {
    return getAuthenticatedPrincipal().getUsername();
  }

  public Boolean checkAuthenticationObject() {
    return getAuthentication() != null;
  }
}
