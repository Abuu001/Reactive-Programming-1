package com.example.reactive_programming_tut1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.tools.agent.ReactorDebugAgent;

@SpringBootApplication
public class ReactiveProgrammingTut1Application {

    public static void main(String[] args) {
        ReactorDebugAgent.init();  // this is for initializing debugging tools for project reactor
        SpringApplication.run(ReactiveProgrammingTut1Application.class, args);
    }

}
