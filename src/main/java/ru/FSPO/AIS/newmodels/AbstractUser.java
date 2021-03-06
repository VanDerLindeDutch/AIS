package ru.FSPO.AIS.newmodels;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;

@MappedSuperclass
@Data
@NoArgsConstructor
public abstract class AbstractUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Size(min = 3, max=20, message = "Name should be between 3 and 20 characters")
    @Column(name = "first_name")
    protected String firstName;
    @Column(name = "birth_date")
    protected Date birthDate;
    @Size(min = 10, max= 100,message = "Email should be valid")
    protected String email;
    //  private String phoneNumber;
    @Size(min = 5, max=20, message = "Login should be between 5 and 20 characters")
    protected String login;
//    @Size(min = 5, max=20, message = "Password should be between 5 and 20 characters")
    protected String password;

    public AbstractUser(Long id) {
        this.id = id;
    }

    public abstract Role getRole();
}
