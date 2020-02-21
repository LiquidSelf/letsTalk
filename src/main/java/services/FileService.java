package services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {

    @Value("${app.upload.dir.name:${user.home}}")
    public String uploadDirName;

    public void uploadFile(MultipartFile file) {
        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            File uploadDir = new File(System.getProperty( "catalina.base" ) +File.separator+ uploadDirName +File.separator);
            if(!uploadDir.exists()) uploadDir.mkdir();
            File newFile = new File(uploadDir, fileName);
            if(newFile.exists()) throw new RuntimeException("file exists");

            Files.copy(file.getInputStream(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could not store file " + file.getOriginalFilename()
                    + ". Please try again!");
        }
    }
}