package com.mslarning.travelms.resource;

import com.mslarning.travelms.domain.Order;
import com.mslarning.travelms.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderResource {

    @Autowired
    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity placeOrder(@RequestBody Order order) throws URISyntaxException {
        System.out.println("*********");
        System.out.println("order received: " + order);
        Order savedOrder = orderService.placeOrder(order);
        return ResponseEntity.created(new URI(savedOrder.getOrderId().toString())).build();
    }
}
