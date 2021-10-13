package vn.alpaca.crudservice.object.wrapper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private int id;

    private double amount;

    private Date paymentDate;

    private int claimRequestId;

}
