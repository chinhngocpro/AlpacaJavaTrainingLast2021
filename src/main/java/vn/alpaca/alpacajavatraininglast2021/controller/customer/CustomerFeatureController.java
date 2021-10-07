package vn.alpaca.alpacajavatraininglast2021.controller.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.alpacajavatraininglast2021.object.dto.ClaimRequestDTO;
import vn.alpaca.alpacajavatraininglast2021.object.dto.ContractDTO;
import vn.alpaca.alpacajavatraininglast2021.object.dto.CustomerDTO;
import vn.alpaca.alpacajavatraininglast2021.object.dto.PaymentDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.ClaimRequest;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Customer;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Payment;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.ClaimRequestMapper;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.ContractMapper;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.CustomerMapper;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.PaymentMapper;
import vn.alpaca.alpacajavatraininglast2021.service.*;
import vn.alpaca.alpacajavatraininglast2021.util.NullAwareBeanUtil;
import vn.alpaca.alpacajavatraininglast2021.util.RequestParamUtil;
import vn.alpaca.alpacajavatraininglast2021.wrapper.request.claimrequest.ClaimRequestForm;
import vn.alpaca.alpacajavatraininglast2021.wrapper.response.SuccessResponse;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customer/{idCardNumber}")
public class CustomerFeatureController {

    private final CustomerService customerService;
    private final ContractService contractService;
    private final ClaimRequestService requestService;
    private final PaymentService paymentService;
    private final FileService fileService;
    private final CustomerMapper customerMapper;
    private final ContractMapper contractMapper;
    private final ClaimRequestMapper requestMapper;
    private final PaymentMapper paymentMapper;
    private final NullAwareBeanUtil notNullUtil;
    private final RequestParamUtil paramUtil;

    public CustomerFeatureController(
            CustomerService customerService,
            ContractService contractService,
            ClaimRequestService requestService,
            PaymentService paymentService,
            FileService fileService,
            CustomerMapper customerMapper,
            ContractMapper contractMapper,
            ClaimRequestMapper requestMapper,
            PaymentMapper paymentMapper,
            NullAwareBeanUtil notNullUtil,
            RequestParamUtil paramUtil
    ) {
        this.customerService = customerService;
        this.contractService = contractService;
        this.requestService = requestService;
        this.paymentService = paymentService;
        this.fileService = fileService;
        this.customerMapper = customerMapper;
        this.contractMapper = contractMapper;
        this.requestMapper = requestMapper;
        this.paymentMapper = paymentMapper;
        this.notNullUtil = notNullUtil;
        this.paramUtil = paramUtil;
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

    @GetMapping("/contract")
    public SuccessResponse<Page<ContractDTO>> getContractByCustomerId(
            @PathVariable("idCardNumber") String idCardNumber,
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "size", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy,
            @RequestParam(value = "contract-code", required = false)
                    Optional<String> contractCode,
            @RequestParam(value = "is-valid", required = false)
                    Optional<Boolean> isValid,
            @RequestParam(value = "max-amount", required = false)
                    Optional<Double> maximumAmount,
            @RequestParam(value = "remain-amount", required = false)
                    Optional<Double> remainingAmount,
            @RequestParam(value = "active", required = false)
                    Optional<Boolean> active,
            @RequestParam(value = "hospital-id", required = false)
                    Optional<Integer> hospitalId,
            @RequestParam(value = "accident-id", required = false)
                    Optional<Integer> accidentId
    ) {
        Sort sort = paramUtil.getSort(sortBy);
        Pageable pageable = paramUtil.getPageable(pageNumber, pageSize, sort);

        Page<ContractDTO> dtoPage = new PageImpl<>(
                contractService.findContractsByCustomerIdCardNumber(
                                idCardNumber,
                                contractCode.orElse(null),
                                isValid.orElse(null),
                                maximumAmount.orElse(null),
                                remainingAmount.orElse(null),
                                active.orElse(null),
                                hospitalId.orElse(null),
                                accidentId.orElse(null),
                                pageable
                        )
                        .map(contractMapper::convertToDTO)
                        .getContent()
        );


        return new SuccessResponse<>(dtoPage);
    }

    @GetMapping(value = "/claim-request")
    public SuccessResponse<Page<ClaimRequestDTO>> getClaimRequestsInfo(
            @PathVariable("idCardNumber") String idCardNumber,
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "size", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy,
            @RequestParam(value = "title", required = false)
                    Optional<String> title,
            @RequestParam(value = "description", required = false)
                    Optional<String> description,
            @RequestParam(value = "status", required = false)
                    Optional<String> status
    ) {
        Sort sort = paramUtil.getSort(sortBy);
        Pageable pageable = paramUtil.getPageable(pageNumber, pageSize, sort);

        Page<ClaimRequestDTO> dtoPage = new PageImpl<>(
                requestService.findRequestsByCustomerIdCardNumber(
                                idCardNumber,
                                title.orElse(null),
                                description.orElse(null),
                                status.orElse(null),
                                pageable
                        )
                        .map(requestMapper::covertToDTO)
                        .getContent()
        );

        return new SuccessResponse<>(dtoPage);
    }

    @GetMapping(value = "/payment/{requestId}")
    public SuccessResponse<PaymentDTO> getPaymentByRequestId(
            @PathVariable("idCardNumber") String idCardNumber,
            @PathVariable("requestId") int requestId
    ) {
        Payment payment = paymentService
                .findPaymentByRequestIdAndCustomerIdCard(
                        requestId,
                        idCardNumber
                );

        PaymentDTO dto = paymentMapper.convertToDTO(payment);

        return new SuccessResponse<>(dto);
    }

    @PostMapping(
            value = "/claim-request",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public SuccessResponse<ClaimRequestDTO> createNewClaimRequest(
            @ModelAttribute("formData") ClaimRequestForm formData,
            @PathVariable String idCardNumber)
            throws InvocationTargetException, IllegalAccessException {
        ClaimRequest request = new ClaimRequest();
        notNullUtil.copyProperties(request, formData);

        if (!ObjectUtils.isEmpty(formData.getReceiptPhotoFiles())) {
            request.setReceiptPhotos(formData.getReceiptPhotoFiles().stream()
                    .map(fileService::saveFile)
                    .filter(s -> !ObjectUtils.isEmpty(s))
                    .collect(Collectors.toList()));
        }
        Customer customer = customerService
                .findCustomerByIdCardNumber(idCardNumber);
        request.setCustomer(customer);

        ClaimRequestDTO dto =
                requestMapper.covertToDTO(requestService.saveRequest(request));

        return new SuccessResponse<>(dto);
    }
}


