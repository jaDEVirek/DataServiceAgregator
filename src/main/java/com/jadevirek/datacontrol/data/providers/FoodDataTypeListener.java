package com.jadevirek.datacontrol.data.providers;


import com.jadevirek.datacontrol.data.FoodType;
import com.jadevirek.datacontrol.repository.FoodTypeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.time.Duration;

@Log4j2
@Component
public class FoodDataTypeListener {

    private final FoodTypeRepository foodTypeRepository;

    @Autowired
    public FoodDataTypeListener(FoodTypeRepository foodTypeRepository) {
        this.foodTypeRepository = foodTypeRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void listen() {

        Flux<FoodType> saveFoods = getFoods("8090")
                .onErrorResume(throwable -> getFoods("8080"))
                .retryWhen(Retry.fixedDelay(1000, Duration.ofSeconds(4)))
                .map(name -> new FoodType(name, "none", 1, null))
                .flatMap(foodTypeRepository::save);

        foodTypeRepository.deleteAll()  // this runs asynchronously, thats why we need one pipeline
                .thenMany(saveFoods)
                .subscribe(log::info);
    }

    private Flux<String> getFoods(String port) {
        return WebClient.create()
                .get()
                .uri("http://localhost:+" + port + "/listener/foods")
                .retrieve()
                .bodyToFlux(String.class)
                .doOnSubscribe(subscription -> log.info("Connection .... using port: " +port))
                .doOnError(throwable -> log.info("Connection Failed on port: " + port));
    }
}
