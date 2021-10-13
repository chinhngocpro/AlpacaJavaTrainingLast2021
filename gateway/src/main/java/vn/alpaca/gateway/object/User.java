package vn.alpaca.gateway.object;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class User {

    Integer id;

    String username;

    String password;

    private List<String> permissions;

    private boolean active = true;
}
