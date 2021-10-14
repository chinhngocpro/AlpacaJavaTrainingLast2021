package vn.alpaca.athenticationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.alpaca.athenticationservice.object.User;
import vn.alpaca.athenticationservice.object.request.LoginForm;
import vn.alpaca.athenticationservice.service.JwtTokenService;
import vn.alpaca.response.wrapper.SuccessResponse;

@RestController
public class AuthController {

    @Autowired
    JwtTokenService tokenService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public SuccessResponse<String> login(@RequestBody LoginForm form) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenService.generateToken((User) authentication.getPrincipal());

        return new SuccessResponse<>(jwt);
    }

    @GetMapping(
            value = "/verify-token"
    )
    public SuccessResponse<Integer> verify() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new SuccessResponse<>(user.getId());
    }
}
