package vn.alpaca.userservice.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.alpaca.userservice.entity.jpa.Role;

import java.util.Optional;

public interface RoleJpaRepository extends JpaRepository<Role, Integer> {

  Optional<Role> findByName(String name);
}
