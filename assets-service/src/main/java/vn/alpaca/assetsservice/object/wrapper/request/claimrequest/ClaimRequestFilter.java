package vn.alpaca.assetsservice.object.wrapper.request.claimrequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClaimRequestFilter {

    private String title;
    private String description;
    private String status;
}
