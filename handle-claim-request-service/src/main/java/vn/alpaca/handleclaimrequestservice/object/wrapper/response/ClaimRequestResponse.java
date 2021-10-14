package vn.alpaca.handleclaimrequestservice.object.wrapper.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClaimRequestResponse {

    private int id;

    private String title;

    private String description;

    private List<String> receiptPhotos;

    private String status;

}
