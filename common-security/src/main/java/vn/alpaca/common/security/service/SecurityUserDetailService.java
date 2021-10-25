package vn.alpaca.common.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.alpaca.common.security.client.SecurityClient;
import vn.alpaca.common.security.object.AuthPermission;
import vn.alpaca.common.security.object.AuthUser;
import vn.alpaca.dto.wrapper.SuccessResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailService implements UserDetailsService {

    private final SecurityClient securityClient;

    @Override
    public AuthUser loadUserByUsername(String s)
            throws UsernameNotFoundException {
        SuccessResponse<AuthUser> response =
                securityClient.findByUserName(s);
        return response.getData();
    }

    public AuthUser findUserById(int id) {
        SuccessResponse<AuthUser> response =
                securityClient.findById(id);
        return response.getData();
    }

    public List<AuthPermission> findAllPermissions() {
        SuccessResponse<List<AuthPermission>> response =
                securityClient.findAllPermissions();
        return response.getData();
    }
}
