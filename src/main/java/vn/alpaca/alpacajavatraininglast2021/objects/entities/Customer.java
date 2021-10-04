package vn.alpaca.alpacajavatraininglast2021.objects.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fullName;

    private boolean gender;

    private String idCardNumber;

    @Type(type = "list-array")
    @Column(columnDefinition = "text[]")
    private List<String> phoneNumbers;

    private String email;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    private String address;

    private String occupation;

    private boolean active;

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
    private List<Request> requests;

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
