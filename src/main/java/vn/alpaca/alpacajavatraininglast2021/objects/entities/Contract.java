package vn.alpaca.alpacajavatraininglast2021.objects.entities;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "contracts")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
public class Contract implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String contractCode;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date validFrom = new Date();

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date validTo;

    @NotNull
    private Double maximumAmount;

    @NotNull
    private Double remainingAmount;

    @NotNull
    private boolean active;

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
