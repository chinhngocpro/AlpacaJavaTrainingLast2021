package vn.alpaca.alpacajavatraininglast2021.object.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "analyzed_receipts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Audited
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

    @ManyToOne
    @JoinColumn(name = "analyzer_id")
    private User analyzer;

    @OneToOne
    @JoinColumn(name = "request_id", referencedColumnName = "id")
    private ClaimRequest claimRequest;

    @Override
    public String toString() {
        return "AnalyzedReceipt{" +
                "id=" + id +
                ", isValid=" + isValid +
                ", title='" + title + '\'' +
                ", amount=" + amount +
                ", hospitalId=" + hospitalId +
                ", accidentId=" + accidentId +
                '}';
    }
}
