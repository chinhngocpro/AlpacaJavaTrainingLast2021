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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String title;

    private String description;

    @Type(type = "list-array")
    @Column(columnDefinition = "text[]")
    private List<String> receiptPhotos;

    private String status = "PENDING";

    private Integer customerId;
}
