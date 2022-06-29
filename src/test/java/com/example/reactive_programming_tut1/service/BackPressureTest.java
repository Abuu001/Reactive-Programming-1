package com.example.reactive_programming_tut1.service;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

public class BackPressureTest {

    @Test
    public void testBackPressure(){
        var numbers = Flux.range(1,Integer.MAX_VALUE).log();
       // numbers.subscribe(integer -> System.out.println("Integer: = "+integer));

        numbers.subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(400); // I can only handle 400 data
            }

            @Override
            protected void hookOnNext(Integer value) {
                System.out.println("value = " + value);

                if(value == 400){  // says if I get my 400 datas, cancel I dont want any more
                    cancel();
                }
            }

            @Override
            protected void hookOnComplete() {
                System.out.println("Completed YEYYY");
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                super.hookOnError(throwable);
            }

            @Override
            protected void hookOnCancel() {
                super.hookOnCancel();
            }
        });
    }


    @Test
    public void testBackPressureDropFunctionality(){
        var numbers = Flux.range(1,Integer.MAX_VALUE).log();
        // numbers.subscribe(integer -> System.out.println("Integer: = "+integer));

        numbers
                .onBackpressureDrop(integer -> {
                    System.out.println("Dropped values = " + integer);
                })
                .subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(400); // I can only handle 400 data
            }

            @Override
            protected void hookOnNext(Integer value) {
                System.out.println("value = " + value);

                if(value == 400){  // says if I get my 400 datas, cancel I dont want any more
                  //  cancel(); or
                    hookOnCancel();
                }
            }

            @Override
            protected void hookOnComplete() {
                System.out.println("Completed YEYYY");
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                super.hookOnError(throwable);
            }

            @Override
            protected void hookOnCancel() {
                super.hookOnCancel();
            }
        });
    }

    @Test
    public void testBackPressureBuffer(){ //on buffer will store values above 400 so that we can access them upon request
        var numbers = Flux.range(1,Integer.MAX_VALUE).log();
        // numbers.subscribe(integer -> System.out.println("Integer: = "+integer));

        numbers
                .onBackpressureBuffer(200,i -> System.out.println("Buffered value = " + i))
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(400); // I can only handle 400 data
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        System.out.println("value = " + value);

                        if(value == 400){  // says if I get my 400 datas, cancel I dont want any more
                            //  cancel(); or
                            hookOnCancel();
                        }
                    }

                    @Override
                    protected void hookOnComplete() {
                        System.out.println("Completed YEYYY");
                    }

                    @Override
                    protected void hookOnError(Throwable throwable) {
                        super.hookOnError(throwable);
                    }

                    @Override
                    protected void hookOnCancel() {
                        super.hookOnCancel();
                    }
                });
    }

    @Test
    public void testBackPressureError(){
        var numbers = Flux.range(1,Integer.MAX_VALUE).log();
        // numbers.subscribe(integer -> System.out.println("Integer: = "+integer));

        numbers
                .onBackpressureError()
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(400); // I can only handle 400 data
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        System.out.println("value = " + value);

                        if(value == 400){  // says if I get my 400 datas, cancel I dont want any more
                            //  cancel(); or
                            hookOnCancel();
                        }
                    }

                    @Override
                    protected void hookOnComplete() {
                        System.out.println("Completed YEYYY");
                    }

                    @Override
                    protected void hookOnError(Throwable throwable) {
                        System.out.println("throwable = " + throwable);
                    }

                    @Override
                    protected void hookOnCancel() {
                        super.hookOnCancel();
                    }
                });
    }
}
