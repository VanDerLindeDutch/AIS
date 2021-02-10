package ru.FSPO.AIS.services;

public enum TypeOfDownloadedFile {
    IMAGE_FLOOR("C:\\Users\\Lamer\\Desktop\\AIS\\src\\main\\webapp\\resources\\floorsPhotos\\"),
    IMAGE_BC("C:\\Users\\Lamer\\Desktop\\AIS\\src\\main\\webapp\\resources\\uploads\\");


    String path;

    TypeOfDownloadedFile(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
