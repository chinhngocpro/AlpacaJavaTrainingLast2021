package vn.alpaca.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthenticationRes {
    private Integer id;
    private String username;
    private String password;
    private Integer roleId;
    private boolean active;
}
