package vn.alpaca.common.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PaymentResponse {

    private Double amount;

    @JsonProperty("claim_request_id")
    private Integer claimRequestId;
}
