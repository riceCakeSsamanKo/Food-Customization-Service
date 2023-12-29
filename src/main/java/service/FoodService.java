package service;

import domain.Food;
import exception.NoDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.FoodRepository;

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
}
