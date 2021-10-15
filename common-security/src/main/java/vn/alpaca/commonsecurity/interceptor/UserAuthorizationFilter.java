package vn.alpaca.commonsecurity.interceptor;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.alpaca.commonsecurity.client.SecurityUserClient;
import vn.alpaca.commonsecurity.object.SecurityAuthorityDetail;
import vn.alpaca.commonsecurity.object.SecurityUserDetail;
import vn.alpaca.commonsecurity.object.ServiceUserDetail;
import vn.alpaca.commonsecurity.service.SecurityUserDetailService;
import vn.alpaca.constant.CustomHttpHeader;
import vn.alpaca.response.wrapper.SuccessResponse;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class UserAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    SecurityUserDetailService userDetailService;

    @Autowired
    ServiceUserDetail serviceUserDetail;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Integer id = extractUserIdFromHeader(request);
            String serverName = extractServiceNameFromHeader(request);
            if (id != null) {
                SecurityUserDetail user = userDetailService.findUserById(id);

                if (user != null) {

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } else if (Strings.isNotEmpty(serverName)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(serviceUserDetail, null, serviceUserDetail.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
//            e.printStackTrace();
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
