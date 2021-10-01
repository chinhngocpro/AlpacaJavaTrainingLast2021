package vn.alpaca.alpacajavatraininglast2021.objects.entities;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "Contracts")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String contractCode;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar validFrom;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar validTo;

    private Double maximumAmount;

    private Double remainingAmount;

    @Type(type = "list-array")
    @Column(columnDefinition = "integer[]")
    private List<Integer> acceptableHospitalIds;

    @Type(type = "list-array")
    @Column(columnDefinition = "integer[]")
    private List<Integer> acceptableAccidentIds;

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", contractCode='" + contractCode + '\'' +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                ", maximumAmount=" + maximumAmount +
                ", remainingAmount=" + remainingAmount +
                '}';
    }
}
