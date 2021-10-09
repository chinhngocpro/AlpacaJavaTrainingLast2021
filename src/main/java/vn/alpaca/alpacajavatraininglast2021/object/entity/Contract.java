package vn.alpaca.alpacajavatraininglast2021.object.entity;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "contracts")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
@Audited
public class Contract implements Serializable {

    @Id
    @SequenceGenerator(
            name = "contracts_id_seq",
            sequenceName = "contracts_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "contracts_id_seq"
    )
    private int id;

    @Pattern(regexp = "^[a-zA-Z]{3}[0-9]{9}$")
    private String contractCode;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Temporal(TemporalType.TIMESTAMP)
    private Date validFrom = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date validTo;

    private Double maximumAmount;

    private Double remainingAmount;

    private boolean active = true;

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
