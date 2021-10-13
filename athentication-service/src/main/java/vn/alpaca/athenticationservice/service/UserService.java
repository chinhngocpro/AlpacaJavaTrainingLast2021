package vn.alpaca.athenticationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import vn.alpaca.athenticationservice.client.UserClient;
import vn.alpaca.athenticationservice.object.User;
import vn.alpaca.response.wrapper.SuccessResponse;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserClient userClient;

    public User findUserById(int id) {
        SuccessResponse<User> user = userClient.findById(id);
        return user.getData();
    }

    @Override
    public User loadUserByUsername(String s) {
        SuccessResponse<User> user = userClient.findByUserName(s);
        return user.getData();
    }
}
