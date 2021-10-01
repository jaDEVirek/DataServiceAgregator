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

        Flux<FoodType> saveFoods = WebClient.create()
                .get()
                .uri("http://localhost:8080/listener/foods")
                .retrieve()
                .bodyToFlux(String.class)
                .map(name -> new FoodType(name, "none", 1, null))
                .flatMap(foodTypeRepository::save);

        foodTypeRepository.deleteAll()  // this runs asynchronously, thats why we need one pipeline
                .thenMany(saveFoods)
                .subscribe(log::info);
    }
}
