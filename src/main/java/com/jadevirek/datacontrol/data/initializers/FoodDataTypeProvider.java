package com.jadevirek.datacontrol.data.initializers;


import com.jadevirek.datacontrol.data.FoodType;
import com.jadevirek.datacontrol.repository.FoodTypeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;


@Log4j2
@Component
public class FoodDataTypeProvider {

    private final FoodTypeRepository foodTypeRepository;

    @Autowired
    public FoodDataTypeProvider(FoodTypeRepository foodTypeRepository) {
        this.foodTypeRepository = foodTypeRepository;
    }


    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        Flux<FoodType> foodTypeFlux = Flux.just("Chicken", "Rice", "Pasta", "Banana")
                .map(name -> new FoodType(name, null, 1, null))
                .flatMap(foodTypeRepository::save);

        foodTypeRepository.deleteAll()  // this runs asynchronously, thats why we need one pipeline
                .thenMany(foodTypeFlux)      // this operations runs on not the same threads
                .thenMany(foodTypeRepository.findAll())
                .subscribe(log::info);
    }
}
