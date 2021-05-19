package com.mslarning.travelms.service;

import com.mslarning.travelms.domain.Order;
import com.mslarning.travelms.domain.OrderEvent;
import com.mslarning.travelms.domain.OrderState;
import com.mslarning.travelms.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;


import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AirlineCompensateAction implements Action<OrderState, OrderEvent>{

    private final JmsTemplate jmsTemplate;
    private final OrderRepository orderRepository;


    @Override
    public void execute(StateContext<OrderState, OrderEvent> context) {
        System.out.println("Airline Compensate Action");
        String orderId = (String) context.getMessage().getHeaders().get("ORDER_ID_HEADER");
//        // find from DB orderId
        Optional<Order> orderOptional = orderRepository.findById(Long.parseLong(orderId));
        jmsTemplate.convertAndSend("airline-queue", orderOptional.get());

    }
}
