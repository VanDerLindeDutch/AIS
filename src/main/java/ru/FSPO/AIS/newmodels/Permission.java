package ru.FSPO.AIS.newmodels;

public enum Permission {
    CREATE_BC("CreateBC"),
    RENT("Rent");

    private final String permission;


    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
