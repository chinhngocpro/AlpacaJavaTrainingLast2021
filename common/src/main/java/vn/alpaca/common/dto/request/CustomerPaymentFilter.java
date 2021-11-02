package vn.alpaca.common.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerPaymentFilter extends PaymentFilter {

    private int requestId;

    private String idCardNumber;
}
