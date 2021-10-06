package vn.alpaca.alpacajavatraininglast2021.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.alpaca.alpacajavatraininglast2021.exception.ResourceNotFoundException;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Authority;
import vn.alpaca.alpacajavatraininglast2021.repository.AuthorityRepository;

import java.util.Optional;

@Service
public class AuthorityService {

    @Autowired
    AuthorityRepository authorityRepository;

    public Authority findById(int id) {
        Optional<Authority> authority = authorityRepository.findById(id);
        return authority.orElseThrow(() -> new ResourceNotFoundException());
    }
}
