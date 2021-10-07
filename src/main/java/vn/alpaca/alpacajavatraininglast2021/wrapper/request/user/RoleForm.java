package vn.alpaca.alpacajavatraininglast2021.wrapper.request.user;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleForm {

    private String name;

    private Set<Integer> authorityIds;

}