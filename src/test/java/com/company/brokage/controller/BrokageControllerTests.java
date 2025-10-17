package com.company.brokage.controller;

import com.company.brokage.model.Order;
import com.company.brokage.model.request.OrderRequest;
import com.company.brokage.model.response.OrderResponse;
import com.company.brokage.service.BrokageService;
import com.company.brokage.util.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BrokageControllerTests {

    @Mock
    private BrokageService brokageService;

    @InjectMocks
    private BrokageController brokageController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrderTest() {

        Order order = Order.builder()
                .orderId(Long.valueOf("111111"))
                .assetName("ASSET1")
                .orderSide("SELL")
                .size(1)
                .createDate(LocalDate.now())
                .price(100)
                .customerId("34232322")
                .build();

        OrderResponse response = OrderResponse.builder()
                .order(order)
                .message(Constants.ORDER_CREATED_SUCCESSFULLY)
                .build();

        when(brokageService.createOrder((any(Order.class)))).thenReturn(response);

        Assertions.assertNotNull(brokageController.createOrder(OrderRequest.builder().build()));

    }

}
