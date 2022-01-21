package com.tomatopay.transactionservice.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "account")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "balance")
    private BigDecimal balance;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;

}
