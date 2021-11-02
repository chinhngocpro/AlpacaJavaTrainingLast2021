package vn.alpaca.common.dto.request;

import lombok.Data;
import vn.alpaca.common.validation.Numbers;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
public class CustomerRequest {

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
