package com.bgp.stu.spring.statemachine.sample.controller;

import com.bgp.stu.spring.statemachine.sample.config.StateMachineConfig;
import com.bgp.stu.spring.statemachine.sample.enums.Events;
import com.bgp.stu.spring.statemachine.sample.enums.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ping
 */
@RestController
public class SpringStatemachineSampleController {

    @Autowired
    private StateMachineFactory<States, Events> stateMachineFactory;

    @RequestMapping("/")
    public String index() {
        return "SpringStatemachineSample";
    }

    @GetMapping("/state")
    public String run() {
        StateMachine<States, Events> stateMachine = stateMachineFactory.getStateMachine(StateMachineConfig.ENUM_STATE_MACHINE_SAMPLE);
        stateMachine.start();
        stateMachine.sendEvent(Events.EVENT_A);
        stateMachine.sendEvent(Events.EVENT_B);
        return "State,Event";
    }
}
