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

    private boolean isValid;

    private int hospitalId;

    private int accidentId;

    private double amount;

}
