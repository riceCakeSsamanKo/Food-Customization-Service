package B612.food.customization.service.api;

import B612.food.customization.service.service.FoodService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FoodApiController.class)
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
        String koreanParam1 = "바나나칩";
        String encodedParam1 = URLEncoder.encode(koreanParam1, StandardCharsets.UTF_8);
        String koreanParam2 = "(유)돌코리아";
        String encodedParam2 = URLEncoder.encode(koreanParam2, StandardCharsets.UTF_8);

        //when
        LinkedMultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("desc_kor", encodedParam1);
        param.add("bgn_year", "2017");
        param.add("animal_plant", encodedParam2);
        param.add("pageNo", "1");
        param.add("numOfRows", "3");

        //then
        this.mvc.perform(get("/open-api/food").params(param))
                .andExpect(status().isOk())
                .andDo(print());
    }
}