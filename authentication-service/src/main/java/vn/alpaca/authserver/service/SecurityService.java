package vn.alpaca.authserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.alpaca.authserver.entity.User;
import vn.alpaca.authserver.repository.AuthPermissionRepository;
import vn.alpaca.authserver.repository.AuthUserRepository;
import vn.alpaca.common.security.object.AuthPermission;
import vn.alpaca.common.security.object.AuthRole;
import vn.alpaca.common.security.object.AuthUser;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService {

    private final AuthUserRepository userRepo;
    private final AuthPermissionRepository permissionRepo;

    @Override
    public AuthUser loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepo.findAuthUserByUsername(username)
                .orElseThrow(() -> new AccessDeniedException(
                        "Authentication not found"
                ));

        return convertToAuthModel(user);
    }

    public AuthUser findUserById(int id) {
        User user = userRepo.findAuthUserById(id)
                .orElseThrow(() -> new AccessDeniedException(
                        "Authentication not found"
                ));
        return convertToAuthModel(user);
    }

    public List<AuthPermission> findAllPermission() {
        return permissionRepo.findAll().stream()
                .map(authority -> new AuthPermission(
                        authority.getId(),
                        authority.getPermissionName()
                )).collect(Collectors.toList());
    }

    private AuthUser convertToAuthModel(User user) {
        AuthUser authUser = new AuthUser();
        authUser.setId(user.getId());
        authUser.setUsername(user.getUsername());
        authUser.setPassword(user.getPassword());
        authUser.setActive(user.isActive());

        AuthRole role = new AuthRole();
        role.setId(user.getRole().getId());
        role.setName(user.getRole().getName());

        List<AuthPermission> permissions =
                user.getRole().getAuthorities().stream()
                        .map(authority -> new AuthPermission(
                                authority.getId(),
                                authority.getPermissionName()
                        )).collect(Collectors.toList());

        role.setPermissions(permissions);

        authUser.setRole(role);

        return authUser;
    }
}
