package vn.alpaca.alpacajavatraininglast2021.object.request.payment;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PaymentFilter {

        private Double minAmount;
        private Double maxAmount;
        private Date fromDate;
        private Date toDate;
}
