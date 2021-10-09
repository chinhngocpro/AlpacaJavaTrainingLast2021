package vn.alpaca.alpacajavatraininglast2021.controller.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.alpacajavatraininglast2021.object.dto.ClaimRequestDTO;
import vn.alpaca.alpacajavatraininglast2021.object.dto.ContractDTO;
import vn.alpaca.alpacajavatraininglast2021.object.dto.CustomerDTO;
import vn.alpaca.alpacajavatraininglast2021.object.dto.PaymentDTO;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.ClaimRequestMapper;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.ContractMapper;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.CustomerMapper;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.PaymentMapper;
import vn.alpaca.alpacajavatraininglast2021.object.request.claimrequest.ClaimRequestFilter;
import vn.alpaca.alpacajavatraininglast2021.object.request.claimrequest.ClaimRequestForm;
import vn.alpaca.alpacajavatraininglast2021.object.request.contract.ContractFilter;
import vn.alpaca.alpacajavatraininglast2021.object.request.payment.PaymentFilter;
import vn.alpaca.alpacajavatraininglast2021.object.response.SuccessResponse;
import vn.alpaca.alpacajavatraininglast2021.service.ClaimRequestService;
import vn.alpaca.alpacajavatraininglast2021.service.ContractService;
import vn.alpaca.alpacajavatraininglast2021.service.CustomerService;
import vn.alpaca.alpacajavatraininglast2021.service.PaymentService;

import java.util.Optional;

import static vn.alpaca.alpacajavatraininglast2021.util.RequestParamUtil.getPageable;
import static vn.alpaca.alpacajavatraininglast2021.util.RequestParamUtil.getSort;

@RestController
@RequestMapping("/api/customer/{idCardNumber}")
public class CustomerFeatureController {

    private final CustomerService customerService;
    private final ContractService contractService;
    private final ClaimRequestService requestService;
    private final PaymentService paymentService;
    private final CustomerMapper customerMapper;
    private final ContractMapper contractMapper;
    private final PaymentMapper paymentMapper;
    private final ClaimRequestMapper requestMapper;

    public CustomerFeatureController(
            CustomerService customerService,
            ContractService contractService,
            ClaimRequestService requestService,
            PaymentService paymentService,
            CustomerMapper customerMapper,
            ContractMapper contractMapper,
            PaymentMapper paymentMapper,

            ClaimRequestMapper requestMapper) {
        this.customerService = customerService;
        this.contractService = contractService;
        this.requestService = requestService;
        this.paymentService = paymentService;
        this.customerMapper = customerMapper;
        this.contractMapper = contractMapper;
        this.paymentMapper = paymentMapper;
        this.requestMapper = requestMapper;
    }

    @GetMapping("/info")
    public SuccessResponse<CustomerDTO> getInfo(
            @PathVariable("idCardNumber") String idCardNumber
    ) {
        CustomerDTO dto = customerMapper
                .convertToDTO(customerService.findCustomerByIdCardNumber(
                        idCardNumber));

        return new SuccessResponse<>(dto);
    }

    @GetMapping("/contracts")
    public SuccessResponse<Page<ContractDTO>> getContractsByCustomerId(
            @PathVariable("idCardNumber") String idCardNumber,
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "size", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy,
            @RequestBody ContractFilter filter

    ) {
        Sort sort = getSort(sortBy);
        Pageable pageable = getPageable(pageNumber, pageSize, sort);

        Page<ContractDTO> dtoPage = new PageImpl<>(
                contractService.findContractsByCustomerIdCardNumber(
                                idCardNumber,
                                filter,
                                pageable
                        )
                        .map(contractMapper::convertToDTO)
                        .getContent()
        );


        return new SuccessResponse<>(dtoPage);
    }

    @GetMapping(value = "/claim-requests")
    public SuccessResponse<Page<ClaimRequestDTO>> getClaimRequestsInfo(
            @PathVariable("idCardNumber") String idCardNumber,
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "size", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy,
            @RequestBody ClaimRequestFilter filter
    ) {
        Sort sort = getSort(sortBy);
        Pageable pageable = getPageable(pageNumber, pageSize, sort);

        Page<ClaimRequestDTO> dtoPage = new PageImpl<>(
                requestService.findRequestsByCustomerIdCardNumber(
                                idCardNumber,
                                filter,
                                pageable
                        )
                        .map(requestMapper::covertToDTO)
                        .getContent()
        );

        return new SuccessResponse<>(dtoPage);
    }

    @GetMapping(value = "/payments/{requestId}")
    public SuccessResponse<Page<PaymentDTO>> getPaymentByRequestId(
            @PathVariable("idCardNumber") String idCardNumber,
            @PathVariable("requestId") int requestId,
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "size", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy,
            @RequestBody PaymentFilter filter
    ) {
        Sort sort = getSort(sortBy);
        Pageable pageable = getPageable(pageNumber, pageSize, sort);

        Page<PaymentDTO> dtoPage = new PageImpl<>(
                paymentService
                        .findPaymentsByRequestIdAndCustomerIdCard(
                                requestId,
                                idCardNumber,
                                filter,
                                pageable
                        )
                        .map(paymentMapper::convertToDTO)
                        .getContent()
        );

        return new SuccessResponse<>(dtoPage);
    }

    @PostMapping(
            value = "/claim-requests",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public SuccessResponse<ClaimRequestDTO> createNewClaimRequest(
            @ModelAttribute("formData") ClaimRequestForm formData,
            @PathVariable String idCardNumber
    ) {

        ClaimRequestDTO dto = requestMapper
                .covertToDTO(
                        requestService.saveRequest(idCardNumber, formData));

        return new SuccessResponse<>(dto);
    }
}


