package vn.alpaca.athenticationservice.object.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm {

    @NotBlank(message = "blank")
    String username;

    @NotBlank(message = "blank")
    String password;
}
