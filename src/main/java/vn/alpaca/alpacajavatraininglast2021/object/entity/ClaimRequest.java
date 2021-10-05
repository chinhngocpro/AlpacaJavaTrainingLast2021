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
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "claim_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
@Audited
public class ClaimRequest implements Serializable {

    @Id
    @SequenceGenerator(
            name = "claim_requests_id_seq",
            sequenceName = "claim_requests_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "claim_requests_id_seq"
    )
    private int id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @Type(type = "list-array")
    @Column(columnDefinition = "text[]")
    private List<String> receiptPhotos;

    @NotBlank
    private String status = "PENDING";

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private User employeeInCharge;

    @OneToOne(mappedBy = "claimRequest")
    private AnalyzedReceipt analyzedReceipt;

    @OneToOne(mappedBy = "claimRequest")
    private Payment payment;

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
