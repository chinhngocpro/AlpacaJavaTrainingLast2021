package vn.alpaca.crudservice.object.wrapper.request.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.alpaca.validation.Numbers;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerForm {

    @NotBlank(message = "blank")
    private String fullName;

    @NotNull(message = "null")
    private boolean gender;

    @NotBlank(message = "blank")
    @Size(min = 9, max = 12, message = "size (min:9,max:12)")
    private String idCardNumber;

    @NotNull(message = "null")
    @Numbers(message = "wrong-format (expect: numbers, actual: strings)")
    private List<String> phoneNumbers;

    @Email(message = "wrong-format (expect: email, actual: plain string)")
    private String email;

    private Date dateOfBirth;

    private String address;

    private String occupation;
}
