package ru.FSPO.AIS.dao;

public enum UserType {
    RENTER("RENTER"),
    LANDLORD("LANDLORD");
    private String name;

    UserType(String name) {
        this.name = name;
    }


    public String getName(){
        return name;
    }
}
