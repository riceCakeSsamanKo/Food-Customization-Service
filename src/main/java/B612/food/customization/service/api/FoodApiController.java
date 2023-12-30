package B612.food.customization.service.api;

import B612.food.customization.service.domain.Food;
import B612.food.customization.service.domain.Nutrition;
import B612.food.customization.service.exception.NoDataException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.Module;
import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import B612.food.customization.service.service.FoodService;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@Slf4j
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
     * 단일 식품 조회 api
     *
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

    /**
     * 전체 식품 조회 api
     *
     * @return 식품 정보
     * @throws NoDataException
     */
    @GetMapping("/api/food")
    public Result getAllFoodData() throws NoDataException {
        List<Food> foods = foodService.findFoods();
        List<FoodDto> collect = foods.stream()
                .map(f -> new FoodDto(f))
                .collect(Collectors.toList());

        return new Result(collect);  // wraping
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

    /*@PostMapping("https://apis.data.go.kr/1471000/FoodNtrIrdntInfoService1/getFoodNtrItdntList1?serviceKey=RRAZ7WfqxV7iXm8KE6cMuImCqAiHG%2Fn1fVI6E%2FbnzlmWxRNU1l%2FEsHQ8794sz47WFNlM1HMaQCv8%2FtWAH2gBqQ%3D%3D&pageNo=10&numOfRows=20&type=json")
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

    @Value("${openApi.serviceKey}")
    private String serviceKey;

    @Value("${openApi.callBackUrl}")
    private String callBackUrl;

    @Value("${openApi.dataType}")
    private String dataType;

     @GetMapping("/forecast")
    public ResponseEntity<String> callForecastApi(
            @RequestParam(value="DESC_KOR") String name,
            @RequestParam(value="SERVING_WT") int servingWeight,
            @RequestParam(value="NUTR_CONT1") int calories,
            @RequestParam(value="NUTR_CONT2") int carbonHydrate,
            @RequestParam(value="NUTR_CONT3") int protein,
            @RequestParam(value="NUTR_CONT4") int fat,
            @RequestParam(value="NUTR_CONT5") int sugar,
            @RequestParam(value="NUTR_CONT6") int natrium,
            @RequestParam(value="NUTR_CONT7") int cholesterol,
            @RequestParam(value="NUTR_CONT8") int saturatedFattyAcid,
            @RequestParam(value="NUTR_CONT9") int transFattyAcid
    ){
        HttpURLConnection urlConnection = null;
        InputStream stream = null;
        String result = null;

        String urlStr = callBackUrl +
                "serviceKey=" + serviceKey +
                "&dataType=" + dataType +
                "&base_date=" + baseDate +
                "&base_time=" + baseTime +
                "&beach_num=" + beachNum;

        try {
            URL url = new URL(urlStr);

            urlConnection = (HttpURLConnection) url.openConnection();
            stream = getNetworkConnection(urlConnection);
            result = readStreamToString(stream);

            if (stream != null) stream.close();
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
