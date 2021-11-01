package vn.alpaca.athenticationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.athenticationservice.mapper.AuthUserMapper;
import vn.alpaca.common.dto.request.AuthenticationRequest;
import vn.alpaca.common.dto.response.UserResponse;
import vn.alpaca.common.dto.wrapper.AbstractResponse;
import vn.alpaca.common.dto.wrapper.SuccessResponse;
import vn.alpaca.common.dto.wrapper.TokenResponse;
import vn.alpaca.security.model.AuthUser;
import vn.alpaca.security.model.RefreshToken;
import vn.alpaca.security.service.JwtTokenService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenService tokenService;
  private final AuthUserMapper mapper;

  @PostMapping("/login")
  AbstractResponse getToken(@RequestBody AuthenticationRequest authRequest) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    AuthUser user = (AuthUser) authentication.getPrincipal();

    String accessToken = tokenService.generateToken(user);
    String refreshToken = tokenService.createRefreshToken(user).getToken();
    UserResponse userInfo = mapper.authUserToUserResponse(user);

    TokenResponse response = new TokenResponse(accessToken, refreshToken, userInfo);

    return new SuccessResponse<>(response);
  }

  @PostMapping("/refresh-token/{token}")
  AbstractResponse refreshToken(@PathVariable String token) {
    RefreshToken refreshToken = tokenService.verifyExpiration(token);

    String accessToken = tokenService.generateToken(refreshToken.getUser());
    UserResponse userInfo = mapper.authUserToUserResponse(refreshToken.getUser());

    TokenResponse response = new TokenResponse(accessToken, refreshToken.getToken(), userInfo);

    return new SuccessResponse<>(response);
  }
}
