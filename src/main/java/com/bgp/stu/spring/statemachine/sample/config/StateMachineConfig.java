package com.bgp.stu.spring.statemachine.sample.config;

import com.bgp.stu.spring.statemachine.sample.enums.Events;
import com.bgp.stu.spring.statemachine.sample.enums.States;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;
import java.util.Optional;

/**
 * @author Ping
 */
@Slf4j
@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    public static final String ENUM_STATE_MACHINE_SAMPLE = "enum-state-machine-sample";

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config
                .withConfiguration()
                .machineId(ENUM_STATE_MACHINE_SAMPLE)
                .listener(listener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states
                .withStates()
                .initial(States.STATE_INIT)
                .end(States.STATE_FINISH)
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
                .withExternal()
                .source(States.STATE_INIT).target(States.STATE_PROCESS).event(Events.EVENT_A)
                .and()
                .withExternal()
                .source(States.STATE_PROCESS).target(States.STATE_FINISH).event(Events.EVENT_B);
    }

    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                log.info("State[" + Optional.ofNullable(from).map(State::getId).orElse(null) + "] change to " + to.getId());
            }
        };
    }
}
