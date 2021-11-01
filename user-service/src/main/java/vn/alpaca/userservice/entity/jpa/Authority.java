package vn.alpaca.userservice.entity.jpa;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "authorities", schema = "user_management")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Authority implements Serializable {

    @Id
    @SequenceGenerator(
            name = "authorities_id_seq",
            sequenceName = "roles_id_seq"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "authorities_id_seq"
    )
    private int id;

    private String permissionName;
}
