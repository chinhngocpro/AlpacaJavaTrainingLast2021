package vn.alpaca.dto.request;

import lombok.Getter;
import lombok.Setter;
import vn.alpaca.validation.Numbers;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserReq {

    @NotBlank(message = "blank")
    private String username;

    @NotBlank(message = "blank")
    private String password;

    @NotBlank(message = "blank")
    private String fullName;

    @NotNull(message = "null")
    private Boolean gender;

    @NotBlank(message = "blank")
    @Size(min = 9, max = 12, message = "size (min:9, max:12)")
    private String idCardNumber;

    @NotEmpty(message = "empty")
    @Numbers(message = "wrong-format (expect: numbers, actual: strings)")
    private List<String> phoneNumbers;

    @NotBlank(message = "blank")
    @Email(message = "wrong-format (expect: email, actual: plain string)")
    private String email;

    private Date dateOfBirth;

    private String address;

    private Integer roleId;
}
