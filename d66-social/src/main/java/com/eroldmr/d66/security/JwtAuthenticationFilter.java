package com.eroldmr.d66.security;

import com.eroldmr.d66.appuser.AppUserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static io.jsonwebtoken.lang.Strings.hasText;

/**
 * @author Mompati 'Patco' Keetile
 * @created 03-01-2023 @ 14:36
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;
  private final AppUserService appUserService;
  private final AuthenticatedUserService authenticatedUserService;

  @SneakyThrows({ServletException.class, IOException.class})
  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain){

    String jwToken = getJwtFromRequest(request);

    if (hasText(jwToken) && jwtProvider.validateToken(jwToken)) {
      UserDetails userDetails = appUserService.loadUserByUsername(jwtProvider.getUsernameFromJwt(jwToken));
      UsernamePasswordAuthenticationToken authentication =
              new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

//      SecurityContextHolder.getContext().setAuthentication(authentication);
      authenticatedUserService.setAuthentication(authentication);
    }
    filterChain.doFilter(request, response);
  }

  private String getJwtFromRequest(HttpServletRequest request) {
    String jwToken = request.getHeader("Authorization");
    return hasText(jwToken) && jwToken.startsWith("Bearer ") ? jwToken.substring(7) : jwToken;
  }
}
