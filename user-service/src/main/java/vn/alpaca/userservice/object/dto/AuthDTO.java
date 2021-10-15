package vn.alpaca.userservice.object.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthDTO {
    private Integer id;
    private String username;
    private String password;
    private Integer roleId;
    private boolean active;
}
