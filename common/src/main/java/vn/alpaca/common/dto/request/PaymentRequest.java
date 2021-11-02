package vn.alpaca.common.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class PaymentRequest {

    private Integer requestId;

    @NotNull(message = "null")
    private Double amount;

    @NotNull(message = "null")
    private Date paymentDate;

}
