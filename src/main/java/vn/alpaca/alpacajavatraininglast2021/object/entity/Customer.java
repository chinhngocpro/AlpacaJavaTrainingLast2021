package vn.alpaca.alpacajavatraininglast2021.object.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class Customer implements Serializable {

    @Id
    @SequenceGenerator(
            name = "customers_id_seq",
            sequenceName = "customers_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customers_id_seq"
    )
    private int id;

    @NotBlank
    private String fullName;

    @NotNull
    private boolean gender;

    @NotBlank
    @Size(min = 9, max = 12)
    private String idCardNumber;

    @Type(type = "list-array")
    @Column(columnDefinition = "text[]")
    private List<String> phoneNumbers;

    @NotBlank
    private String email;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @NotBlank
    private String address;

    @NotBlank
    private String occupation;

    @NotNull
    private boolean active = true;

    @OneToMany(mappedBy = "customer",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH
            })
    private List<Contract> contracts;

    @OneToMany(mappedBy = "customer",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH
            })
    private List<ClaimRequest> claimRequests;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", gender=" + gender +
                ", idCardNumber='" + idCardNumber + '\'' +
                ", phoneNumbers=" + phoneNumbers +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", occupation='" + occupation + '\'' +
                '}';
    }
}
