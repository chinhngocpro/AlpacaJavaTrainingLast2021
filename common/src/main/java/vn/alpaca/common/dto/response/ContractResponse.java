package vn.alpaca.common.dto.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ContractResponse {

    private int id;

    private String contractCode;

    private Date validFrom;

    private Date validTo;

    private Double maximumAmount;

    private Double remainingAmount;

    private List<Integer> acceptableHospitalIds;

    private List<Integer> acceptableAccidentIds;
}
