package vn.alpaca.alpacajavatraininglast2021.controller.v1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.alpacajavatraininglast2021.object.dto.ContractDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Contract;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.ContractMapper;
import vn.alpaca.alpacajavatraininglast2021.service.ContractService;
import vn.alpaca.alpacajavatraininglast2021.util.NullAwareBeanUtil;
import vn.alpaca.alpacajavatraininglast2021.wrapper.request.contract.ContractForm;
import vn.alpaca.alpacajavatraininglast2021.wrapper.response.SuccessResponse;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@RestController
@RequestMapping(
        value = "/api/v1/contracts",
        consumes = "application/json",
        produces = "application/json"
)
public class ContractController {

    private final ContractService service;
    private final ContractMapper mapper;
    private final NullAwareBeanUtil notNullUtil;

    public ContractController( ContractService service,
            ContractMapper mapper,
            NullAwareBeanUtil notNullUtil) {
        this.service = service;
        this.mapper = mapper;
        this.notNullUtil = notNullUtil;
    }

    @PreAuthorize("hasAuthority('CONTRACT_READ')")
    @GetMapping
    public SuccessResponse<Page<ContractDTO>> getAllContracts(
            @RequestParam("page") Optional<Integer> pageNumber,
            @RequestParam("size") Optional<Integer> pageSize
    ) {
        Pageable pageable = Pageable.unpaged();

        if (pageNumber.isPresent()) {
            pageable = PageRequest.of(pageNumber.get(), pageSize.orElse(5));
        }

        Page<ContractDTO> dtoPage = new PageImpl<>(
                service.findAllContracts(pageable)
                        .map(mapper::convertToDTO)
                        .getContent()
        );

        return new SuccessResponse<>(dtoPage);
    }

    @GetMapping(value = "/{contractId}")
    public SuccessResponse<ContractDTO> getContractById(
            @PathVariable("contractId") int id
    ) {
        ContractDTO dto  =
                mapper.convertToDTO(service.findContractById(id));

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('CONTRACT_CREATE')")
    @PostMapping
    public SuccessResponse<ContractDTO> createNewContract(
            @RequestBody ContractForm formData
    ) throws InvocationTargetException, IllegalAccessException {
        Contract contract = new Contract();
        notNullUtil.copyProperties(contract, formData);

        ContractDTO dto =
                mapper.convertToDTO(service.saveContract(contract));

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('CONTRACT_UPDATE')")
    @PutMapping(value = "/{contractId}")
    public SuccessResponse<ContractDTO> updateContract(
            @PathVariable("contractId") int id,
            @RequestBody ContractForm formData
    ) throws InvocationTargetException, IllegalAccessException {
        Contract contract = service.findContractById(id);
        notNullUtil.copyProperties(contract, formData);

        ContractDTO dto =
                mapper.convertToDTO(service.saveContract(contract));

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('CONTRACT_UPDATE')")
    @PatchMapping(value = "/{contractId}/activate")
    public SuccessResponse<Boolean> activateContract(
            @PathVariable("contractId") int id
    ) {
        service.activateContract(id);

        return new SuccessResponse<>(true);
    }


    @PreAuthorize("hasAuthority('CONTRACT_UPDATE')")
    @PatchMapping(value = "/{contractId}/deactivate")
    public SuccessResponse<Boolean> deactivateContract(
            @PathVariable("contractId") int id
    ) {
        service.deactivateContract(id);

        return new SuccessResponse<>(true);
    }
}
