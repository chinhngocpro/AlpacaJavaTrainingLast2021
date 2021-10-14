package vn.alpaca.handleclaimrequestservice.object.wrapper.request.analyzedreceipt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalyzedReceiptFilter {

    private Boolean isValid;
    private String title;
    private Integer hospitalId;
    private Integer accidentId;
    private Double minAmount;
    private Double maxAmount;
}
