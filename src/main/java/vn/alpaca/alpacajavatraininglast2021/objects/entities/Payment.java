package vn.alpaca.alpacajavatraininglast2021.objects.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;

    @ManyToOne
    @JoinColumn(name = "accountant_id")
    private User accountant;

    @OneToOne
    @JoinColumn(name = "request_id")
    private Request request;
}
