package vn.alpaca.authserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.alpaca.authserver.entity.Role;

public interface AuthRoleRepository
        extends JpaRepository<Role, Integer> {
}
