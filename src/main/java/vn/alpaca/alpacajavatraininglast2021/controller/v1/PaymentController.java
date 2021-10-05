package vn.alpaca.alpacajavatraininglast2021.controller.v1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.alpacajavatraininglast2021.object.dto.PaymentDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Payment;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.PaymentMapper;
import vn.alpaca.alpacajavatraininglast2021.service.PaymentService;
import vn.alpaca.alpacajavatraininglast2021.util.NullAwareBeanUtil;
import vn.alpaca.alpacajavatraininglast2021.wrapper.response.SuccessResponse;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService service;
    private final PaymentMapper mapper;
    private final NullAwareBeanUtil notNullUtil;

    public PaymentController(PaymentService service,
            PaymentMapper mapper,
            NullAwareBeanUtil notNullUtil) {
        this.service = service;
        this.mapper = mapper;
        this.notNullUtil = notNullUtil;
    }

    @GetMapping(
            consumes = "application/json",
            produces = "application/json"
    )
    public SuccessResponse<Page<PaymentDTO>> getAllPayments(
            @RequestParam("page") Optional<Integer> pageNumber,
            @RequestParam("size") Optional<Integer> pageSize
    ) {

        Pageable pageable = Pageable.unpaged();

        if (pageNumber.isPresent()) {
            pageable = PageRequest.of(pageNumber.get(), pageSize.orElse(5));
        }

        Page<PaymentDTO> dtoPage = new PageImpl<>(
                service.findAllPayments(pageable)
                        .map(mapper::convertToDTO)
                        .getContent()
        );

        return new SuccessResponse<>(dtoPage);
    }

    @PostMapping(
            consumes = "application/json",
            produces = "application/json"
    )
    public SuccessResponse<PaymentDTO> createNewPayment(
            @RequestBody PaymentDTO paymentDTO
    ) throws InvocationTargetException, IllegalAccessException {
        Payment payment = new Payment();
        notNullUtil.copyProperties(payment, paymentDTO);

        PaymentDTO dto =
                mapper.convertToDTO(service.savePayment(payment));

        return new SuccessResponse<>(dto);
    }

    @PutMapping(
            value = "/{paymentId}",
            consumes = "application/json",
            produces = "application/json"
    )
    public SuccessResponse<PaymentDTO> updatePayment(
            @PathVariable("paymentId") int id,
            @RequestBody PaymentDTO paymentDTO
    ) throws InvocationTargetException, IllegalAccessException {
        Payment payment = service.findPaymentById(id);
        notNullUtil.copyProperties(payment, paymentDTO);

        PaymentDTO dto =
                mapper.convertToDTO(service.savePayment(payment));

        return new SuccessResponse<>(dto);
    }
}