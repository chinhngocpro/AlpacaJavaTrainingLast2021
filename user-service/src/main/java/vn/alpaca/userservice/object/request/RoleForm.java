package vn.alpaca.userservice.object.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
public class RoleForm {

    @NotBlank(message = "blank")
    private String name;

    private Set<Integer> authorityIds;

}
