package vn.alpaca.alpacajavatraininglast2021.wrapper.request.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    String username;

    String password;
}
