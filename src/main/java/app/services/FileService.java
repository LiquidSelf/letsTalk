package app.services;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import web.controllers.JWTAuth;

import java.io.File;
import java.nio.file.Files;
import java.util.Objects;

@Service
public class FileService {

    @Autowired
    private File uplDir;

    private static final Log logger = LogFactory.getLog(JWTAuth.class);

    public File uploadFile(MultipartFile file) {
        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())),
                   extension = FilenameUtils.getExtension(fileName);

            if(extension == null) throw new RuntimeException("extension is empty");

            File newFile =  File.createTempFile("upl",  "."+extension, uplDir);

            Files.write(newFile.toPath(), file.getBytes());

            return newFile;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could not store file " + file.getOriginalFilename()+ ". Please try again!");
        }
    }
}