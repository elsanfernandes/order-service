package com.rummer.os.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rummer.os.api.common.TransactionRequest;
import com.rummer.os.api.common.TransactionResponse;
import com.rummer.os.api.entity.Order;
import com.rummer.os.api.common.Payment;
import com.rummer.os.api.repo.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
@RefreshScope
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    @Lazy
    private RestTemplate template;

    @Value("${microservice.payment-service.endpoints.endpoint.uri}")
    private String PAYMENT_SERVICE_URL;

    private Logger log = LoggerFactory.getLogger(OrderService.class);

    public TransactionResponse saveOrder (TransactionRequest transactionRequest) throws JsonProcessingException {
        String responseMsg;
        Order order = transactionRequest.getOrder();
        Payment payment = transactionRequest.getPayment();
        orderRepository.save(order);
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());
        log.info("OrderService Step 1 : {}", new ObjectMapper().writeValueAsString(transactionRequest));

        Payment paymentRes = template.postForObject(PAYMENT_SERVICE_URL, payment, Payment.class);
        log.info("OrderService Step2 : {}", new ObjectMapper().writeValueAsString(paymentRes));

        responseMsg = paymentRes.getPaymentStatus().equals("success") ? "Transaction is successful" : "Transaction is failed";
        return new TransactionResponse(order, paymentRes.getTransactionId(), responseMsg);
    }

}
