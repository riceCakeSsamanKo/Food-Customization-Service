package domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Nutrition {
    private int servingWeight; // 1회 제공량(g)
    private int calories; // 열량(kcal)

    private int carbohydrate; // 탄수화물(g)
    private int protein;  //단백질(g)
    private int fat;  //지방(g)
    private int sugar;  //당류(g)
    private int natrium;  //나트륨(mg)
    private int cholesterol;  //콜레스트롤(mg)
    private int saturatedFattyAcid;  // 포화지방산(g)
    private int transFattyAcid; //트랜스지방산(g)
}
