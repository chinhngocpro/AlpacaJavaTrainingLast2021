package vn.alpaca.sendclaimrequestservice.object.entity;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "claim_requests")
@Audited
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
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

    private String title;

    private String description;

    @Type(type = "list-array")
    @Column(columnDefinition = "text[]")
    private List<String> receiptPhotos;

    private String status = "PENDING";

    @ToString.Exclude
    private Integer customerId;
}
