package vn.alpaca.common.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ContractFilter {

    private String contractCode;

    private Boolean isValid;

    private Double maximumAmount;

    private Double remainingAmount;

    private Boolean active;

    private Integer hospitalId;

    private Integer accidentId;

    private Pagination pagination = new Pagination(5);
}
