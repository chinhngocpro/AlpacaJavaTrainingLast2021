package vn.alpaca.alpacajavatraininglast2021.object.request.contract;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractFilter {

    private String contractCode;
    private Boolean isValid;
    private Double maximumAmount;
    private Double remainingAmount;
    private Boolean active;
    private Integer hospitalId;
    private Integer accidentId;
}
