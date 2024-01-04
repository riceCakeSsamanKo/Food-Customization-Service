package B612.food.customization.service.service;

import B612.food.customization.service.domain.User;
import B612.food.customization.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = false)
    public Long saveUser(User user) {
        // 동일한 아이디의 유저는 등록이 불가해야함
        userRepository.save(user);
        return user.getId();
    }
}
