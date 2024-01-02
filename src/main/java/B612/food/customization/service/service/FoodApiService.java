package B612.food.customization.service.service;

import B612.food.customization.service.dto.FoodItems;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

@Service
public class FoodApiService {
    public FoodItems parsingJsonObject(String json) {
        FoodItems result = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.readValue(json, FoodItems.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public LinkedMultiValueMap<String, String> makeParam(String name, String company, String beginYear, String pageNo, String numOfRows) {
        LinkedMultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("desc_kor", name);
        param.add("animal_plant", company);
        param.add("bgn_year", beginYear);
        param.add("pageNo", pageNo);
        param.add("numOfRows", numOfRows);

        return param;
    }
}
