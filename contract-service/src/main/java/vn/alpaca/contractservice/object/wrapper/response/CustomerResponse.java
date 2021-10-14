package vn.alpaca.contractservice.object.wrapper.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CustomerResponse {

    private int id;

    private String fullName;

    private boolean gender;

    private String idCardNumber;

    private List<String> phoneNumbers;

    private String email;

    private Date dateOfBirth;

    private String address;

    private String occupation;
}
