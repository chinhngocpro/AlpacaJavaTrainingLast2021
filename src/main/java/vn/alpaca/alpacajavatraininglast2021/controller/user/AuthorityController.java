package vn.alpaca.alpacajavatraininglast2021.controller.user;

import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.alpacajavatraininglast2021.object.dto.AuthorityDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Authority;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.AuthorityMapper;
import vn.alpaca.alpacajavatraininglast2021.object.response.SuccessResponse;
import vn.alpaca.alpacajavatraininglast2021.service.AuthorityService;
import static vn.alpaca.alpacajavatraininglast2021.util.RequestParamUtil.*;

import java.util.Optional;

@RestController
@RequestMapping(
        value = "/api/user/authorities",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AuthorityController {

    private final AuthorityService authorityService;

    private final AuthorityMapper mapper;

    public AuthorityController(AuthorityService authorityService,
                               AuthorityMapper mapper) {
        this.authorityService = authorityService;
        this.mapper = mapper;
    }

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
}
