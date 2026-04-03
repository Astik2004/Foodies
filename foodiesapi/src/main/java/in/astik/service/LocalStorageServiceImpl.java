package in.astik.service;

import in.astik.exception.FileStorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class LocalStorageServiceImpl implements StorageService {

    @Value("${app.storage.upload-dir}")
    private String uploadDir;

    @Value("${app.storage.base-url}")
    private String baseUrl;

    @Override
    public String uploadFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new FileStorageException("No file uploaded or file is empty");
        }
        try {
            Files.createDirectories(Paths.get(uploadDir));
            String originalName = file.getOriginalFilename();
            if (originalName == null || !originalName.matches(".*\\.(png|jpg|jpeg|webp|gif)$")) {
                throw new FileStorageException("Invalid file type. Allowed: png, jpg, jpeg, webp, gif");
            }

            String fileName = System.currentTimeMillis() + "_" + originalName;

            Path path = Paths.get(uploadDir + fileName);
            Files.write(path, file.getBytes());
            return baseUrl + fileName;
        } catch (IOException e) {
            throw new FileStorageException("Error saving file: " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public boolean deleteFile(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty()) {
            return false;
        }
        String fileName = "";
        try {
            fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            Path path = Paths.get(uploadDir + fileName);

            if (!Files.exists(path)) {
                throw new FileStorageException("Image not found: " + fileName);
            }

            Files.delete(path);
            return true;

        } catch (IOException e) {
            throw new FileStorageException("Error deleting file: " + fileName, e);
        }
    }
}
