package com.jadevirek.datacontrol.controler;

import com.jadevirek.datacontrol.data.FoodType;
import com.jadevirek.datacontrol.repository.FoodTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class FoodTypeControler {

    private final FoodTypeRepository foodTypeRepository;

    @Autowired
    public FoodTypeControler(FoodTypeRepository foodTypeRepository) {
        this.foodTypeRepository = foodTypeRepository;
    }

    @GetMapping("/foods")
    public Flux<FoodType> getFoods(){
        return foodTypeRepository.findAll();
    }
}
