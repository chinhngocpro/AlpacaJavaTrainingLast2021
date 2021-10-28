package vn.alpaca.authserver.controller;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.authserver.service.SecurityService;
import vn.alpaca.common.security.object.AuthPermission;
import vn.alpaca.common.security.object.AuthUser;
import vn.alpaca.common.security.service.JwtTokenService;
import vn.alpaca.dto.request.LoginReq;
import vn.alpaca.dto.wrapper.SuccessResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenService tokenService;
    private final SecurityService securityService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public SuccessResponse<String> login(@RequestBody LoginReq loginReq) throws Exception {
        AuthUser user = securityService.loadUserByUsername(loginReq.getUsername());

        if (!passwordEncoder.matches(loginReq.getPassword(), user.getPassword())) {
            throw new Exception();
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenService.generateToken(user);

        return new SuccessResponse<>(jwt);
    }

    @GetMapping(value = "/verify-token")
    public SuccessResponse<Integer> verify() {
        AuthUser user =
                (AuthUser) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        return new SuccessResponse<>(user.getId());
    }

    @GetMapping(value = "/verify-user/{userId}")
    public SuccessResponse<AuthUser> verifyUser(
            @PathVariable int userId
    ) {
        AuthUser user = securityService.findUserById(userId);
        return new SuccessResponse<>(user);
    }

    @GetMapping(value = "/search/{username}")
    public SuccessResponse<AuthUser> verifyUser(
            @PathVariable String username
    ) {
        AuthUser user = securityService.loadUserByUsername(username);
        return new SuccessResponse<>(user);
    }

    @GetMapping(value = "/authorities")
    public SuccessResponse<List<AuthPermission>> getAllPermisisons() {
        List<AuthPermission> permissions = securityService.findAllPermission();
        return new SuccessResponse<>(permissions);
    }
}
