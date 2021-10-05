package vn.alpaca.alpacajavatraininglast2021.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.alpaca.alpacajavatraininglast2021.object.entity.User;
import vn.alpaca.alpacajavatraininglast2021.security.jwt.JwtTokenProvider;
import vn.alpaca.alpacajavatraininglast2021.wrapper.request.auth.LoginRequest;
import vn.alpaca.alpacajavatraininglast2021.wrapper.response.AbstractResponse;
import vn.alpaca.alpacajavatraininglast2021.wrapper.response.SuccessResponse;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public AbstractResponse login(@RequestBody LoginRequest form) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken((User) authentication.getPrincipal());

        return new SuccessResponse<>(jwt);
    }
}
