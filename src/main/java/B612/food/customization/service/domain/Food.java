package B612.food.customization.service.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Food {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Embedded
    private Nutrition nutrition;

    public Food(String name, Nutrition nutrition) {
        this.name = name;
        this.nutrition = nutrition;
    }
}
