package vn.alpaca.common.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthorityResponse {

  private int id;

  @JsonProperty("permission")
  private String permissionName;
}
