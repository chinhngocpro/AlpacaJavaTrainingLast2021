package vn.alpaca.userservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.response.wrapper.SuccessResponse;
import vn.alpaca.userservice.object.dto.AuthorityDTO;
import vn.alpaca.userservice.object.entity.Authority;
import vn.alpaca.userservice.object.mapper.AuthorityMapper;
import vn.alpaca.userservice.service.AuthorityService;

import java.util.Optional;

import static vn.alpaca.util.ExtractParam.getPageable;
import static vn.alpaca.util.ExtractParam.getSort;

@RestController
@RequestMapping(value = "/authorities")
public class AuthorityController {

    private final AuthorityService authorityService;

    private final AuthorityMapper mapper;

    public AuthorityController(AuthorityService authorityService,
                               AuthorityMapper mapper) {
        this.authorityService = authorityService;
        this.mapper = mapper;
    }


    @GetMapping
    public SuccessResponse<Page<AuthorityDTO>> getAuthorities(
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "size", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy
    ) {
        Sort sort = getSort(sortBy);
        Pageable pageable = getPageable(pageNumber, pageSize, sort);

        Page<AuthorityDTO> dtoPage = new PageImpl<>(
                authorityService.findAll(pageable)
                        .map(mapper::convertToDTO)
                        .getContent()
        );

        return new SuccessResponse<>(dtoPage);
    }

    @GetMapping("/{id}")
    public SuccessResponse<AuthorityDTO> getAuthority(
            @PathVariable("id") int id) {
        Authority authority = authorityService.findById(id);
        AuthorityDTO dto = mapper.convertToDTO(authority);
        return new SuccessResponse<>(dto);
    }
}