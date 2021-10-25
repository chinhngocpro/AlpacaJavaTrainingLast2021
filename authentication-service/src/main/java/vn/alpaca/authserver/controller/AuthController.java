package vn.alpaca.authserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final AuthenticationManager authenticationManager;

    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public SuccessResponse<String> login(@RequestBody LoginReq loginReq) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginReq.getUsername(),
                        loginReq.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenService.generateToken(
                (AuthUser) authentication.getPrincipal()
        );

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
