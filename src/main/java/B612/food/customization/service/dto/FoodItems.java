package B612.food.customization.service.dto;

import B612.food.customization.service.service.FoodItemDeserializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonDeserialize(using = FoodItemDeserializer.class)  //foodItems에 FoodItemDeserializer를 사용해서 Deserialize 함을 명시
public class FoodItems {
    @JsonProperty("items")
    private List<FoodItem> foodItems;

    @JsonCreator
    public FoodItems(@JsonProperty("body") JsonNode node) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode itemNode = node.findValue("items");
        this.foodItems = Arrays.stream(objectMapper.treeToValue(itemNode, FoodItem[].class)).toList();
    }
}