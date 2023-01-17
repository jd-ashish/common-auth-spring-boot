package com.projects.app.commons.utils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUploader {

    private final String path;

    public FileUploader(String path) {
        this.path = path;
    }

    public String uploadMultipartFile(MultipartFile file) {
        String name = file.getOriginalFilename();
        String randomID = UUID.randomUUID().toString();
        assert name != null;
        String fileName1 = randomID.concat(name.substring(name.lastIndexOf(".")));
        String filePath = path + File.separator + fileName1;
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }
        try {
            Files.copy(file.getInputStream(), Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileName1;
    }

    public InputStream getResource(String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        return new FileInputStream(fullPath);
    }

    public String getBase64(MultipartFile file) throws IOException {
        return "data:image/png;base64," + Base64.encodeBase64String(file.getBytes());
    }

    public void deleteFile(String profilePicture) {
        File file = new File(profilePicture);
        File file1 = new File(file.getAbsolutePath());
        file1.delete();
    }

}
