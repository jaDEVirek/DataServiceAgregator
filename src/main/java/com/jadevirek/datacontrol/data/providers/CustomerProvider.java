package com.jadevirek.datacontrol.data.providers;


import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@Component
public class CustomerProvider {

    public Flux<String> generate() {
        return Flux.fromStream(Stream.generate(() -> "Customer " + System.currentTimeMillis()))
                .delayElements(Duration.ofSeconds(3));
    }
}


