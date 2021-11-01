package vn.alpaca.userservice.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.alpaca.userservice.entity.jpa.Authority;

public interface AuthorityJpaRepository extends JpaRepository<Authority, Integer> {}
