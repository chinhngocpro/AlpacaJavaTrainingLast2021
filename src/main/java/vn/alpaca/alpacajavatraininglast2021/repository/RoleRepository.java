package vn.alpaca.alpacajavatraininglast2021.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String roleName);
}
