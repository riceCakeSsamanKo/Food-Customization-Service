package api;

import domain.Food;
import domain.Nutrition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import service.FoodService;

@RestController
@RequiredArgsConstructor
public class FoodApiController {
    private final FoodService foodService;

    @PostMapping("https://apis.data.go.kr/1471000/FoodNtrIrdntInfoService1/getFoodNtrItdntList1?serviceKey=RRAZ7WfqxV7iXm8KE6cMuImCqAiHG%2Fn1fVI6E%2FbnzlmWxRNU1l%2FEsHQ8794sz47WFNlM1HMaQCv8%2FtWAH2gBqQ%3D%3D&pageNo=10&numOfRows=20&type=json")
    public void saveFoodData(@RequestBody @Validated CreateFoodRequest... requests) {
        for (CreateFoodRequest request : requests) {
            String name = request.getDESC_KOR();
            int servingWt = request.getSERVING_WT();
            int calories = request.getNUTR_CONT1();
            int carbohydrate = request.getNUTR_CONT2();
            int protein = request.getNUTR_CONT3();
            int fat = request.getNUTR_CONT4();
            int sugar = request.getNUTR_CONT5();
            int natrium = request.getNUTR_CONT6();
            int cholesterol = request.getNUTR_CONT7();
            int saturatedFattyAcid = request.getNUTR_CONT8();
            int transFattyAcid = request.getNUTR_CONT9();

            Food food = new Food(name, new Nutrition(servingWt, calories, carbohydrate, protein, fat, sugar, natrium, cholesterol, saturatedFattyAcid, transFattyAcid));
            foodService.save(food);
        }
    }

    @Data
    @AllArgsConstructor
    static class CreateFoodRequest {
        private String DESC_KOR; //식품이름
        private int SERVING_WT; //1회제공량 (g)
        private int NUTR_CONT1; //열량 (kcal)
        private int NUTR_CONT2; //탄수화물 (g)
        private int NUTR_CONT3; //단백질 (g)
        private int NUTR_CONT4; //지방 (g)
        private int NUTR_CONT5; //당류 (g)
        private int NUTR_CONT6; //나트륨 (mg)
        private int NUTR_CONT7; //콜레스테롤 (mg)
        private int NUTR_CONT8; //포화지방산 (g)
        private int NUTR_CONT9; //트랜스지방산 (g)
    }
}
