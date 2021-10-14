package vn.alpaca.handleclaimrequestservice.object.wrapper.request.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    private Integer requestId;

    @NotNull(message = "null")
    private Double amount;

    @NotNull(message = "null")
    private Date paymentDate;

}
