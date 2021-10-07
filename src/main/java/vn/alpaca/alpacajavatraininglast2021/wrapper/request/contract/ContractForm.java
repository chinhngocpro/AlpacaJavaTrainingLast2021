package vn.alpaca.alpacajavatraininglast2021.wrapper.request.contract;

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
public class ContractForm {

    private Integer customerId;

    private String contractCode;

    private Date validFrom;

    private Date validTo;

    private Double maximumAmount;

    private Double remainingAmount;

    private List<Integer> acceptableHospitalIds;

    private List<Integer> acceptableAccidentIds;
}
