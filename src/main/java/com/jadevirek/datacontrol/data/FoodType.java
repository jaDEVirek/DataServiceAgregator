package com.jadevirek.datacontrol.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class FoodType {
    String name;
    String type;
    String quantity;
    Nutrition nutrition;


}
