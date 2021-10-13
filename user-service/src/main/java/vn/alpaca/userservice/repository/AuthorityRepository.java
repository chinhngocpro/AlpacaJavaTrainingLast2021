package vn.alpaca.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.alpaca.userservice.object.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}
