package B612.food.customization.service.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static B612.food.customization.service.domain.Sex.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserTest {

    @Test
    @DisplayName("User 객체 생성")
    public void create() throws Exception{
        //given
        PhysicalInformation physicalInformation = new PhysicalInformation(180, 84);

        //when
        User user1 = new User("user1", MALE, new LogIn("123", "123"), new Address("성남시", "분당구 분당동", "12345"));
        User user2 = new User("user2", MALE, new LogIn("123", "123"), new Address("성남시", "분당구 분당동", "12345"),"umjunsik@naver.com");
        User user3 = new User("user3", MALE, new LogIn("123", "123"), new Address("성남시", "분당구 분당동", "12345"), physicalInformation);

        physicalInformation.setBodyFat(24);
        User user4 = new User("user4", MALE, new LogIn("123", "123"), new Address("성남시", "분당구 분당동", "12345"), "umjunsik@naver.com", physicalInformation);

        //then
        System.out.println(user1);
        System.out.println(user2);
        System.out.println(user3);
        System.out.println(user4);

    }
}