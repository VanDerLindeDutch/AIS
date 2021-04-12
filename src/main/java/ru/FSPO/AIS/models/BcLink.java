package ru.FSPO.AIS.models;


public class BcLink extends AbstractUser {

    @Override
    public Role getRole() {
        return Role.LANDLORD;
    }

    @Override
    public String toString() {
        return "BcLink{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", birthDate=" + birthDate +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
