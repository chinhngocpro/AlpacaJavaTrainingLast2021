package vn.alpaca.athenticationservice.object.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm {

    @NotBlank(message = "username_blank_error")
    String username;

    @NotBlank(message = "password_blank_error")
    String password;
}
