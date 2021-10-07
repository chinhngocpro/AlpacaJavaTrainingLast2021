package vn.alpaca.alpacajavatraininglast2021.object.dto;

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

    private String fullName;

    private boolean gender;

    private String idCardNumber;

    private List<String> phoneNumbers;

    private String email;

    private Date dateOfBirth;

    private String address;

    private RoleDTO role;

}
