package vn.alpaca.alpacajavatraininglast2021.objects.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private double amount;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate = new Date();

    @ManyToOne
    @JoinColumn(name = "accountant_id")
    private User accountant;

    @OneToOne
    @JoinColumn(name = "request_id")
    private Request request;
}
