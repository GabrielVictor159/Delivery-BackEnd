package com.Delivery.delivery.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@Service
public class ImageService {

    private static final String IMAGE_UPLOAD_DIRECTORY = "src/main/resources/static/images/";

    public String salvar(MultipartFile image) throws IOException {
        File directory = new File(IMAGE_UPLOAD_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File file = new File(directory, image.getOriginalFilename());
        image.transferTo(file);
        return file.getAbsolutePath();
    }

    public void excluir(String location) throws IOException {
        File file = new File(location);
        if (file.exists()) {
            file.delete();
        }
    }
}
