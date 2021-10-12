package vn.alpaca.userservice.object.wrapper.response;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {

    private int id;

    private String username;

    private String role;

    private String fullName;

    private boolean gender;

    private String idCardNumber;

    private List<String> phoneNumbers;

    private String email;

    private Date dateOfBirth;

    private String address;

}
