package B612.food.customization.service;

import B612.food.customization.service.domain.Food;
import B612.food.customization.service.domain.Nutrition;
import B612.food.customization.service.dto.FoodItems;
import B612.food.customization.service.service.FoodItemApiService;
import B612.food.customization.service.service.FoodService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDB {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.initFood();
        initService.initFoodFormApi();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final FoodService foodService;
        private final FoodItemApiService foodItemApiService;

        public void initFood() {
            Nutrition nutrition1 = new Nutrition(1f, 2f, 3f, 4f, 5f, null, 6f, 7f, null, 8f);
            Nutrition nutrition2 = new Nutrition("3.1", "2.3", "4.4", "N/A", "2.1", "9.5", "0.0", "N/A", "N/A", "4.4");
            Nutrition nutrition3 = new Nutrition("0", "0.00", "0.00", "0.00", "0.00", "N/A", "0.00", "N/A", "N/A", "N/A");

            Food food1 = new Food("food1", nutrition1);
            Food food2 = new Food("food2", nutrition2);
            Food food3 = new Food("엄준식", nutrition3);

            foodService.save(food1);
            foodService.save(food2);
            foodService.save(food3);
        }

        public void initFoodFormApi() {
            FoodItems foodItems = foodItemApiService.fetchFoodItemsFromExternalAPI("", "", "", "", "5");

            foodItems.getFoodItems().forEach(item -> {
                String name = item.getName();
                String servingWt = item.getServingWt();
                String fat = item.getFat();
                String calories = item.getCalories();
                String cholesterol = item.getCholesterol();
                String carbonHydrate = item.getCarbonHydrate();
                String natrium = item.getNatrium();
                String protein = item.getProtein();
                String saturatedFattyAcid = item.getSaturatedFattyAcid();
                String sugar = item.getSugar();
                String transFattyAcid = item.getTransFattyAcid();

                Nutrition nutrition = new Nutrition(servingWt, calories, carbonHydrate, protein, fat, sugar, natrium, cholesterol, saturatedFattyAcid, transFattyAcid);
                Food food = new Food(name, nutrition);
                foodService.save(food);
            });
        }
    }
}

