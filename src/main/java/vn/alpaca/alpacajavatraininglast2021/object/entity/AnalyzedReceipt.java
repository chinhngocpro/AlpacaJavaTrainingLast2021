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

    @NotNull
    private boolean isValid;

    @NotNull
    private int hospitalId;

    @NotNull
    private int accidentId;

    @NotBlank
    private String title;

    @NotNull
    private double amount;

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
                ", hospitalId=" + hospitalId +
                ", accidentId=" + accidentId +
                ", title='" + title + '\'' +
                ", amount=" + amount +
                '}';
    }
}
