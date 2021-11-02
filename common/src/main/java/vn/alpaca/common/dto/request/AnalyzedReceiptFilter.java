package vn.alpaca.common.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AnalyzedReceiptFilter {

    private Boolean isValid;
    private String title;
    private Integer hospitalId;
    private Integer accidentId;
    private Double minAmount;
    private Double maxAmount;

    private Pagination pagination = new Pagination(5);
}
