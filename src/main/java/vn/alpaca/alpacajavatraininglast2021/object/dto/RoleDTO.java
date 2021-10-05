package vn.alpaca.alpacajavatraininglast2021.object.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RoleDTO {

    private int id;

    private String name;

    private Set<String> permissions;

}
