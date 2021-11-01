package vn.alpaca.userservice.entity.jpa;

import lombok.*;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "roles", schema = "user_management")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role implements Serializable {

  @Id
  @SequenceGenerator(name = "roles_id_seq", sequenceName = "roles_id_seq")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_id_seq")
  private int id;

  private String name;

  @ToString.Exclude
  @ManyToMany(
      cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE},
      fetch = FetchType.EAGER)
  @JoinTable(
      name = "roles_authorities",
      schema = "user_management",
      joinColumns = @JoinColumn(name = "role_id"),
      inverseJoinColumns = @JoinColumn(name = "authority_id"))
  private Set<Authority> authorities;

  public void addAuthority(Authority authority) {
    if (ObjectUtils.isEmpty(authorities)) {
      authorities = new LinkedHashSet<>();
    }

    authorities.add(authority);
  }
}
