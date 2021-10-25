package vn.alpaca.authserver.entity;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Audited
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role implements Serializable {

    @Id
    @SequenceGenerator(
            name = "roles_id_seq",
            sequenceName = "roles_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "roles_id_seq"
    )
    private int id;

    private String name;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH
    }, fetch = FetchType.EAGER)
    @JoinTable(name = "roles_authorities",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    @ToString.Exclude
    private Set<Authority> authorities;

    public void addAuthority(@NonNull Authority authority) {
        if (authorities == null) {
            authorities = new HashSet<>();
        }
        authorities.add(authority);
    }
}
