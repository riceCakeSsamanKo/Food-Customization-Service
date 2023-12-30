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
    public void callOpenApi () throws Exception{
        //given
        String name = "바나나칩";

        //when
        LinkedMultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        /*param.add("desc_kor", name);
        param.add("bgn_year","2017");
        param.add("animal_plant","(유)돌코리아");
        param.add("pageNo","10");
        param.add("numOfRows","1");*/

        //then
        this.mvc.perform(get("/open-api/food").params(param))
                .andExpect(status().isOk())
                .andDo(print());
    }
}