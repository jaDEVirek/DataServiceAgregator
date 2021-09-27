package com.jadevirek.datacontrol.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Nutrition {
    String type;
    String carbohydrates;
    String proteins;
    String fats;
    String fibre;
    String calories;
}
