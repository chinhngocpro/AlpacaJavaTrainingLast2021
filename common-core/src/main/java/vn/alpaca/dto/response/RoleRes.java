package vn.alpaca.dto.response;

import lombok.*;
import vn.alpaca.dto.request.AuthorityRes;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RoleRes {

    private int id;

    private String name;

    private Set<AuthorityRes> authorities;

}
