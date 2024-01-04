package B612.food.customization.service.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

import static lombok.AccessLevel.*;

@Embeddable
@Getter
@ToString(exclude = "password")
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class LogIn {
    private String login_id;
    private String password;
}
