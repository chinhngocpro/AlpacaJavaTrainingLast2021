package vn.alpaca.gateway.service;

import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.alpaca.common.dto.wrapper.SuccessResponse;
import vn.alpaca.gateway.client.AuthenticationClient;

@Service
public class AuthenticationService {

  private final AuthenticationClient client;

  public AuthenticationService(AuthenticationClient client) {
    this.client = client;
  }

  public Integer validateToken(String token) {
    if (Strings.isNullOrEmpty(token)) {
      return null;
    }

    if (!token.startsWith("Bearer ")) {
      return null;
    }

    SuccessResponse<Integer> response = client.verifyToken(token);

    if (response == null) {
      return null;
    }

    return response.getData();
  }
}
