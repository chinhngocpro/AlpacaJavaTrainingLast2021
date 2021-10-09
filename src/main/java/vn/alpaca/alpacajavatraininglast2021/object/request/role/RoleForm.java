package vn.alpaca.alpacajavatraininglast2021.object.request.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleForm {

    @NotBlank(message = "blank")
    private String name;

    private Set<Integer> authorityIds;

}
