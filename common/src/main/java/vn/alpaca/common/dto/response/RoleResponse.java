package vn.alpaca.common.dto.response;

import lombok.Data;

import java.util.Set;

@Data
public class RoleResponse {

  private int id;

  private String name;

  private Set<AuthorityResponse> authorities;
}
