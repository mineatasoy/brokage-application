package com.company.brokage.service;

import com.company.brokage.model.Asset;
import com.company.brokage.model.Order;
import com.company.brokage.model.response.AssetResponse;
import com.company.brokage.model.response.OrderResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface BrokageService {

    public OrderResponse createOrder(Order order);

    public OrderResponse cancelOrder(String orderId);

    public ArrayList<Asset> getAssetsByCustomer(String customerId);

    public List<Order> getOrdersByCustomerWithDate(String customerId, LocalDate dateStart, LocalDate dateEnd);

}
