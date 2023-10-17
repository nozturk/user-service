package dev.nozturk.userservice.filter;

import dev.nozturk.userservice.auth.CustomUserDetails;
import dev.nozturk.userservice.auth.JwtTokenProvider;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
  public static final String JWT_HEADER = "Authorization";

  private final JwtTokenProvider tokenProvider;

  private final UserDetailsService userDetailsService;

  public JwtRequestFilter(JwtTokenProvider tokenProvider, UserDetailsService userDetailsService) {
    this.tokenProvider = tokenProvider;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    String authorizationHeader = request.getHeader(JWT_HEADER);

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      String token = authorizationHeader.substring(7);
      String username = tokenProvider.extractUsername(token);
      CustomUserDetails userDetails =
          (CustomUserDetails) userDetailsService.loadUserByUsername(username);
      if (!((List) userDetails.getAuthorities()).get(0).equals("ROLE_ADMIN")) {

        boolean valid = tokenProvider.validateToken(token);
        if (valid && Objects.nonNull(userDetails)) {
          UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
              new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities());
          usernamePasswordAuthenticationToken.setDetails(
              new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
      }
    }
    chain.doFilter(request, response);
  }
}
