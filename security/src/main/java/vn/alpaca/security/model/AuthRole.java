package vn.alpaca.security.model;

import lombok.*;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class AuthRole implements Serializable {

    private int id;

    private String name;

    private Set<AuthPermission> authorities;

    public void addAuthority(AuthPermission permission) {
        if (ObjectUtils.isEmpty(authorities)) {
            authorities = new LinkedHashSet<>();
        }

        authorities.add(permission);
    }
}
