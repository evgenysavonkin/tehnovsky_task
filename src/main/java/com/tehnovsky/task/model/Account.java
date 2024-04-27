package com.tehnovsky.task.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tehnovsky.task.util.enums.Currency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private BigDecimal balance;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;
}
