package vn.alpaca.common.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class RoleRequest {

    @JsonProperty("role_name")
    @NotBlank(message = "not blank")
    private String name;

    @JsonProperty("authority_ids")
    @NotNull(message = "null")
    private Set<Integer> authorityIds;
}
