package service;

import domain.Food;
import exception.NoDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.FoodRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;

    @Transactional(readOnly = false)
    public void save(Food food) {
        foodRepository.save(food);
    }

    public Food findFood(Long foodId) {
        return foodRepository.findOne(foodId);
    }

    public Food findFood(String name) throws NoDataException {
        Optional<Food> findFood = foodRepository.findByName(name);
        if (findFood.isEmpty()) {
            throw new NoDataException("발생위치: FoodService.findFood(String name)\n" +
                    "발생이유: 해당 이름의 식품을 찾을 수 없습니다.");
        }
        return findFood.get();
    }

    public List<Food> findFoods() {
        return foodRepository.findAll();
    }

    /**
     * 영양소별 식품 검색
     * @param calories
     * @return List<Food>
     */
    public List<Food> findFoodThatHasMoreCalories(int calories) {
        return foodRepository.findThatHasMoreCarlories(calories);
    }

    public List<Food> findFoodThatHasLessCalories(int calories) {
        return foodRepository.findThatHasLessCarlories(calories);
    }

    public List<Food> findFoodThatHasMoreProtein(int protein) {
        return foodRepository.findThatHasMoreProtein(protein);
    }

    public List<Food> findFoodThatHasMoreFat(int fat) {
        return foodRepository.findThatHasMoreFat(fat);
    }

    public List<Food> findFoodThatHasLessFat(int fat) {
        return foodRepository.findThatHasLessFat(fat);
    }
}