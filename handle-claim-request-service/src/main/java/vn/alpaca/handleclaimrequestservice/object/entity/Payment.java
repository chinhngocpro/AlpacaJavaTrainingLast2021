package vn.alpaca.handleclaimrequestservice.object.entity;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "payments")
@Audited
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Payment implements Serializable {

    @Id
    @SequenceGenerator(
            name = "payments_id_seq",
            sequenceName = "payments_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "payments_id_seq"
    )
    private int id;

    private double amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate = new Date();

    @OneToOne
    @JoinColumn(name = "request_id")
    @ToString.Exclude
    private ClaimRequest claimRequest;
}
