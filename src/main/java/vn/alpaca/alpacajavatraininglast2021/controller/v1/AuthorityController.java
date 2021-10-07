package vn.alpaca.alpacajavatraininglast2021.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.alpacajavatraininglast2021.object.dto.AuthorityDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Authority;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.AuthorityMapper;
import vn.alpaca.alpacajavatraininglast2021.service.AuthorityService;
import vn.alpaca.alpacajavatraininglast2021.wrapper.response.SuccessResponse;

import java.util.Optional;

@RestController
@RequestMapping(
        value = "/api/v1/authorities",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AuthorityController {

    @Autowired
    AuthorityService authorityService;

    @Autowired
    AuthorityMapper mapper;

    @PreAuthorize("hasAuthority('AUTHORITY_READ')")
    @GetMapping("/{id}")
    public SuccessResponse<AuthorityDTO> getAuthority(
            @PathVariable("id") int id) {
        Authority authority = authorityService.findById(id);
        AuthorityDTO dto = mapper.convertToDTO(authority);
        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('AUTHORITY_READ')")
    @GetMapping
    public SuccessResponse<Page<AuthorityDTO>> getAuthorities(
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "size", required = false)
                    Optional<Integer> pageSize
    ) {
        Pageable pageable = Pageable.unpaged();
        if (pageNumber.isPresent()) {
            pageable = PageRequest.of(pageNumber.get(), pageSize.orElse(5));
        }

        Page<AuthorityDTO> dtoPage = new PageImpl<>(
                authorityService.findAll(pageable)
                        .map(mapper::convertToDTO)
                        .getContent()
        );

        return new SuccessResponse<>(dtoPage);
    }
}
