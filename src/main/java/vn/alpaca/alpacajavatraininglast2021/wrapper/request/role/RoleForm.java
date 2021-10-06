package vn.alpaca.alpacajavatraininglast2021.wrapper.request.role;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleForm {

    private String name;

    private Set<Integer> permissions;

}
