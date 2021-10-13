package vn.alpaca.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.alpaca.userservice.object.entity.Role;

public interface RoleRepository extends
        JpaRepository<Role, Integer> {
}
