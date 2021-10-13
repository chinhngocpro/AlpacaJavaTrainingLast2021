package vn.alpaca.assetsservice.object.wrapper.request.payment;

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
public class PaymentForm {

    private Integer requestId;

    private Integer accountantId;

    @NotNull(message = "null")
    private Double amount;

    @NotNull(message = "null")
    private Date paymentDate;

}
