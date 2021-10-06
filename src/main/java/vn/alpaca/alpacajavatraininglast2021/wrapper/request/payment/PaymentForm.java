package vn.alpaca.alpacajavatraininglast2021.wrapper.request.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.alpaca.alpacajavatraininglast2021.wrapper.request.user.UserForm;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentForm {

    private double amount;

    private Date paymentDate;

    private UserForm accountantInCharge;

}
