package vn.alpaca.gateway.service;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.alpaca.gateway.client.AuthenticationClient;
import vn.alpaca.dto.wrapper.SuccessResponse;

@Service
public class AuthService {

    @Autowired
    AuthenticationClient authClient;

    public Integer validateToken(String token) {
        if (Strings.isNullOrEmpty(token)) {
            return null;
        }

        if (!token.startsWith("Bearer ")) {
            return null;
        }

        SuccessResponse<Integer> response = authClient.verifyToken(token);

        if (response == null) {
            return null;
        }

        return response.getData();

    }
}
