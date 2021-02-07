package ru.FSPO.AIS.models;


import javax.validation.constraints.Size;
import java.sql.Date;

public class RenterLink {

  private long renterId;
  @Size(min = 3, max=20, message = "Name should be between 3 and 20 characters")
  private String firstName;
  private java.sql.Date birthDate;
  private long companyId;
  @Size(min = 5, max=20, message = "Login should be between 5 and 20 characters")
  private String login;
  @Size(min = 5, max=20, message = "Password should be between 5 and 20 characters")
  private String password;
  @Size(min = 10, max= 100,message = "Email should be valid")
  private String email;

  public RenterLink() {
  }

  public RenterLink(long renterId, String firstName, Date birthDate, String login, String password, String email) {
    this.renterId = renterId;
    this.firstName = firstName;
    this.birthDate = birthDate;
    this.login = login;
    this.password = password;
    this.email = email;
  }

  public long getRenterId() {
    return renterId;
  }

  public void setRenterId(long renterId) {
    this.renterId = renterId;
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


  public long getCompanyId() {
    return companyId;
  }

  public void setCompanyId(long companyId) {
    this.companyId = companyId;
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


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "RenterLink{" +
            "renterId=" + renterId +
            ", firstName='" + firstName + '\'' +
            ", birthDate=" + birthDate +
            ", companyId=" + companyId +
            ", login='" + login + '\'' +
            ", password='" + password + '\'' +
            ", email='" + email + '\'' +
            '}';
  }
}
