package B612.food.customization.service.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Address {
    private String city; // 시, 군
    private String street; // 동, 읍, 면 + 자택 주소
    private String zipcode;  // 우편 번호
}
