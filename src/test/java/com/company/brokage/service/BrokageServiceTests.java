package com.company.brokage.service;

import com.company.brokage.model.Asset;
import com.company.brokage.model.Order;
import com.company.brokage.model.response.OrderResponse;
import com.company.brokage.repository.AssetRepository;
import com.company.brokage.repository.OrderRepository;
import com.company.brokage.service.impl.BrokageServiceImpl;
import com.company.brokage.util.Constants;
import com.company.brokage.util.SideEnum;
import com.company.brokage.util.StatusEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BrokageServiceTests {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private AssetRepository assetRepository;

    @InjectMocks
    private BrokageServiceImpl brokerageService;

    @Test
    void createOrderSELLTest() {

        Order order = Order.builder().orderId(Long.valueOf("111111")).assetName("ASSET1").orderSide(SideEnum.SELL.name()).size(1).createDate(LocalDate.now()).price(100).customerId("34232322").build();
        OrderResponse response = OrderResponse.builder().order(order).message(Constants.ORDER_CREATED_SUCCESSFULLY).build();
        OrderResponse result = brokerageService.createOrder(order);

        assertEquals(response, result);
    }

    @Test
    void createOrderBUYSuccessTest() {
        // given
        String customerId = "12345";
        Asset mockAsset = new Asset();
        mockAsset = Asset.builder().customerId(customerId).assetName(Constants.ASSET_TRY).size(500.0).usableSize(500.0).build();

        // when
        when(assetRepository.findByCustomerIdAndAssetName(customerId, Constants.ASSET_TRY)).thenReturn(mockAsset);

        Order order = Order.builder().orderId(Long.valueOf("111111")).assetName("ASSET1").orderSide(SideEnum.BUY.name()).size(1).createDate(LocalDate.now()).price(20).customerId("12345").build();
        OrderResponse response = OrderResponse.builder().order(order).message(Constants.ORDER_CREATED_SUCCESSFULLY).build();
        OrderResponse result = brokerageService.createOrder(order);

        // then
        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void createOrderBUYFailTest() {
        //given
        String customerId = "12345";
        Asset mockAsset = new Asset();
        mockAsset = Asset.builder().customerId(customerId).assetName(Constants.ASSET_TRY).size(500.0).usableSize(0.0).build();

        //when
        when(assetRepository.findByCustomerIdAndAssetName(customerId, Constants.ASSET_TRY)).thenReturn(mockAsset);

        Order order = Order.builder().orderId(Long.valueOf("111111")).assetName("ASSET1").orderSide(SideEnum.BUY.name()).size(1).createDate(LocalDate.now()).price(20).customerId("12345").build();
        OrderResponse response = OrderResponse.builder().order(order).message(Constants.ORDER_CREATE_USABLESIZE_NOT_AVAILABLE).build();
        OrderResponse result = brokerageService.createOrder(order);

        //then
        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void cancelOrderSuccessTest() {
        // given
        Order order = Order.builder().orderId(Long.valueOf("111111")).assetName("ASSET1").orderSide(SideEnum.SELL.name()).size(1).createDate(LocalDate.now()).price(100).customerId("34232322").status(StatusEnum.PENDING.name()).build();
        OrderResponse response = OrderResponse.builder().order(order).message(Constants.ORDER_DELETED_SUCCESSFULLY).build();
        OrderResponse resultCreate = brokerageService.createOrder(order);

        // when
        when(orderRepository.findById("111111")).thenReturn(Optional.of(order));

        OrderResponse result = brokerageService.cancelOrder(order.getOrderId().toString());

        // then
        assertEquals(response, result);

    }

    @Test
    void cancelOrderStatusNotAvailableTest() {
        // given
        Order order = Order.builder().orderId(Long.valueOf("111111")).assetName("ASSET1").orderSide(SideEnum.SELL.name()).size(1).createDate(LocalDate.now()).price(100).customerId("34232322").status(StatusEnum.MATCHED.name()).build();
        OrderResponse response = OrderResponse.builder().order(order).message(Constants.ORDER_DELETE_STATUS_UNAVAILABLE).build();
        OrderResponse resultCreate = brokerageService.createOrder(order);

        // when
        when(orderRepository.findById("111111")).thenReturn(Optional.of(order));

        OrderResponse result = brokerageService.cancelOrder(order.getOrderId().toString());

        // then
        assertNotNull(result);
        assertEquals(response, result);

    }



    @Test
    void getAssetsByCustomer() {
        // given
        String customerId = "12345";
        ArrayList<Asset> mockAssets = new ArrayList<>();
        mockAssets.add(Asset.builder().customerId(customerId).assetName(Constants.ASSET_TRY).size(500.0).usableSize(500.0).build());
        mockAssets.add(Asset.builder().customerId(customerId).assetName(Constants.ASSET_TRY).size(200.0).usableSize(200.0).build());

        // when
        when(assetRepository.findByCustomerId(customerId)).thenReturn(mockAssets);

        ArrayList<Asset> result = brokerageService.getAssetsByCustomer(customerId);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("TRY", result.get(0).getAssetName());
        assertEquals("TRY", result.get(1).getAssetName());
        verify(assetRepository, times(1)).findByCustomerId(customerId);

    }

}

