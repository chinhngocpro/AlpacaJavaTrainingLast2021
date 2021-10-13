package vn.alpaca.assetsservice.object.wrapper.request.analyzedreceipt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnalyzedReceiptForm {

    private Integer claimRequestId;

    private Integer analyzerId;

    @NotNull(message = "null")
    private String title;

    @NotNull(message = "null")
    private Integer hospitalId;

    @NotNull(message = "null")
    private Integer accidentId;

    @NotNull(message = "null")
    private Double amount;

}
