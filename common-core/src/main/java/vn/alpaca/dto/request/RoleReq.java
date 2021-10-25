package vn.alpaca.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
public class RoleReq {

    @NotBlank(message = "blank")
    private String name;

    private Set<Integer> authorityIds;

}
