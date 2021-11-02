package vn.alpaca.common.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AnalyzedReceiptRequest {

    @NotNull(message = "null")
    private Integer claimRequestId;

    @NotBlank(message = "blank")
    private String title;

    @NotNull(message = "null")
    private Integer hospitalId;

    @NotNull(message = "null")
    private Integer accidentId;

    @NotNull(message = "null")
    private Double amount;

}
