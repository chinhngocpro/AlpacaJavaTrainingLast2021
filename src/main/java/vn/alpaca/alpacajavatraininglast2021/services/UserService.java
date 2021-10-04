package vn.alpaca.alpacajavatraininglast2021.services;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    // 1. Find all available users
    // 2. Find specific users (apply searching, include role filter)
    // 3. Create a new user (include set role for user)
    // 4. Update user info (include update role for user)
    // 5. Activate/deactivate user

    // 6. View all available roles
    // 7. View specific roles (apply searching)
    // 8. Edit role info
    // 9. Delete role
}
