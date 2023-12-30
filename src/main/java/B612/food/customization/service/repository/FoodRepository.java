package B612.food.customization.service.repository;

import B612.food.customization.service.domain.Food;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FoodRepository {
    private final EntityManager em;

    public void save(Food food) {
        em.persist(food);
    }

    public Food findOne(Long foodId) {
        return em.find(Food.class, foodId);
    }

    public Optional<Food> findByName(String name) {
        List<Food> findFoods = em.createQuery("select f from Food f " +
                        "where f.name = :name", Food.class)
                .setParameter("name", name)
                .getResultList();

        return findFoods.stream().findAny();
    }

    public List<Food> findAll() {
        return em.createQuery("select f from Food f", Food.class)
                .getResultList();
    }

    /**
     * 영양소별 검색 (탄단지)
     */

    // 탄수화물
    public List<Food> findThatHasMoreCarlories(int calories) {
        return em.createQuery("select f from Food f " +
                        "where f.nutrition.calories >= :calories", Food.class)
                .setParameter("calories", calories)
                .getResultList();
    }

    public List<Food> findThatHasLessCarlories(int calories) {
        return em.createQuery("select f from Food f " +
                        "where f.nutrition.calories <= :calories", Food.class)
                .setParameter("calories", calories)
                .getResultList();
    }

    // 단백질
    public List<Food> findThatHasMoreProtein(int protein) {
        return em.createQuery("select f from Food f " +
                        "where f.nutrition.protein >= :protein", Food.class)
                .setParameter("protein", protein)
                .getResultList();
    }

    //지방
    public List<Food> findThatHasMoreFat(int fat) {
        return em.createQuery("select f from Food f " +
                        "where f.nutrition.fat >= :fat", Food.class)
                .setParameter("fat", fat)
                .getResultList();
    }

    public List<Food> findThatHasLessFat(int fat) {
        return em.createQuery("select f from Food f " +
                        "where f.nutrition.fat <= :fat", Food.class)
                .setParameter("fat", fat)
                .getResultList();
    }

}
