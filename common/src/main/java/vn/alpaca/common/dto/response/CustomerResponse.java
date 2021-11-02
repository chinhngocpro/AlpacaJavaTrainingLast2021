package vn.alpaca.common.dto.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
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
