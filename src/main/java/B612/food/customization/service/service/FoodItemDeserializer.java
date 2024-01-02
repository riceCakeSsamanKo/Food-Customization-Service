package B612.food.customization.service.service;


import B612.food.customization.service.dto.FoodItem;
import B612.food.customization.service.dto.FoodItems;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

// json 데이터 바인딩
public class FoodItemDeserializer extends JsonDeserializer<FoodItems> {

    private final ObjectMapper objectMapper;

    public FoodItemDeserializer() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public FoodItems deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        JsonNode itemsNode = node.findValue("items"); // json 데이터에서 items라는 계층의 데이터 가져옴

        // ObjectMapper의 treeToValue() 메서드를 활용해서 반환된  stream 값을 toList()를 사용해 List로 변환
        List<FoodItem> items = Arrays.stream(objectMapper.treeToValue(itemsNode, FoodItem[].class)).toList();

        return new FoodItems(items);
    }


    /**
     * JsonNode 클래스 메서드(mapping 접근 메서드)
     *
     * get() : 노드의 필드를 찾고 없으면 null을 반환한다.
     *     e.g. node.get("body").get("totalCount").asInt();
     *
     * path() : 노드의 필드를 찾고 없으면 MissingNode를 반환한다.
     * findValue() : 노드와 자식 노드들에서 필드를 찾고 없으면 null을 반환한다.
     */

      /*
      순차적인 접근은 get(), path()
      노드 하위 전체에서 필드를 찾고 싶으면 findValue()
      이때 동일 필드명이 있는 경우 잘못된 필드를 찾을 수도 있다.
      item은 동일 필드명이 없기 때문에 findValue() 방식으로 접근했다.
      */
}