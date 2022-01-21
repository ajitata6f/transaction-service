package com.tomatopay.transactionservice.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "currency")
    private String currency;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

    //@Enumerated(EnumType.STRING)
    //private TransactionType genre;

}
