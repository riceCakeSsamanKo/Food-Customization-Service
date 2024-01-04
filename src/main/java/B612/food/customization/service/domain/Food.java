package B612.food.customization.service.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.*;

@Entity
@Getter
@AllArgsConstructor
@ToString
@NoArgsConstructor(access = PROTECTED)
public class Food {
    @Id
    @GeneratedValue
    @Column(name = "food_id")
    private Long id;

    private String name;

    @Embedded
    private Nutrition nutrition;

    public Food(String name, Nutrition nutrition) {
        this.name = name;
        this.nutrition = nutrition;
    }
}
