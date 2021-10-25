package vn.alpaca.common.security.object;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AuthRole {

    private int id;

    private String name;

    private List<AuthPermission> permissions;

}
