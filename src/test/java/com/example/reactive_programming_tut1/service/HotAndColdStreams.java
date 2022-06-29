package com.example.reactive_programming_tut1.service;

import java.time.Duration;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.tools.agent.ReactorDebugAgent;

public class HotAndColdStreams {

    @Test
    public void coldStreamTest(){

        Hooks.onOperatorDebug(); // this is used for  debugging
        ReactorDebugAgent.init(); //for debugging
        ReactorDebugAgent.processExistingClasses(); //for debugging

        var numbers = Flux.range(1,100);

        numbers.subscribe(integer -> System.out.println("Subscriber 1 = " + integer));
        numbers.checkpoint("Checkpoint 1"); // this is used for debugging .you can add checkpoints anywhere in ur code for debugging
        numbers.subscribe(integer -> System.out.println("Subscriber 2 = " + integer));
        numbers.checkpoint("Checkpoint 2"); // this is used for debugging .you can add checkpoints anywhere in ur code for debugging

        numbers.subscribe(integer -> System.out.println("Subscriber 3 = " + integer));
        numbers.subscribe(integer -> System.out.println("Subscriber 4 = " + integer));
    }

    @SneakyThrows
    @Test
    public void hotStreamTest(){
        var numbers = Flux.range(1,100)
                .delayElements(Duration.ofMillis(1000));

        ConnectableFlux<Integer> publisher = numbers.publish();
        publisher.connect();

        publisher.subscribe(integer -> System.out.println("Subscriber 1 = " + integer));
        Thread.sleep(4000L);
        publisher.subscribe(integer -> System.out.println("Subscriber 2 = " + integer));
        Thread.sleep(4000L);
        publisher.subscribe(integer -> System.out.println("Subscriber 3 = " + integer));
        Thread.sleep(4000L);
        publisher.subscribe(integer -> System.out.println("Subscriber 4 = " + integer));
    }
}
