package vn.alpaca.alpacajavatraininglast2021.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.alpaca.alpacajavatraininglast2021.object.entity.User;
import vn.alpaca.alpacajavatraininglast2021.object.request.auth.LoginForm;
import vn.alpaca.alpacajavatraininglast2021.object.response.AbstractResponse;
import vn.alpaca.alpacajavatraininglast2021.object.response.SuccessResponse;
import vn.alpaca.alpacajavatraininglast2021.provider.JwtTokenProvider;

@RestController
@RequestMapping(
        value = "/api",
        consumes = "application/json",
        produces = "application/json"
)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public AbstractResponse login(@RequestBody LoginForm form) {
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                form.getUsername(),
                                form.getPassword()
                        )
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(
                (User) authentication.getPrincipal()
        );

        return new SuccessResponse<>(jwt);
    }
}
