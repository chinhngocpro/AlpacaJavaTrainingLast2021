package vn.alpaca.security.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthPermission implements Serializable {

  private int id;

  private String permissionName;
}
