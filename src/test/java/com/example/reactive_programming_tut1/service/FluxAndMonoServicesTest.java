package com.example.reactive_programming_tut1.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class FluxAndMonoServicesTest {

    FluxAndMonoServices fluxAndMonoServices = new FluxAndMonoServices();
    @BeforeEach
    void setUp() {
    }

    @Test
    void fruitsFlux() {
        var fruitsFlux = fluxAndMonoServices.fruitsFlux();
        StepVerifier.create(fruitsFlux)
                .expectNext("Mangoes","Oranges","Banana","Apples")
                .verifyComplete();
    }

    @Test
    void fruitsMono() {
        var fruitsMono = fluxAndMonoServices.fruitsMono();
        StepVerifier.create(fruitsMono)
                .expectNext("Mangoes")
                .verifyComplete();
    }

    @Test
    void fruitsFluxFilter() {
        var fruitsFilter = fluxAndMonoServices.fruitsFluxFilter(4);
        StepVerifier.create(fruitsFilter)
                .expectNext("Mangoes","Oranges","Banana","Apple")
                .verifyComplete();

        //count the no of returned elements
        StepVerifier.create(fruitsFilter)
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    void fruitsFluxTransform() {
        var fruitsFilter = fluxAndMonoServices.fruitsFluxTransform(4);
        StepVerifier.create(fruitsFilter)
                .expectNext("Mangoes","Oranges","Banana","Apple")
                .verifyComplete();
    }

    @Test
    void fruitsFluxTransformDefaultIfEmpty() {
        var fruitsFilter = fluxAndMonoServices.fruitsFluxTransformDefaultIfEmpty(400000);
        StepVerifier.create(fruitsFilter)
                .expectNext("Default")
                .verifyComplete();
    }

    @Test
    void fruitsFluxConcat() {
        var fruitsFlux = fluxAndMonoServices.fruitsFluxConcat();

        StepVerifier.create(fruitsFlux)
                .expectNext("Mangoes","Oranges","Tomatoes","Cabbages")
                .verifyComplete();
    }

    @Test
    void fruitsFluxMerge() {
        var fruitsFlux = fluxAndMonoServices.fruitsFluxMerge().log();

        StepVerifier.create(fruitsFlux)
                .expectNext("Mangoes","Tomatoes","Oranges","Cabbages")
                .verifyComplete();
    }

    @Test
    void fruitsFluxZip() {
        var fruitsFlux = fluxAndMonoServices.fruitsFluxZip().log();

        StepVerifier.create(fruitsFlux)
                .expectNext("MangoesTomatoes","OrangesCabbages")
                .verifyComplete();
    }

    @Test
    void fruitsFluxFilterDoOn() {
        var fruitsFilterDoOn = fluxAndMonoServices.fruitsFluxFilterDoOn(4);
        StepVerifier.create(fruitsFilterDoOn)
                .expectNext("Mangoes","Oranges","Banana","Apple")
                .verifyComplete();
    }

    @Test
    void fruitsFluxOnErrorReturn() {
        var fruitsFilterDoOn = fluxAndMonoServices.fruitsFluxOnErrorReturn();
        StepVerifier.create(fruitsFilterDoOn)
                .expectNext("Mangoes","Oranges","Banana","Apple","pech","Return default when an error occurrs")
                .verifyComplete();
    }

    @Test
    void fruitsFluxOnErrorContinue() {
        var fruitsFilterDoOn = fluxAndMonoServices.fruitsFluxOnErrorContinue();
        StepVerifier.create(fruitsFilterDoOn)
                .expectNext("MANGOES","BANANA")
                .verifyComplete();
    }

    @Test
    void fruitsFluxOnErrorMap() {
        var fruitsFilterDoOn = fluxAndMonoServices.fruitsFluxOnErrorMap();
        StepVerifier.create(fruitsFilterDoOn)
                .expectNext("MANGOES")
                .expectError(IllegalStateException.class)
                .verify();
    }
}