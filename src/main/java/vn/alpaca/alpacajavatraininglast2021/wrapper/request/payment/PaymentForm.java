package vn.alpaca.alpacajavatraininglast2021.wrapper.request.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentForm {

    private Integer requestId;

    private Integer accountantId;

    private Double amount;

    private Date paymentDate;

}
