package com.example.reactive_programming_tut1.service;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxAndMonoServices {

   public Flux<String> fruitsFlux(){
       return Flux.fromIterable(List.of("Mangoes","Oranges","Banana","Apples")).log();
   }

    public Flux<String> fruitsFluxMap(){
        return Flux.fromIterable(List.of("Mangoes","Oranges","Banana","Apples"))
                .map(s -> s.toUpperCase())
                .log();
    }

    public Flux<String> fruitsFluxFilter(int number){
        return Flux.fromIterable(List.of("Mangoes","Oranges","Banana","Apple","pech"))
                .filter(s -> s.length() > number)
                .log();
    }

    public Flux<String> fruitsFluxFlatMap(){
        return Flux.fromIterable(List.of("Mangoes","Oranges","Banana","Apple","pech"))
                .flatMap(s -> Flux.just(s.split("")))
         /*       .delayElements(Duration.ofMillis(
                        new Random().nextInt(1000)
                ))*/
                .log();
    }

    /** NOTES
     * flatMApMany - will convert a mono to a flux
      * transform - used to convert from one type to another
     */

    public Mono<String> fruitsMono(){
        return Mono.just("Mangoes");
    }

    public Flux<String> fruitsFluxTransform(int number){
        Function<Flux<String>,Flux<String>> filterData = data -> data.filter(s -> s.length() > number);

        return Flux.fromIterable(List.of("Mangoes"))
                .transform(filterData)
                .log();
    }

    public Flux<String> fruitsFluxTransformDefaultIfEmpty(int number){
        Function<Flux<String>,Flux<String>> filterData = data -> data.filter(s -> s.length() > number);

        return Flux.fromIterable(List.of("Mangoes","Oranges","Banana","Apple","pech"))
                .transform(filterData)
                .defaultIfEmpty("Default")
              //  .switchIfEmpty(Flux.just("Pineapple","Avocados")) you can use different set of data if not found >number . will switch to new data set
                .log();
    }

    public Flux<String> fruitsFluxConcat(){
         var fruits = Flux.just("Mangoes","Oranges");
        var veggies = Flux.just("Tomatoes","Cabbages");

        return Flux.concat(fruits,veggies).log();
    }

    public Flux<String> fruitsFluxMerge(){
        var fruits = Flux.just("Mangoes","Oranges")
                .delayElements(Duration.ofMillis(1000));
        var veggies = Flux.just("Tomatoes","Cabbages")
                .delayElements(Duration.ofMillis(1300));
//result = ["Mangoes","Tomatoes","Oranges","Cabbages"]
        return Flux.merge(fruits,veggies).log();
    }

    public Flux<String> fruitsFluxZip(){
        var fruits = Flux.just("Mangoes","Oranges");
        var veggies = Flux.just("Tomatoes","Cabbages");

        return Flux.zip(fruits,veggies,(first,second) -> first + second).log();
    }

    public Flux<String> fruitsFluxFilterDoOn(int number){
        return Flux.fromIterable(List.of("Mangoes","Oranges","Banana","Apple","pech"))
                .filter(s -> s.length() > number)
                .doOnNext(s ->{
                    System.out.println("s = "+ s);
                })
                .doOnSubscribe(subscription -> System.out.println(subscription.toString()))
                .doOnComplete(()-> System.out.println("Hurray Completed"))
                //.doOnError()
                .log();
    }

    public Flux<String> fruitsFluxOnErrorReturn(){
        return Flux.just("Mangoes","Oranges","Banana","Apple","pech")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .onErrorReturn("Return default when an error occurrs")
                .log();
    }

    public Flux<String> fruitsFluxOnErrorContinue(){ // it will drop the value causing the error and continue processing other values
        return Flux.just("Mangoes","Oranges","Banana")
                .map(s ->{
                    if(s.equalsIgnoreCase("orAnGes")){
                        throw new RuntimeException("Exception Occurred");
                    }
                  return   s.toUpperCase();
                }).onErrorContinue((err,val)->{
                    System.out.println("error = "+ err);
                    System.out.println("val that caused error = "+ val);
                })
                .log();
    }

    public Flux<String> fruitsFluxOnErrorMap(){ // it will drop the value causing the error and continue processing other values
        return Flux.just("Mangoes","Oranges","Banana")
                .map(s ->{
                    if(s.equalsIgnoreCase("orAnGes")){
                        throw new RuntimeException("Exception Occurred");
                    }
                    return   s.toUpperCase();
                }).onErrorMap(throwable -> { // for mapping this exception to a different exception that we have
                    System.out.println("Throwable = " + throwable);
                    return  new IllegalStateException("From On Error Map");
                }).log();
    }

    public static void main(String[] args) {
        FluxAndMonoServices fluxAndMonoServices = new FluxAndMonoServices();
        fluxAndMonoServices.fruitsFlux()
                .subscribe(s ->{
                    System.out.println("Flux -> "+ s);
                });

        fluxAndMonoServices.fruitsMono()
                .subscribe(s ->{
                    System.out.println("Mono -> "+ s);
                });

        fluxAndMonoServices.fruitsFluxMap()
                .subscribe(s ->{
                            System.out.println("Flux map -> "+ s);
                        }
                );
        fluxAndMonoServices.fruitsFluxFlatMap()
            .subscribe(s ->{
                        System.out.println("Flux Flatmap -> "+ s);
                    }
            );

    }
}


