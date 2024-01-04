package B612.food.customization.service.domain;

import jakarta.persistence.*;
import lombok.*;

import static B612.food.customization.service.domain.Obesity.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
public class PhysicalInformation {
    @Id
    @GeneratedValue
    @Column(name = "physical_information_id")
    private Long id;

    private double height; // 단위(cm)
    private double bodyWeight;
    private double bmi; // bmi 지수

    @Setter
    private double skeletalMuscleMass; // 골격근량
    @Setter
    private double bodyFat = 0; // 체지방량(%)

    public PhysicalInformation(double height, double bodyWeight) {
        this.height = height;
        this.bodyWeight = bodyWeight;
        this.bmi = 1.3 * bodyWeight / Math.pow((height / 100), 2.5);
    }

    public void setBodyWeight(double bodyWeight) {
        this.bodyWeight = bodyWeight;
        this.bmi = 1.3 * bodyWeight / Math.pow((height / 100), 2.5);
    }

    public void setHeight(double height) {
        this.height = height;
        this.bmi = 1.3 * bodyWeight / Math.pow((height / 100), 2.5);
    }
}
