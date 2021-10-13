package vn.alpaca.userservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.alpaca.userservice.object.entity.Authority;
import vn.alpaca.response.exception.ResourceNotFoundException;
import vn.alpaca.userservice.repository.AuthorityRepository;

@Service
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public Page<Authority> findAll(Pageable pageable) {
        return authorityRepository.findAll(pageable);
    }

    public Authority findById(int id) {
        return authorityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "There's no authority match with id " + id
                ));
    }
}
