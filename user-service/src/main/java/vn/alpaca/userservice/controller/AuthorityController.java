package vn.alpaca.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.dto.request.AuthorityRes;
import vn.alpaca.dto.wrapper.SuccessResponse;
import vn.alpaca.userservice.object.entity.Authority;
import vn.alpaca.userservice.object.mapper.AuthorityMapper;
import vn.alpaca.userservice.service.AuthorityService;
import vn.alpaca.util.ExtractParam;

import java.util.Optional;

@RestController
@RequestMapping(value = "/authorities")
@RequiredArgsConstructor
public class AuthorityController {

    private final AuthorityService service;
    private final AuthorityMapper mapper;

    @PreAuthorize("hasAuthority('AUTHORITY_READ')")
    @GetMapping
    public SuccessResponse<Page<AuthorityRes>> getAuthorities(
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "size", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy
    ) {
        Sort sort = ExtractParam.getSort(sortBy);
        Pageable pageable =
                ExtractParam.getPageable(pageNumber, pageSize, sort);

        Page<AuthorityRes> dtoPage = new PageImpl<>(
                service.findAll(pageable)
                        .map(mapper::convertToDTO)
                        .getContent()
        );

        return new SuccessResponse<>(dtoPage);
    }

    @PreAuthorize("hasAuthority('AUTHORITY_READ')")
    @GetMapping("/{id}")
    public SuccessResponse<AuthorityRes> getAuthority(
            @PathVariable("id") int id) {
        Authority authority = service.findById(id);
        AuthorityRes dto = mapper.convertToDTO(authority);
        return new SuccessResponse<>(dto);
    }
}