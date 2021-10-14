package vn.alpaca.handleclaimrequestservice.object.wrapper.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private int id;

    private double amount;

    private Date paymentDate;

    private int claimRequestId;

}
