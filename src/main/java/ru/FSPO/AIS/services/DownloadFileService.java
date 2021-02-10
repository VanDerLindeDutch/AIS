package ru.FSPO.AIS.services;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class DownloadFileService {
    public static void DownloadFile(MultipartFile file, TypeOfDownloadedFile typeOfDownloadedFile) throws IOException {
        String imagePath = file.getOriginalFilename();
        Path path = Paths.get(typeOfDownloadedFile.getPath() + StringUtils.cleanPath(Objects.requireNonNull(imagePath)));
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    }
}
