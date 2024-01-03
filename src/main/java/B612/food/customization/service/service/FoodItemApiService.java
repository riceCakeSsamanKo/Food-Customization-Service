package B612.food.customization.service.service;

import B612.food.customization.service.api.FoodApiController;
import B612.food.customization.service.dto.FoodItems;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class FoodItemApiService {
    private final RestTemplate restTemplate;
    private final FoodApiController foodApiController;

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

    //외부 api로 부터 json 데이터를 가져와서 FoodItem dto에 데이터를 담아 반환
    @Value("${openApi.baseUrl}")
    private String baseUrl;

    @Value("${openApi.ServiceKey}")
    private String serviceKey;

    @Value(value = "${openApi.type}")
    private String type;

    public FoodItems fetchFoodItemsFromExternalAPI(String desc_kor, String animal_plant, String beginYear, String pageNo, String numOfRows) {
        HttpURLConnection urlConnection = null;
        InputStream stream = null;
        String result = null;

        // 한글을 utf-8로 인코딩
        String encodedName = URLEncoder.encode(desc_kor, StandardCharsets.UTF_8);
        String encodedBgnYear = URLEncoder.encode(beginYear, StandardCharsets.UTF_8);
        String encodedAnimalPlant = URLEncoder.encode(animal_plant, StandardCharsets.UTF_8);
        String encodedPageNo = URLEncoder.encode(pageNo, StandardCharsets.UTF_8);
        String encodedNumOfRows = URLEncoder.encode(numOfRows, StandardCharsets.UTF_8);

        String urlStr = baseUrl +
                "serviceKey=" + serviceKey +
                "&type=" + type;

        if (!desc_kor.isEmpty()) {
            urlStr += "&desc_kor=" + encodedName;
        }
        if (!beginYear.isEmpty()) {
            urlStr += "&bgn_year=" + encodedBgnYear;
        }
        if (!animal_plant.isEmpty()) {
            urlStr += "&animal_plant=" + encodedAnimalPlant;
        }
        if (!pageNo.isEmpty()) {
            urlStr += "&pageNo=" + encodedPageNo;
        }
        if (!numOfRows.isEmpty()) {
            urlStr += "&numOfRows=" + encodedNumOfRows;
        }

        try {
            URL url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            stream = getNetworkConnection(urlConnection);
            result = readStreamToString(stream);

            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return parsingJsonObject(result);
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
