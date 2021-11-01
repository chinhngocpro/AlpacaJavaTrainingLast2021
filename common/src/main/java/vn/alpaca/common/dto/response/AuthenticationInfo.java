package vn.alpaca.common.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class AuthenticationInfo implements Serializable {

  private int id;

  private String username;

  @JsonProperty("role")
  private String roleName;

  private Set<String> authorities;
}
