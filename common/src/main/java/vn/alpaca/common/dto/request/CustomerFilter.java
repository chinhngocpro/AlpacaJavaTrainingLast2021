package vn.alpaca.common.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerFilter {

    private String fullName;
    private Boolean gender;
    private String idCardNumber;
    private String email;
    private Date dobFrom;
    private Date dobTo;
    private String address;
    private Boolean active;
    private Pagination pagination = new Pagination(5);
}
