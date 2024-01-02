package B612.food.customization.service.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Nutrition {
    private float servingWeight; // 1회 제공량(g)
    private float calories; // 열량(kcal)

    private float carbonHydrate; // 탄수화물(g)
    private float protein;  //단백질(g)
    private float fat;  //지방(g)
    private float sugar;  //당류(g)
    private float natrium;  //나트륨(mg)
    private float cholesterol;  //콜레스트롤(mg)
    private float saturatedFattyAcid;  // 포화지방산(g)
    private float transFattyAcid; //트랜스지방산(g)

    public Nutrition(String servingWt, String calories, String carbonHydrate,
                     String protein, String fat, String sugar, String natrium,
                     String cholesterol, String saturatedFattyAcid, String transFattyAcid) {
        this.servingWeight = Float.parseFloat(servingWt);
        this.calories = Float.parseFloat(calories);
        this.carbonHydrate = Float.parseFloat(carbonHydrate);
        this.protein = Float.parseFloat(protein);
        this.fat = Float.parseFloat(fat);
        this.sugar = Float.parseFloat(sugar);
        this.natrium = Float.parseFloat(natrium);
        this.cholesterol = Float.parseFloat(cholesterol);
        this.saturatedFattyAcid = Float.parseFloat(saturatedFattyAcid);
        this.transFattyAcid = Float.parseFloat(transFattyAcid);
    }
}
