package com.company.brokage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/*customerId, assetName, orderSide, size, price, status, createDate**/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    String customerId;
    String assetName;
    String orderSide;
    int size;
    double price;
    String status;
    LocalDate createDate;

}

