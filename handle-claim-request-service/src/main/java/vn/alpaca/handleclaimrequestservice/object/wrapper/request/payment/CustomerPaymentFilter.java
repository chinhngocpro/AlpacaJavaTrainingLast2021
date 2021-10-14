package vn.alpaca.handleclaimrequestservice.object.wrapper.request.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPaymentFilter extends PaymentFilter {

    private int requestId;

    private String idCardNumber;
}
