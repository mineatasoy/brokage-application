package com.company.brokage.controller;

import com.company.brokage.model.Asset;
import com.company.brokage.model.Order;
import com.company.brokage.model.request.AssetRequest;
import com.company.brokage.model.request.OrderRequest;
import com.company.brokage.model.response.AssetResponse;
import com.company.brokage.model.response.OrderResponse;
import com.company.brokage.service.BrokageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api")
public class BrokageController {

/*
Create Order: Create a new order for a given customer, asset, side, size and price
Side can be BUY or SELL. Customer is a unique id for a customer. Asset is the name of the stock customer wants to buy. Size represents how many shares customer wants to buy. Price represents how much customer wants to pay for per share.
List Orders: List orders for a given customer and date range. (you can add more filter if you want)
Delete Order: Cancel a pending order. Other status orders cannot be deleted
Deposit Money: Deposit TRY for a given customer and amount
Withdraw Money: Withdraw TRY for a given customer amount and IBAN
List Assets: List all assets for a given customer (you can add filters if you want)
 */

    @Autowired
    BrokageService brokageService;

    @PostMapping("/orders/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        Order order = Order.builder()
                .customerId(orderRequest.getCustomerId())
                .assetName(orderRequest.getAssetName())
                .orderSide(orderRequest.getOrderSide())
                .size(orderRequest.getSize())
                .price(orderRequest.getPrice())
                .status(orderRequest.getStatus())
                .createDate(orderRequest.getCreateDate()).build();
        return new ResponseEntity<>(brokageService.createOrder(order), HttpStatus.OK);

    }

    @GetMapping("orders")
    public ResponseEntity<List<Order>> getOrders(
            @RequestParam String customerId,
            @RequestParam LocalDate dateStart,
            @RequestParam LocalDate dateEnd ) {

        return new ResponseEntity<>(brokageService.getOrdersByCustomerWithDate(customerId, dateStart, dateEnd), HttpStatus.OK);

    }

    @DeleteMapping("/orders/delete")
    public ResponseEntity<OrderResponse> deleteOrder(@RequestParam String orderId) {

        return new ResponseEntity<>(brokageService.cancelOrder(orderId), HttpStatus.OK);

    }

    @GetMapping("assets/{customerId}")
    public ResponseEntity<List<Asset>> getAssets(@PathVariable String customerId) {

        return new ResponseEntity<>(brokageService.getAssetsByCustomer(customerId), HttpStatus.OK);

    }

}
