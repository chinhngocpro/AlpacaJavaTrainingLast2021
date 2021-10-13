package vn.alpaca.crudservice.object.entity;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "analyzed_receipts")
@Audited
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AnalyzedReceipt implements Serializable {

    @Id
    @SequenceGenerator(
            name = "analyzed_receipts_id_seq",
            sequenceName = "analyzed_receipts_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "analyzed_receipts_id_seq"
    )
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
