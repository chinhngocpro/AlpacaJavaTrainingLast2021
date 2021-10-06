package vn.alpaca.alpacajavatraininglast2021.wrapper.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.alpaca.alpacajavatraininglast2021.object.dto.RoleDTO;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserForm {

    private String username;

    private String password;

    private String fullName;

    private Boolean gender;

    private String idCardNumber;

    private List<String> phoneNumbers;

    private String email;

    private Date dateOfBirth;

    private String address;

    private Boolean active;

    private RoleDTO role;
}
