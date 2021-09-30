package com.jadevirek.datacontrol.controler;


import com.jadevirek.datacontrol.data.FoodType;
import com.jadevirek.datacontrol.repository.FoodTypeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


/**
 * Second way to declare Controller.
 */
@Configuration
public class FoodTypeRouter {

    @Bean
    public RouterFunction<ServerResponse> routs(FoodTypeRepository foodTypeRepository) {
        return RouterFunctions.route()
                .GET("/route/foods", sRequest -> ServerResponse.ok()
                        .body(foodTypeRepository.findAll(), FoodType.class))
                .build();
    }
}
