package vn.alpaca.alpacajavatraininglast2021.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import vn.alpaca.alpacajavatraininglast2021.objects.entities.Role;
import vn.alpaca.alpacajavatraininglast2021.objects.entities.User;

import java.util.Collection;

public interface UserService extends UserDetailsService {

    // 1. Find all available users
    Collection<User> findAllUsers();

    Page<User> findAllUsers(Pageable pageable);

    // 2. Find specific users (apply searching, include role filter)
    Collection<User> findActiveUsers();

    Page<User> findActiveUsers(Pageable pageable);

    Collection<User> findUsersByNameContains(String fullName);

    Page<User> findUsersByNameContains(String fullName, Pageable pageable);

    Collection<User> findUsersByGender(boolean gender);

    Page<User> findUsersByGender(boolean gender, Pageable pageable);

    User findUserById(int id);

    User findUserByUsername(String username);

    User findUserByIdCard(String idCardNumber);

    // 3. Create a new user / Update user info
    User saveUser(User user);

    // 4. Activate/deactivate user
    void activateUser(int userId);

    void deactivateUser(int userId);

    // 5. View all available roles
    Collection<Role> findAllRoles();

    // 6. View specific roles (apply searching)
    Role findRoleById(int id);

    Role findRoleByName(String roleName);

    // 7. Create new role / Edit role info
    Role saveRole(Role role);

    // 8. Delete role
    void deleteRole(int roleId);

    void deleteRole(Role role);
}
