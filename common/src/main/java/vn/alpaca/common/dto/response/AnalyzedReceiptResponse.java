package vn.alpaca.common.dto.response;

import lombok.Data;

@Data
public class AnalyzedReceiptResponse {

    private String title;

    private double amount;

    private int hospitalId;

    private int accidentId;
}
