package vn.alpaca.assetsservice.object.wrapper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnalyzedReceiptDTO {

    private int id;

    private String title;

    private int hospitalId;

    private int accidentId;

    private double amount;

}
