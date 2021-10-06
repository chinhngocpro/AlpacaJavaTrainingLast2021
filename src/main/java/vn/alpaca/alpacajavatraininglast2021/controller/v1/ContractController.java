package vn.alpaca.alpacajavatraininglast2021.controller.v1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/api/v1/contracts")
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

    @GetMapping(
            consumes = "application/json",
            produces = "application/json"
    )
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

    @GetMapping(value = "/{contractId}",
            consumes = "application/json",
            produces = "application/json"
    )
    public SuccessResponse<ContractDTO> getContractById(
            @PathVariable("contractId") int id
    ) {
        ContractDTO dto  =
                mapper.convertToDTO(service.findContractById(id));

        return new SuccessResponse<>(dto);
    }

    @PostMapping(
            consumes = "application/json",
            produces = "application/json"
    )
    public SuccessResponse<ContractDTO> createNewContract(
            @RequestBody ContractForm formData
    ) throws InvocationTargetException, IllegalAccessException {
        Contract contract = new Contract();
        notNullUtil.copyProperties(contract, formData);

        ContractDTO dto =
                mapper.convertToDTO(service.saveContract(contract));

        return new SuccessResponse<>(dto);
    }

    @PutMapping(value = "/{contractId}",
            consumes = "application/json",
            produces = "application/json"
    )
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

    @PatchMapping(value = "/{contractId}/activate",
            consumes = "application/json",
            produces = "application/json"
    )
    public SuccessResponse<Boolean> activateContract(
            @PathVariable("contractId") int id
    ) {
        service.activateContract(id);

        return new SuccessResponse<>(true);
    }


    @PatchMapping(value = "/{contractId}/deactivate",
            consumes = "application/json",
            produces = "application/json"
    )
    public SuccessResponse<Boolean> deactivateContract(
            @PathVariable("contractId") int id
    ) {
        service.deactivateContract(id);

        return new SuccessResponse<>(true);
    }
}
