package vn.alpaca.entity;

import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customers")
@Audited
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer implements Serializable {

    @Id
    @SequenceGenerator(
            name = "customers_id_seq",
            sequenceName = "customers_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customers_id_seq"
    )
    private int id;

    private String fullName;

    private boolean gender;

    private String idCardNumber;

    @Type(type = "list-array")
    @Column(columnDefinition = "text[]")
    private List<String> phoneNumbers;

    private String email;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    private String address;

    private String occupation;

    private boolean active = true;
}