package vn.alpaca.alpacajavatraininglast2021.object.dto;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RoleDTO {

    private int id;

    private String name;

    private Set<AuthorityDTO> authorities = new HashSet<>();

}
