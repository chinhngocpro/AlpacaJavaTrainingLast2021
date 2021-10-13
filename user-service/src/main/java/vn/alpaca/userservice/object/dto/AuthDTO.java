package vn.alpaca.userservice.object.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthDTO {
    private String username;
    private String password;
    private boolean active;
}
