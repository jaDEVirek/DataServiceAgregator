package com.jadevirek.datacontrol.repository;

import com.jadevirek.datacontrol.data.FoodType;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodTypeRepository extends ReactiveCrudRepository<FoodType,Long> {


}
