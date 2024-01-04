package B612.food.customization.service.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class LogIn {
    private String login_id;
    private String password;
}
