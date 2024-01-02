package B612.food.customization.service.dto;

import B612.food.customization.service.service.FoodItemDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(using = FoodItemDeserializer.class)  //foodItems에 FoodItemDeserializer를 사용해서 Deserialize 함을 명시
public class FoodItems {
    @JsonProperty("items")
    private List<FoodItem> foodItems;
}