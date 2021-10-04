package vn.alpaca.alpacajavatraininglast2021.objects.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class AnalyzedReceipt implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private boolean isValid;

    @NotNull
    private int hospitalId;

    @NotNull
    private int accidentId;

    @NotBlank
    private String name;

    @NotNull
    private double amount;

    @ManyToOne
    @JoinColumn(name = "analyzer_id")
    private User analyzer;

    @OneToOne
    @JoinColumn(name = "request_id", referencedColumnName = "id")
    private Request request;

    @Override
    public String toString() {
        return "AnalyzedReceipt{" +
                "id=" + id +
                ", isValid=" + isValid +
                ", hospitalId=" + hospitalId +
                ", accidentId=" + accidentId +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }
}
