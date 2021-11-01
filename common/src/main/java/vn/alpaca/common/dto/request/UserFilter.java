package vn.alpaca.common.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserFilter {

    private String username;

    @JsonProperty("full_name")
    private String fullName;

    private Boolean gender;

    @JsonProperty("id_card_number")
    private String idCardNumber;

    @JsonProperty("dob_from")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date from;

    @JsonProperty("dob_to")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date to;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String email;

    private String address;

    private Boolean active;

    private Pagination pagination = new Pagination(5);

}
