package ru.FSPO.AIS.services;

public enum TypeOfDownloadedFile {
    IMAGE_PLACEMENT("C:\\Users\\Lamer\\Desktop\\AIS\\src\\main\\webapp\\resources\\photos\\placementsPhotos\\"),
    IMAGE_FLOOR("C:\\Users\\Lamer\\Desktop\\AIS\\src\\main\\webapp\\resources\\photos\\floorsPhotos\\"),
    IMAGE_BC("C:\\Users\\Lamer\\Desktop\\AIS\\src\\main\\webapp\\resources\\photos\\uploads\\");


    String path;

    TypeOfDownloadedFile(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
