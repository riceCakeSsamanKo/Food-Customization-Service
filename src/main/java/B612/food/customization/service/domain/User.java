package B612.food.customization.service.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "USERS")
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

    @Enumerated
    private Sex sex;

    @OneToOne
    @JoinColumn()
    private PhysicalInformation physicalInfo;


    public User(String name, Sex sex, LogIn login, Address address) {
        this.name = name;
        this.sex = sex;
        this.logIn = login;
        this.address = address;
    }

    public User(String name, Sex sex, LogIn login, Address address, String email) {
        this.name = name;
        this.sex = sex;
        this.logIn = login;
        this.address = address;
        this.email = email;
    }

}
