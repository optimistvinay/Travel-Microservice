package com.mslarning.travelms.config;

import com.mslarning.travelms.domain.OrderEvent;
import com.mslarning.travelms.domain.OrderState;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.action.Action;

import java.util.EnumSet;

@RequiredArgsConstructor
@Configuration
@EnableStateMachineFactory // generates state machine
public class OrderStateMachineConfig extends StateMachineConfigurerAdapter<OrderState, OrderEvent> {

    private final Action<OrderState, OrderEvent> airlineAction;
    private final Action<OrderState, OrderEvent> hotelAction;
    private final Action<OrderState, OrderEvent> airlineCompensateAction;

    @Override
    public void configure(StateMachineStateConfigurer<OrderState, OrderEvent> states) throws Exception {
        states.withStates()
                .initial(OrderState.NEW)
                .states(EnumSet.allOf(OrderState.class))//all states
//                .end(OrderState.HOTEL)
                .end(OrderState.COMPLETED)
                .end(OrderState.CANCELLED)
        ;
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderState, OrderEvent> transitions) throws Exception {
        transitions.withExternal()
                .source(OrderState.NEW).target(OrderState.AIRLINE).event(OrderEvent.BOOK_AIRLINE)
                .action(airlineAction)
                .and().withExternal()
                .source(OrderState.AIRLINE).target(OrderState.HOTEL).event(OrderEvent.BOOK_AIRLINE_COMPLETED)
                .action(hotelAction)
                .and().withExternal()
                .source(OrderState.HOTEL).target(OrderState.COMPLETED).event(OrderEvent.BOOK_HOTEL_COMPLETED)
//                .action(e3Action)
                .source(OrderState.HOTEL).target(OrderState.CANCELLED).event(OrderEvent.BOOK_HOTEL_FAILED)
                .action(airlineCompensateAction)
        ;
    }
}
