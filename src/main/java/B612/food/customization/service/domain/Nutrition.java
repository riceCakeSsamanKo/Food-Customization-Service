package B612.food.customization.service.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Nutrition {
    // 문자열 데이터를 소수로 다루기 위해서 wrapper class Float를 사용
    private Float servingWeight; // 1회 제공량(g)
    private Float calories; // 열량(kcal)

    private Float carbonHydrate; // 탄수화물(g)
    private Float protein;  //단백질(g)
    private Float fat;  //지방(g)
    private Float sugar;  //당류(g)
    private Float natrium;  //나트륨(mg)
    private Float cholesterol;  //콜레스트롤(mg)
    private Float saturatedFattyAcid;  // 포화지방산(g)
    private Float transFattyAcid; //트랜스지방산(g)

    public Nutrition(String servingWt, String calories, String carbonHydrate,
                     String protein, String fat, String sugar, String natrium,
                     String cholesterol, String saturatedFattyAcid, String transFattyAcid) {

        // 받은 json 데이터 값이 “N/A”인 경우 null을 넣어줌.
        this.servingWeight = stringToFloatConvert(servingWt);
        this.calories = stringToFloatConvert(calories);
        this.carbonHydrate = stringToFloatConvert(carbonHydrate);
        this.protein = stringToFloatConvert(protein);
        this.fat = stringToFloatConvert(fat);
        this.sugar = stringToFloatConvert(sugar);
        this.natrium = stringToFloatConvert(natrium);
        this.cholesterol = stringToFloatConvert(cholesterol);
        this.saturatedFattyAcid = stringToFloatConvert(saturatedFattyAcid);
        this.transFattyAcid = stringToFloatConvert(transFattyAcid);
    }

    // 받은 json 데이터 값이 “N/A”인 경우 null을 넣어줌.
    private Float stringToFloatConvert(String data) {
        if (data.equals("N/A")) {
            return null;
        }
        return Float.parseFloat(data);
    }
}
