package vn.alpaca.alpacajavatraininglast2021.object.request.contract;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractForm {

    private Integer customerId;

    @NotBlank(message = "blank")
    @Pattern(
            regexp = "^[a-zA-Z]{3}[0-9]{9}$",
            message = "wrong-format (pattern: ^[a-zA-Z]{3}[0-9]{9}$)"
    )
    private String contractCode;

    @NotNull(message = "null")
    private Date validFrom;

    @NotNull(message = "null")
    private Date validTo;

    @NotNull(message = "null")
    private Double maximumAmount;

    @NotNull(message = "null")
    private Double remainingAmount;

    @NotEmpty(message = "empty")
    private List<Integer> acceptableHospitalIds;

    @NotEmpty(message = "empty")
    private List<Integer> acceptableAccidentIds;
}
