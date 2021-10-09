package vn.alpaca.alpacajavatraininglast2021.object.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractDTO {

    private int id;

    private String contractCode;

    private CustomerDTO customer;

    private Date validFrom;

    private Date validTo;

    private Double maximumAmount;

    private Double remainingAmount;

    private List<Integer> acceptableHospitalIds;

    private List<Integer> acceptableAccidentIds;
}
