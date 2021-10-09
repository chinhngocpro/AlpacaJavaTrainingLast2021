package vn.alpaca.alpacajavatraininglast2021.object.request.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
public class UserFilter {

    private String username;
    private String fullName;
    private Boolean gender;
    private String idCardNumber;
    private String email;
    private Date from;
    private Date to;
    private String address;
    private Boolean active;
}
