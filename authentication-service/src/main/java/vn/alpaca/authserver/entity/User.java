package vn.alpaca.authserver.entity;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
@Audited
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Id
    @SequenceGenerator(
            name = "users_id_seq",
            sequenceName = "users_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "users_id_seq"
    )
    private int id;

    private String username;

    private String password;

    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @ToString.Exclude
    private Role role;
}
