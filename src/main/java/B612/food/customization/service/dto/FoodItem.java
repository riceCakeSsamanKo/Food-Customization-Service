package B612.food.customization.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
//객체에는 없는 알 수 없는 property가 JSON 데이터에 있어도 에러를 내뱉지 않고 무시
public class FoodItem {
    // 각 식품의 영양정보는 FoodItem에 저장됨

    //식품이름
    @JsonProperty("DESC_KOR")
    private String DESC_KOR;

    //1회제공량 (g)
    @JsonProperty("SERVING_WT")
    private String SERVING_WT;

    //열량 (kcal)
    @JsonProperty("NUTR_CONT1")
    private String NUTR_CONT1;

    //탄수화물 (g)
    @JsonProperty("NUTR_CONT2")
    private String NUTR_CONT2;

    //단백질 (g)
    @JsonProperty("NUTR_CONT3")
    private String NUTR_CONT3;

    //지방 (g)
    @JsonProperty("NUTR_CONT4")
    private String NUTR_CONT4;

    //당류 (g)
    @JsonProperty("NUTR_CONT5")
    private String NUTR_CONT5;

    //나트륨 (mg)
    @JsonProperty("NUTR_CONT6")
    private String NUTR_CONT6;

    //콜레스테롤 (mg)
    @JsonProperty("NUTR_CONT7")
    private String NUTR_CONT7;

    //포화지방산 (g)
    @JsonProperty("NUTR_CONT8")
    private String NUTR_CONT8;

    //트랜스지방산 (g)
    @JsonProperty("NUTR_CONT9")
    private String NUTR_CONT9;

    //구축년도
    @JsonProperty("BGN_YEAR")
    private String BGN_YEAR;

    //가공업체
    @JsonProperty("ANIMAL_PLANT")
    private String ANIMAL_PLANT;
}
