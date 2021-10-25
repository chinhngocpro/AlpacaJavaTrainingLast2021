package vn.alpaca.common.security.interceptor;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.alpaca.common.security.client.SecurityClient;
import vn.alpaca.common.security.object.AuthPermission;
import vn.alpaca.common.security.object.AuthUser;
import vn.alpaca.common.security.service.SecurityUserDetailService;
import vn.alpaca.constant.CustomHttpHeader;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class UserAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    SecurityUserDetailService userDetailService;

    @Autowired
    SecurityClient userClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        try {
            Integer id = extractUserIdFromHeader(request);
            String serverName = extractServiceNameFromHeader(request);

            if (id != null) {
                AuthUser user = userDetailService.findUserById(id);

                if (user != null) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(user, null,
                                    user.getAuthorities());

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(
                                    request));

                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);
                }
            } else if (Strings.isNotEmpty(serverName)) {
                AuthUser service = new AuthUser();
                service.setPermissions(
                        userDetailService.findAllPermissions().stream()
                                .map(AuthPermission::getPermissionName)
                                .collect(Collectors.toList())
                );

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                service, null,
                                service.getAuthorities());

                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(
                                request));

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }

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
}
