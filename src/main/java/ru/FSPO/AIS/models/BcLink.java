package ru.FSPO.AIS.models;


import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.sql.Date;

public class BcLink {

    private long bcLinkId;
    @Size(min = 3, max=20, message = "Name should be between 3 and 20 characters")
    private String firstName;
    //  private String lastName;
    private java.sql.Date birthDate;
    @Size(min = 10, max= 100,message = "Email should be valid")
    private String email;
    //  private String phoneNumber;
    @Size(min = 5, max=20, message = "Login should be between 5 and 20 characters")
    private String login;
    @Size(min = 5, max=20, message = "Password should be between 5 and 20 characters")
    private String password;


    public BcLink(long bcLinkId, String firstName, Date birthDate, String email, String login, String password) {
        this.bcLinkId = bcLinkId;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.email = email;
        this.login = login;
        this.password = password;
    }

    public BcLink() {

    }

    public long getBcLinkId() {
        return bcLinkId;
    }

    public void setBcLinkId(long bcLinkId) {
        this.bcLinkId = bcLinkId;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public java.sql.Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(java.sql.Date birthDate) {
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

    @Override
    public String toString() {
        return "BcLink{" +
                "bcLinkId=" + bcLinkId +
                ", firstName='" + firstName + '\'' +
                ", birthDate=" + birthDate +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
