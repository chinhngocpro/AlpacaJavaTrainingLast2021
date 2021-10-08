package vn.alpaca.alpacajavatraininglast2021.wrapper.request.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm {
    String username;

    String password;
}
