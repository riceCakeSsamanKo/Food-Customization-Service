package B612.food.customization.service.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String email;

    @Embedded
    private LogIn logIn;

    @Embedded
    private Address address;



    public User(String name, LogIn login, Address address) {
        this.name = name;
        this.logIn = login;
        this.address = address;
    }

    public User(String name, LogIn login, Address address, String email) {
        this.name = name;
        this.logIn = login;
        this.address = address;
        this.email = email;
    }

}
