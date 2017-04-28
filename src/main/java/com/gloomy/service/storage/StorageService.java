package com.gloomy.service.storage;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author YNC on 24/04/2017.
 */
@Service
public class StorageService {

    private final Path mRootLocation = Paths.get("upload-dir");

    public void store(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.mRootLocation.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
    }

    public Resource loadFile(String filename) {
        try {
            Path file = mRootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("FAIL!");
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(mRootLocation.toFile());
    }

    public void deleteFile(String fileName) {
        Path path = Paths.get(String.format("upload-dir%s", fileName));
        if (path != null) {
            FileSystemUtils.deleteRecursively(path.toFile());
        }
    }

    public void init() {
        try {
            if (!Files.exists(mRootLocation)) {
                Files.createDirectory(mRootLocation);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!");
        }
    }
}