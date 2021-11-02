package vn.alpaca.common.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerClaimRequestFilter extends ClaimRequestFilter {

    private String idCardNumber;
}
