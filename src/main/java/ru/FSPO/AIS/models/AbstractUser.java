package ru.FSPO.AIS.models;

import javax.validation.constraints.Size;
import java.sql.Date;

public abstract class AbstractUser {
    protected long id;
    @Size(min = 3, max=20, message = "Name should be between 3 and 20 characters")
    protected String firstName;
    protected java.sql.Date birthDate;
    @Size(min = 10, max= 100,message = "Email should be valid")
    protected String email;
    //  private String phoneNumber;
    @Size(min = 5, max=20, message = "Login should be between 5 and 20 characters")
    protected String login;
//    @Size(min = 5, max=20, message = "Password should be between 5 and 20 characters")
    protected String password;

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract Role getRole();
}
