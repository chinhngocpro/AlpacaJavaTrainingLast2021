package vn.alpaca.common.dto.request;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String username;
    private String password;
}
