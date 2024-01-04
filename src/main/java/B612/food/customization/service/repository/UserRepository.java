package B612.food.customization.service.repository;

import B612.food.customization.service.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public User findOne(Long userId) {
        return em.find(User.class, userId);
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    public Optional<User> findByName(String name) {
        return em.createQuery("select u from User u " +
                        "left join fetch u.physicalInfo p " +
                        "where u.name = :name", User.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findAny();
    }

    public Optional<User> findByLogInId(String logIn_id) {
        return em.createQuery("select u from User u " +
                        "left join fetch u.physicalInfo p " +
                        "where u.logIn.login_id=:id", User.class)
                .setParameter("id", logIn_id)
                .getResultList()
                .stream()
                .findAny();
    }



}
