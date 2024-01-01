package B612.food.customization.service.api;

import B612.food.customization.service.dto.FoodItem;
import B612.food.customization.service.dto.FoodItems;
import B612.food.customization.service.service.FoodService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FoodApiController.class)  // 웹 계층만 테스트
@AutoConfigureMockMvc
class FoodApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean // FoodService를 목 객체로 주입
    private FoodService foodService;

    @Test
    @DisplayName("Open Api 통신 테스트")
    public void callOpenApi() throws Exception {
        //given

        //when
        LinkedMultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("desc_kor", "바나나칩");
        param.add("bgn_year", "2017");
        param.add("animal_plant", "(유)돌코리아");
        param.add("pageNo", "1");
        param.add("numOfRows", "3");

        //then
        this.mvc.perform(get("/open-api/food").params(param))
                .andExpect(status().isOk())
                .andDo(print());
    }

     @Test
    public void testJsonParsingToDto() {
        // 테스트할 JSON 데이터
        String json = "{\n" +
                "    \"header\": {\n" +
                "        \"resultCode\": \"00\",\n" +
                "        \"resultMsg\": \"NORMAL SERVICE.\"\n" +
                "    },\n" +
                "    \"body\": {\n" +
                "        \"pageNo\": 1,\n" +
                "        \"totalCount\": 42,\n" +
                "        \"numOfRows\": 3,\n" +
                "        \"items\": [\n" +
                "            {\n" +
                "                \"DESC_KOR\": \"바나나칩\",\n" +
                "                \"SERVING_WT\": \"30\",\n" +
                "                \"NUTR_CONT1\": \"49.50\",\n" +
                "                \"NUTR_CONT2\": \"5.40\",\n" +
                "                \"NUTR_CONT3\": \"0.30\",\n" +
                "                \"NUTR_CONT4\": \"3.00\",\n" +
                "                \"NUTR_CONT5\": \"3.30\",\n" +
                "                \"NUTR_CONT6\": \"0.60\",\n" +
                "                \"NUTR_CONT7\": \"0.00\",\n" +
                "                \"NUTR_CONT8\": \"2.70\",\n" +
                "                \"NUTR_CONT9\": \"0.00\",\n" +
                "                \"BGN_YEAR\": \"2013\",\n" +
                "                \"ANIMAL_PLANT\": \"(유)돌코리아\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"DESC_KOR\": \"바나나칩\",\n" +
                "                \"SERVING_WT\": \"30\",\n" +
                "                \"NUTR_CONT1\": \"49.50\",\n" +
                "                \"NUTR_CONT2\": \"5.40\",\n" +
                "                \"NUTR_CONT3\": \"0.30\",\n" +
                "                \"NUTR_CONT4\": \"3.00\",\n" +
                "                \"NUTR_CONT5\": \"3.30\",\n" +
                "                \"NUTR_CONT6\": \"0.60\",\n" +
                "                \"NUTR_CONT7\": \"0.00\",\n" +
                "                \"NUTR_CONT8\": \"2.70\",\n" +
                "                \"NUTR_CONT9\": \"0.00\",\n" +
                "                \"BGN_YEAR\": \"2017\",\n" +
                "                \"ANIMAL_PLANT\": \"(유)돌코리아\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"DESC_KOR\": \"바나나칩\",\n" +
                "                \"SERVING_WT\": \"30\",\n" +
                "                \"NUTR_CONT1\": \"46.50\",\n" +
                "                \"NUTR_CONT2\": \"6.00\",\n" +
                "                \"NUTR_CONT3\": \"0.30\",\n" +
                "                \"NUTR_CONT4\": \"2.40\",\n" +
                "                \"NUTR_CONT5\": \"1.50\",\n" +
                "                \"NUTR_CONT6\": \"0.00\",\n" +
                "                \"NUTR_CONT7\": \"0.00\",\n" +
                "                \"NUTR_CONT8\": \"1.80\",\n" +
                "                \"NUTR_CONT9\": \"0.00\",\n" +
                "                \"BGN_YEAR\": \"2013\",\n" +
                "                \"ANIMAL_PLANT\": \"(주)금호FD\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";

        // JSON을 DTO로 파싱
        FoodItems foodItems = foodService.parsingJsonObject(json);

        // 검증
        assertNotNull(foodItems);
        assertEquals(3, foodItems.getFoodItems().size()); // items의 개수가 3개인지 확인
        // 추가적인 필드별 검증은 필요에 따라 구현
    }
}