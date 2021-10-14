package vn.alpaca.handleclaimrequestservice.object.wrapper.request.claimrequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerClaimRequestFilter extends ClaimRequestFilter {

    private String idCardNumber;
}
