package com.Delivery.delivery.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.apache.commons.io.FileUtils;

@Service
public class ImageService {

    private static final String IMAGE_UPLOAD_DIRECTORY = "src/main/resources/static/images/";

    public String salvar(byte[] image) throws IOException {
        File directory = new File(IMAGE_UPLOAD_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String fileName = UUID.randomUUID().toString() + ".jpeg"; // Gera um nome aleatório para a imagem
        File file = new File(directory, fileName);
        FileUtils.writeByteArrayToFile(file, image);

        // Obtém o caminho relativo ao IMAGE_UPLOAD_DIRECTORY
        String relativePath = file.getName();
        return relativePath;
    }

    public void excluir(String fileName) throws IOException {
        File file = new File(IMAGE_UPLOAD_DIRECTORY, fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}
