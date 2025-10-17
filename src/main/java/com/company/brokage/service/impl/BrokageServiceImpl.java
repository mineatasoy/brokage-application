package com.company.brokage.service.impl;

import com.company.brokage.model.Asset;
import com.company.brokage.model.Order;
import com.company.brokage.model.response.AssetResponse;
import com.company.brokage.model.response.OrderResponse;
import com.company.brokage.repository.AssetRepository;
import com.company.brokage.repository.OrderRepository;
import com.company.brokage.service.BrokageService;
import com.company.brokage.util.Constants;
import com.company.brokage.util.SideEnum;
import com.company.brokage.util.StatusEnum;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BrokageServiceImpl implements BrokageService {
    private final OrderRepository orderRepository;

    private final AssetRepository assetRepository;

    public BrokageServiceImpl(OrderRepository orderRepository, AssetRepository assetRepository) {
        this.orderRepository = orderRepository;
        this.assetRepository = assetRepository;
    }

    @Override
    public OrderResponse createOrder(Order order) {
        OrderResponse response = new OrderResponse();
        //checkAssetUsableSize(order);
        if(order.getOrderSide().equals(SideEnum.SELL.name())){
            orderRepository.save(order);
            response.setOrder(order);
            response.setMessage(Constants.ORDER_CREATED_SUCCESSFULLY);
        }else{ //SIDE: BUY
            boolean customerUsableSizeAvailable = checkCustomerSize(order);
            if(customerUsableSizeAvailable){
                orderRepository.save(order);
                response.setOrder(order);
                response.setMessage(Constants.ORDER_CREATED_SUCCESSFULLY);
            }else{
                response.setOrder(order);
                response.setMessage(Constants.ORDER_CREATE_USABLESIZE_NOT_AVAILABLE);
            }
        }
        return response;
    }

    @Override
    public List<Order> getOrdersByCustomerWithDate(String customerId, LocalDate dateStart, LocalDate dateEnd) {
        return orderRepository.findByCustomerIdAndCreateDateBetween(customerId, dateStart, dateEnd);
    }

    @Override
    public OrderResponse cancelOrder(String orderId) {
        OrderResponse response = new OrderResponse();
        Optional<Order> order = orderRepository.findById(orderId);

        if (order == null || order.isEmpty()) {
            response.setMessage(Constants.ORDER_NOT_FOUND);
            return response;
        }

        if (order.get().getStatus().equals(StatusEnum.PENDING.name())) {
            response.setOrder(order.get());
            try {
                order.get().setStatus(StatusEnum.CANCELED.name());
                orderRepository.save(order.get());
                response.setMessage(Constants.ORDER_DELETED_SUCCESSFULLY);
            } catch (Exception e) {
                response.setMessage(Constants.ORDER_DELETE_ERROR);
            }
        } else {
            response.setOrder(order.get());
            response.setMessage(Constants.ORDER_DELETE_STATUS_UNAVAILABLE);
        }

        return response;

    }

    @Override
    public ArrayList<Asset> getAssetsByCustomer(String customerId) {

        return (ArrayList<Asset>) assetRepository.findByCustomerId(customerId);

    }

    private boolean checkCustomerSize(Order order) {
        boolean customerSizeAvailable = false;
        double customerTryUsableSize = 0;

        Asset asset = assetRepository.findByCustomerIdAndAssetName
                (order.getCustomerId(), Constants.ASSET_TRY);

        if(asset != null){
            customerTryUsableSize = asset.getUsableSize();
        }

        if (customerTryUsableSize >= (order.getSize() * order.getPrice())) {
            customerSizeAvailable = true;
        }

        return customerSizeAvailable;

    }


}
