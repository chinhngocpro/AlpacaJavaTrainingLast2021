package vn.alpaca.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import vn.alpaca.userservice.entity.jpa.Authority;
import vn.alpaca.userservice.repository.jpa.AuthorityJpaRepository;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthorityService {

  private final AuthorityJpaRepository authorityJpaRepo;

  public List<Authority> findAll() {
    return authorityJpaRepo.findAll();
  }
}
