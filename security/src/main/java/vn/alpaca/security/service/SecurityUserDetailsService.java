package vn.alpaca.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.alpaca.common.dto.wrapper.SuccessResponse;
import vn.alpaca.security.client.SecurityClient;
import vn.alpaca.security.model.AuthPermission;
import vn.alpaca.security.model.AuthUser;

import java.util.List;

@Service
@Configuration
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

  private final SecurityClient securityClient;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    SuccessResponse<AuthUser> response = securityClient.findByUserName(username);
    return response.getData();
  }

  public AuthUser findById(int id) {
    SuccessResponse<AuthUser> response = securityClient.findById(id);
    return response.getData();
  }

  public List<AuthPermission> findAllPermission() {
    SuccessResponse<List<AuthPermission>> response = securityClient.getAuthorities();
    return response.getData();
  }
}
