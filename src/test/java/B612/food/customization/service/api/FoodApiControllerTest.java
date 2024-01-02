package B612.food.customization.service.api;

import B612.food.customization.service.domain.Food;
import B612.food.customization.service.domain.Nutrition;
import B612.food.customization.service.dto.FoodItem;
import B612.food.customization.service.dto.FoodItems;
import B612.food.customization.service.service.FoodApiService;
import B612.food.customization.service.service.FoodService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // ��ü ���ø����̼� ���ؽ�Ʈ �ε�
@AutoConfigureMockMvc
@Transactional
class FoodApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired // FoodService�� �� ��ü�� ����
    private FoodService foodService;

    @Autowired
    private FoodApiService foodApiService;

    @Test
    @DisplayName("Open Api ��� �׽�Ʈ")
    public void callOpenApi() throws Exception {
        //given

        //when
        LinkedMultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("desc_kor", "�ٳ���Ĩ");
        param.add("bgn_year", "2017");
        param.add("animal_plant", "(��)���ڸ���");
        param.add("pageNo", "1");
        param.add("numOfRows", "3");

        //then
        this.mvc.perform(get("/open-api/food").params(param))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Open Api json DTO ���ε� �׽�Ʈ")
    public void testJsonParsingToDto() throws Exception {
        //when
        LinkedMultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("desc_kor", "�ٳ���Ĩ");
        param.add("bgn_year", "2017");
        param.add("animal_plant", "(��)���ڸ���");
        param.add("pageNo", "1");
        param.add("numOfRows", "3");

        // JSON�� DTO�� �Ľ�
        String contentAsString = this.mvc.perform(get("/open-api/food").params(param))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        FoodItems foodItems = foodApiService.parsingJsonObject(contentAsString);


        for (FoodItem foodItem : foodItems.getFoodItems()) {
            System.out.println("Name = " + foodItem.getName());
            System.out.println("company = " + foodItem.getCompany());
        }
    }

    @Test
    @DisplayName("Open API json �����͸� DB�� �����ϱ�")
    public void saveJsonToDB() throws Exception {
        //given
        LinkedMultiValueMap<String, String> param =
                foodApiService.makeParam("�ٳ���Ĩ", "", "", "", "3");

        //when
        String contentAsString = this.mvc.perform(get("/open-api/food").params(param))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        System.out.println("contentAsString = " + contentAsString);
        FoodItems foodItems = foodApiService.parsingJsonObject(contentAsString);

        foodItems.getFoodItems().forEach(item -> {
            String name = item.getName();
            String servingWt = item.getServingWt();
            String fat = item.getFat();
            String company = item.getCompany();
            String calories = item.getCalories();
            String cholesterol = item.getCholesterol();
            String carbonHydrate = item.getCarbonHydrate();
            String constructionYear = item.getConstructionYear();
            String natrium = item.getNatrium();
            String protein = item.getProtein();
            String saturatedFattyAcid = item.getSaturatedFattyAcid();
            String sugar = item.getSugar();
            String transFattyAcid = item.getTransFattyAcid();
            Nutrition nutrition = new Nutrition(servingWt, calories, carbonHydrate, protein, fat, sugar, natrium, cholesterol, saturatedFattyAcid, transFattyAcid);
            Food food = new Food(name, nutrition);
            foodService.save(food);
        });

        List<Food> findFood = foodService.findFoodsByName("�ٳ���Ĩ");
        for (Food food : findFood) {
            assertEquals(food.getName(), "�ٳ���Ĩ");
            System.out.println("food= " + food);
        }
    }

}