package vn.alpaca.commonsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.alpaca.commonsecurity.client.SecurityUserClient;
import vn.alpaca.commonsecurity.object.SecurityUserDetail;
import vn.alpaca.response.wrapper.SuccessResponse;

@Service
public class SecurityUserDetailService implements UserDetailsService {
    @Autowired
    SecurityUserClient securityUserClient;

    @Override
    public SecurityUserDetail loadUserByUsername(String s) throws UsernameNotFoundException {
        SuccessResponse<SecurityUserDetail> successResponse = securityUserClient.findByUserName(s);
        return successResponse.getData();
    }

    public SecurityUserDetail findUserById(int id) {
        SuccessResponse<SecurityUserDetail> user = securityUserClient.findById(id);
        return user.getData();
    }
}
