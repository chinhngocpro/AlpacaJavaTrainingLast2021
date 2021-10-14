package vn.alpaca.sendclaimrequestservice.object.wrapper.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CustomerFilter {

    private String fullName;
    private Boolean gender;
    private String idCardNumber;
    private String email;
    private Date dobFrom;
    private Date dobTo;
    private String address;
    private Boolean active;
}
