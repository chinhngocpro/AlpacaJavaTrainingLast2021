package vn.alpaca.authserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.alpaca.authserver.entity.Authority;

public interface AuthPermissionRepository
        extends JpaRepository<Authority, Integer> {
}
