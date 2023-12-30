package B612.food.customization.service.api;

import B612.food.customization.service.domain.Food;
import B612.food.customization.service.domain.Nutrition;
import B612.food.customization.service.exception.NoDataException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import B612.food.customization.service.service.FoodService;

@RestController
@RequiredArgsConstructor
public class FoodApiController {
    private final FoodService foodService;

    @Data
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

    @Data
    static class CreateFoodResponse {
        private Long id;
        private String name;

        public CreateFoodResponse(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    /**
     * 식품 조회 api
     * @param name
     * @return 식품 정보
     * @throws NoDataException
     */
    @GetMapping("/api/food/{name}")
    public Result getFoodData(@PathVariable String name) throws NoDataException {
        Food food = foodService.findFood(name);
        FoodDto foodDto = new FoodDto(food);

        return new Result(foodDto);  // wraping
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class FoodDto {
        private String name;
        private int servingWeight;
        private int calories;
        private int carbohydrate;
        private int protein;
        private int fat;
        private int sugar;
        private int natrium;
        private int cholesterol;
        private int saturatedFattyAcid;
        private int transFattyAcid;

        public FoodDto(Food food) {
            Nutrition nutrition = food.getNutrition();

            name = food.getName();
            servingWeight = nutrition.getServingWeight();
            calories = nutrition.getCalories();
            carbohydrate = nutrition.getCarbohydrate();
            protein = nutrition.getProtein();
            fat = nutrition.getFat();
            sugar = nutrition.getSugar();
            natrium = nutrition.getNatrium();
            cholesterol = nutrition.getCholesterol();
            saturatedFattyAcid = nutrition.getSaturatedFattyAcid();
            transFattyAcid = nutrition.getTransFattyAcid();
        }
    }


    /*
    @PostMapping("/api/food/save")
    public CreateFoodResponse saveFoodData(@RequestBody @Validated CreateFoodRequest request) {
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
        Long id = foodService.save(food);

        return new CreateFoodResponse(id, name);
    }*/



}
