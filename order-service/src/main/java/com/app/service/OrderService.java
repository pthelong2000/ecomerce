package com.app.service;

import com.app.model.OrderRequest;
import com.app.model.OrderResponse;

public interface OrderService {

    long placeOrder(OrderRequest request);

    OrderResponse getOrderById(long orderId);
}
