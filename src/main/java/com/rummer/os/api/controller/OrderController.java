package com.rummer.os.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rummer.os.api.common.TransactionRequest;
import com.rummer.os.api.common.TransactionResponse;
import com.rummer.os.api.entity.Order;
import com.rummer.os.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/bookOrder")
    public TransactionResponse bookOrder(@RequestBody TransactionRequest request) throws JsonProcessingException {
        return orderService.saveOrder(request);
    }
}
