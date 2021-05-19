package com.mslarning.travelms.service;


import com.mslarning.travelms.domain.Order;
import com.mslarning.travelms.domain.OrderEvent;
import com.mslarning.travelms.domain.OrderState;
import com.mslarning.travelms.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderStateChangeInterceptor extends StateMachineInterceptorAdapter<OrderState, OrderEvent> {

    @Autowired
    private final OrderRepository orderRepository;

    /**
     * Detect state change(PRE) and persist state of an Order into DB
     * @param state
     * @param message
     * @param transition
     * @param stateMachine
     */
    @Override
    public void preStateChange(State<OrderState, OrderEvent> state, Message<OrderEvent> message, Transition<OrderState, OrderEvent> transition, StateMachine<OrderState, OrderEvent> stateMachine) {
        Optional.ofNullable(message)
                .flatMap(msg -> Optional.ofNullable((String) msg.getHeaders().getOrDefault("ORDER_ID_HEADER", "")))
                .ifPresent(orderId -> {
                    System.out.println("$$$$ Interceptor: orderId " + orderId + " $$$$");
                    log.debug("Saving state for order id: " + orderId + " Status: " + state.getId());
                    Order order = orderRepository.getOne(Long.parseLong(orderId));// todo: may not be correct UUID
                    order.setOrderState(state.getId());
                    orderRepository.saveAndFlush(order);// Hibernate is LazyLoading, so to avoid that.
                });
    }
}
