package B612.food.customization.service.service;

import B612.food.customization.service.domain.Obesity;
import B612.food.customization.service.domain.User;
import B612.food.customization.service.exception.NoDataException;
import B612.food.customization.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = false)
    public Long saveUser(User user) {
        // 동일한 아이디의 유저는 등록이 불가해야함
        if (validateSaveUser(user.getLogIn().getLogin_id())) {
            throw new IllegalStateException("발생위치: UserService.saveUser(User user)" +
                    "발생이유: 해당 로그인 아이디로 가입된 회원이 이미 존재합니다.");
        }
        userRepository.save(user);
        return user.getId();
    }

    private boolean validateSaveUser(String logIn_id) {
        Optional<User> findUser = userRepository.findByLogInId(logIn_id);
        return !findUsers().isEmpty();
    }

    public User findUser(Long userId) {
        return userRepository.findOne(userId);
    }

    public User findUserByLogInId(String login_id) throws NoDataException {
        Optional<User> findUser = userRepository.findByLogInId(login_id);
        if (findUser.isEmpty()) {
            throw new NoDataException("발생위치: UserService.findUserByLogInId(String login_id)\n" +
                    "발생이유: 해당 이름의 회원을 찾을 수 없습니다.");
        }
        return findUser.get();
    }

    public User findUserByEmail(String email) throws NoDataException {
        Optional<User> findUser = userRepository.findByEmail(email);
        if (findUser.isEmpty()) {
            throw new NoDataException("발생위치: UserService.findUserByEmail(String email)\n" +
                    "발생이유: 해당 이메일을 가진 회원을 찾을 수 없습니다.");
        }
        return findUser.get();
    }

    public User findUserByPhoneNumber(String phoneNumber) throws NoDataException {
        Optional<User> findUser = userRepository.findByPhoneNumber(phoneNumber);
        if (findUser.isEmpty()) {
            throw new NoDataException("발생위치: UserService.findByPhoneNumber(String phoneNumber)\n" +
                    "발생이유: 해당 전화번호를 가진 회원을 찾을 수 없습니다.");
        }
        return findUser.get();
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }
    public List<User> findUserByName(String name) {
        return userRepository.findByName(name);
    }

    public List<User> findUserByObesity(Obesity obesity, Integer pagingStartOffset, Integer numOfPage) {
        if (pagingStartOffset == null) pagingStartOffset = 0;
        if (numOfPage == null) numOfPage = 0;

        return userRepository.findByObesity(obesity, pagingStartOffset, numOfPage);
    }
}
