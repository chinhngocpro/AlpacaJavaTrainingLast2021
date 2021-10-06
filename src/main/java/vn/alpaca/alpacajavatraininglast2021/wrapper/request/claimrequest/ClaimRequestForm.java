package vn.alpaca.alpacajavatraininglast2021.wrapper.request.claimrequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.alpaca.alpacajavatraininglast2021.object.dto.CustomerDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClaimRequestForm {
    
    private String title;

    private String description;

    private List<String> receiptPhotos;

    private String status;

    private CustomerDTO customer;

}
