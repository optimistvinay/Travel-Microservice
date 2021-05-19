package com.mslarning.airlinems.domain;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "order_table")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long airlineOrderId;
    private Long orderId;
    //    @Enumerated(EnumType.STRING)
    private String orderState;
    //    @Enumerated(EnumType.STRING)
    private String orderStatus;
    private String sourceLocation;
    private String destinationLocation;

    private BigDecimal amount;

    private LocalDate bookingDate;
}
