package vn.alpaca.gateway.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.alpaca.gateway.object.User;
import vn.alpaca.gateway.service.JwtTokenService;
import vn.alpaca.gateway.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter {

    @Autowired
    private JwtTokenService tokenService;

    @Autowired
    private UserService userService;

    private static final String AUTHENTICATION_SCHEME = "Bearer ";

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = extractTokenFromHeader(request);

            if (tokenService.validateToken(jwt)) {
                int id = tokenService.extractUserIdFromToken(jwt);

                User user = userService.findUserById(id);

//                UsernamePasswordAuthenticationToken authentication =
//                        new UsernamePasswordAuthenticationToken(user, null, user.getPermissions());
//
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
        }

        filterChain.doFilter(request, response);
    }

    protected String extractTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) &&
                bearerToken.startsWith(AUTHENTICATION_SCHEME)) {
            return bearerToken.substring(AUTHENTICATION_SCHEME.length());
        }
        return null;
    }
}
