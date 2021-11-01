package vn.alpaca.common.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.Set;

@Data
public class UserRequest {

    @NotBlank(message = "blank")
    @Size(min = 6, max = 50, message = "size (min:6, max:50)")
    private String username;

    @NotBlank(message = "not blank")
    @Size(min = 6, max = 50, message = "size (min:6, max:50)")
    private String password;

    @JsonProperty("full_name")
    @NotBlank(message = "not blank")
    private String fullName;

    @NotNull(message = "null")
    private boolean gender;

    @JsonProperty("id_card_number")
    @NotBlank(message = "not blank")
    @Size(min = 9, max = 12, message = "size (min:9, max:12)")
    private String idCardNumber;

    @JsonProperty("date_of_birth")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "null")
    private Date dateOfBirth;

    @JsonProperty("phone_numbers")
    @NotEmpty(message = "empty")
    private Set<String> phoneNumbers;

    @NotBlank(message = "not blank")
    @Email(message = "wrong format (expected: email)")
    private String email;

    @NotBlank(message = "not blank")
    private String address;

    @JsonProperty("role_id")
    @NotNull(message = "null")
    private Integer roleId;
}
