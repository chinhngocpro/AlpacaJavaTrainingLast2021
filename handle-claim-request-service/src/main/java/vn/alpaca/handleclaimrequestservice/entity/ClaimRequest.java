package vn.alpaca.handleclaimrequestservice.entity;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "claim_requests", schema = "claim_request_management")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
public class ClaimRequest implements Serializable {

    @Id
    @SequenceGenerator(name = "claim_requests_id_seq",
            sequenceName = "claim_requests_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "claim_requests_id_seq")
    private int id;

    @Column(columnDefinition = "varchar(100)")
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @Type(type = "list-array")
    @Column(columnDefinition = "text[]")
    private List<String> receiptPhotos;

    @Column(columnDefinition = "varchar(20)")
    private String status = "PENDING";

    @Column(columnDefinition = "varchar(100)")
    private Integer customerId;
}
