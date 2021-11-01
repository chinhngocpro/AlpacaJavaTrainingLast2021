package vn.alpaca.security.config.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.alpaca.common.constant.CustomHttpHeader;
import vn.alpaca.security.model.AuthPermission;
import vn.alpaca.security.model.AuthUser;
import vn.alpaca.security.service.SecurityUserDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@Log4j2
@RequiredArgsConstructor
public class UserAuthorizationFilter extends OncePerRequestFilter {

  private final SecurityUserDetailsService userDetailsService;

  private String extractServiceNameFromHeader(HttpServletRequest request) {
    return request.getHeader(CustomHttpHeader.X_AUTHORIZED_SERVICE);
  }

  protected Integer extractUserIdFromHeader(HttpServletRequest request) {
    String userId = request.getHeader(CustomHttpHeader.X_AUTHORIZED_USER);
    if (StringUtils.hasText(userId)) {
      return Integer.parseInt(userId);
    }
    return null;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    try {
      Integer userId = extractUserIdFromHeader(request);
      String serviceName = extractServiceNameFromHeader(request);

      if (!ObjectUtils.isEmpty(userId)) {
        AuthUser user = userDetailsService.findById(userId);

        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      } else if (StringUtils.hasText(serviceName)) {
        List<AuthPermission> authorities = userDetailsService.findAllPermission();
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                new AuthUser(),
                null,
                authorities.stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getPermissionName()))
                    .collect(Collectors.toSet()));

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }

    } catch (Exception ex) {
      log.error(ex.getMessage());
    } finally {
      filterChain.doFilter(request, response);
    }
  }
}
