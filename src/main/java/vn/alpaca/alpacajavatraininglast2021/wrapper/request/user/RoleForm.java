package vn.alpaca.alpacajavatraininglast2021.wrapper.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleForm {

    private String name;

    private Set<Integer> authorityIds;

}
