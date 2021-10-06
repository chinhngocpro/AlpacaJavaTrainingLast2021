package vn.alpaca.alpacajavatraininglast2021.wrapper.request.analyzedreceipt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnalyzedReceiptForm {

    private String title;

    private Boolean isValid;

    private Integer hospitalId;

    private Integer accidentId;

    private Double amount;

}
