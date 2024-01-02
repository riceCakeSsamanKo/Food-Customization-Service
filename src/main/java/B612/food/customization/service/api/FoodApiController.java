package B612.food.customization.service.api;

import B612.food.customization.service.domain.Food;
import B612.food.customization.service.domain.Nutrition;
import B612.food.customization.service.dto.FoodItem;
import B612.food.customization.service.dto.FoodItems;
import B612.food.customization.service.exception.NoDataException;
import B612.food.customization.service.service.FoodApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.Module;
import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import B612.food.customization.service.service.FoodService;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@Slf4j
public class FoodApiController {
    private final FoodService foodService;
    private final FoodApiService foodApiService;

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
        private float servingWeight;
        private float calories;
        private float carbohydrate;
        private float protein;
        private float fat;
        private float sugar;
        private float natrium;
        private float cholesterol;
        private float saturatedFattyAcid;
        private float transFattyAcid;

        public FoodDto(Food food) {
            Nutrition nutrition = food.getNutrition();

            name = food.getName();
            servingWeight = nutrition.getServingWeight();
            calories = nutrition.getCalories();
            carbohydrate = nutrition.getCarbonHydrate();
            protein = nutrition.getProtein();
            fat = nutrition.getFat();
            sugar = nutrition.getSugar();
            natrium = nutrition.getNatrium();
            cholesterol = nutrition.getCholesterol();
            saturatedFattyAcid = nutrition.getSaturatedFattyAcid();
            transFattyAcid = nutrition.getTransFattyAcid();
        }
    }

    @Value("${openApi.baseUrl}")
    private String baseUrl;

    @Value("${openApi.ServiceKey}")
    private String serviceKey;

    @Value("${openApi.type}")
    private String type;

    @GetMapping("/open-api/food")
    public ResponseEntity<String> callFoodApi(
            @RequestParam(value = "desc_kor") String name,
            @RequestParam(value = "bgn_year") String bgnYear,
            @RequestParam(value = "animal_plant") String animalPlant,
            @RequestParam(value = "pageNo") String pageNo,
            @RequestParam(value = "numOfRows") String numOfRows
    ) {

        HttpURLConnection urlConnection = null;
        InputStream stream = null;
        String result = null;


        // 한글을 utf-8로 인코딩
        String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
        String encodedBgnYear = URLEncoder.encode(bgnYear, StandardCharsets.UTF_8);
        String encodedAnimalPlant = URLEncoder.encode(animalPlant, StandardCharsets.UTF_8);
        String encodedPageNo = URLEncoder.encode(pageNo, StandardCharsets.UTF_8);
        String encodedNumOfRows = URLEncoder.encode(numOfRows, StandardCharsets.UTF_8);

        String urlStr = baseUrl +
                "serviceKey=" + serviceKey +
                "&type=" + type;

        if (name != "") {
            urlStr += "&desc_kor=" + encodedName;
        }
        if (bgnYear != "") {
            urlStr += "&bgn_year=" + encodedBgnYear;
        }
        if (animalPlant != "") {
            urlStr += "&animal_plant=" + encodedAnimalPlant;
        }
        if (pageNo != "") {
            urlStr += "&pageNo=" + encodedPageNo;
        }
        if (numOfRows != "") {
            urlStr += "&numOfRows=" + encodedNumOfRows;
        }
        System.out.println("urlStr = " + urlStr);

        try {
            URL url = new URL(urlStr);

            urlConnection = (HttpURLConnection) url.openConnection();
            stream = getNetworkConnection(urlConnection);
            result = readStreamToString(stream);

            if (stream != null) stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /* URLConnection 을 전달받아 연결정보 설정 후 연결, 연결 후 수신한 InputStream 반환 */
    private InputStream getNetworkConnection(HttpURLConnection urlConnection) throws IOException {
        urlConnection.setConnectTimeout(3000);
        urlConnection.setReadTimeout(3000);
        urlConnection.setRequestMethod("GET");
        urlConnection.setDoInput(true);

        if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code : " + urlConnection.getResponseCode());
        }

        return urlConnection.getInputStream();
    }

    /* InputStream을 전달받아 문자열로 변환 후 반환 */
    private String readStreamToString(InputStream stream) throws IOException {
        StringBuilder result = new StringBuilder();

        BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

        String readLine;
        while ((readLine = br.readLine()) != null) {
            result.append(readLine + "\n\r");
        }

        br.close();

        return result.toString();
    }
}