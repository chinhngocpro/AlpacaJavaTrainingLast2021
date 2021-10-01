package vn.alpaca.alpacajavatraininglast2021.objects.entities;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    @Type(type = "list-array")
    @Column(columnDefinition = "text[]")
    private List<String> receiptPhotos;

    private String status = "PENDING";

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private User employeeInCharge;

    @OneToOne(mappedBy = "request")
    private AnalyzedReceipt analyzedReceipt;

    @OneToOne(mappedBy = "request")
    private Payment payment;

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
