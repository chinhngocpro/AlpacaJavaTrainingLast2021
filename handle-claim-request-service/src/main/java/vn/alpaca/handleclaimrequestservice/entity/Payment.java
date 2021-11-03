package vn.alpaca.handleclaimrequestservice.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "payments", schema = "claim_request_management")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private double amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate = new Date();

    @OneToOne
    @JoinColumn(name = "request_id")
    @ToString.Exclude
    private ClaimRequest claimRequest;
}
