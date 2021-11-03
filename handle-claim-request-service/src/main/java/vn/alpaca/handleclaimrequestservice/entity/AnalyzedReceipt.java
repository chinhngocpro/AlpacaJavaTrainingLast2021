package vn.alpaca.handleclaimrequestservice.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "analyzed_receipts", schema = "claim_request_management")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AnalyzedReceipt implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private boolean isValid = true;

    private String title;

    private double amount;

    private int hospitalId;

    private int accidentId;

    @OneToOne
    @JoinColumn(name = "request_id", referencedColumnName = "id")
    @ToString.Exclude
    private ClaimRequest claimRequest;
}
