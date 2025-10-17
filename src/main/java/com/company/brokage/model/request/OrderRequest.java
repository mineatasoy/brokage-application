package com.company.brokage.model.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class OrderRequest {

    String customerId;
    String assetName;
    String orderSide;
    int size;
    double price;
    String status;
    LocalDate createDate;

}
