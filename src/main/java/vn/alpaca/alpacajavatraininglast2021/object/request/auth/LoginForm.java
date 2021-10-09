package vn.alpaca.alpacajavatraininglast2021.object.request.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm {

    @NotBlank(message = "username_blank_error")
    String username;

    @NotBlank(message = "password_blank_error")
    String password;
}
