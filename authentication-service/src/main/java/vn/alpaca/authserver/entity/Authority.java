package vn.alpaca.authserver.entity;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
@Audited
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Authority {

    @Id
    @SequenceGenerator(
            name = "authorities_id_seq",
            sequenceName = "authorities_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "authorities_id_seq"
    )
    private int id;

    private String permissionName;
}
