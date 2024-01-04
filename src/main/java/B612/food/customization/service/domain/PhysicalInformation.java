package B612.food.customization.service.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class PhysicalInformation {
    @Id
    @GeneratedValue
    private Long id;

    private double height; // 단위(cm)
    private double bodyWeight;
    private double bmi; // bmi 지수

    @Setter
    private double skeletalMuscleMass; // 골격근량
    @Setter
    private double bodyFat; // 체질량지수 (kg/m^2)

    @Enumerated
    private Obesity obesity;
    // 비만도 계산 로직 구현 필요

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
