package vn.alpaca.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.alpaca.common.dto.response.AuthorityResponse;
import vn.alpaca.common.dto.wrapper.AbstractResponse;
import vn.alpaca.common.dto.wrapper.SuccessResponse;
import vn.alpaca.userservice.mapper.AuthorityMapper;
import vn.alpaca.userservice.service.AuthorityService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/authorities")
@RequiredArgsConstructor
public class AuthorityController {

  private final AuthorityService service;
  private final AuthorityMapper mapper;

  @GetMapping
  AbstractResponse getAllAuthorities() {
    List<AuthorityResponse> response =
        service.findAll().stream()
            .map(mapper::authorityToAuthorityResponse)
            .collect(Collectors.toList());

    return new SuccessResponse<>(response);
  }
}
