package com.app.service.Impl;

import com.app.entity.Order;
import com.app.exception.OrderServiceException;
import com.app.external.client.PaymentService;
import com.app.external.client.ProductService;
import com.app.model.OrderRequest;
import com.app.model.OrderResponse;
import com.app.model.PaymentRequest;
import com.app.model.PaymentResponse;
import com.app.repository.OrderRepository;
import com.app.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ProductService productService;

    private final PaymentService paymentService;

    private final RestTemplate restTemplate;

    @Override
    public long placeOrder(OrderRequest request) {
        productService.reduceQuantity(request.getProductId(), request.getQuantity());

        Order order = Order.builder()
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .amount(request.getTotalAmount())
                .orderStatus("CREATED")
                .orderDate(Instant.now())
                .build();

        order = orderRepository.save(order);
        String orderStatus = null;
        try {
            PaymentRequest paymentRequest = PaymentRequest.builder()
                    .orderId(order.getId())
                    .amount(order.getAmount())
                    .paymentMode(request.getPaymentMode())
                    .build();
            paymentService.doPayment(paymentRequest);
            orderStatus = "PLACED";
        } catch (Exception e) {
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        return order.getId();
    }

    @Override
    public OrderResponse getOrderById(long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderServiceException("Order not found for the order id: " + orderId));

        ResponseEntity<OrderResponse.ProductDetails> productResponse =
                restTemplate.exchange("http://PRODUCT-SERVICE/product/" + order.getProductId(),
                HttpMethod.GET,
                null, OrderResponse.ProductDetails.class);

        PaymentResponse paymentResponse =
                restTemplate.exchange("http://PAYMENT-SERVICE/payment/order/" + orderId,
                        HttpMethod.GET,
                        null,
                        PaymentResponse.class).getBody();

        OrderResponse.PaymentDetails paymentDetails = Objects.nonNull(paymentResponse)
                ? OrderResponse.PaymentDetails.builder()
                    .paymentId(paymentResponse.getPaymentId())
                    .paymentDate(paymentResponse.getPaymentDate())
                    .paymentMode(paymentResponse.getPaymentMode())
                    .status(paymentResponse.getStatus())
                    .build()
                : null;

        return OrderResponse.builder()
                        .orderId(orderId)
                        .orderDate(order.getOrderDate())
                        .amount(order.getAmount())
                        .orderStatus(order.getOrderStatus())
                        .productDetails(productResponse.getBody())
                        .paymentDetails(paymentDetails)
                        .build();
    }
}
