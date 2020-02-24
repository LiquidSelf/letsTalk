package controllers;

import dto.DTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import services.FileService;

import java.io.File;

@RestController
public class FileUploadREST {
    private static final Log logger = LogFactory.getLog(FileUploadREST.class);

    @Autowired
    FileService fileService;

    @RequestMapping(value="/api/uploadFile", method= RequestMethod.POST)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        try {
            File saved = fileService.uploadFile(file);
            return ResponseEntity.ok(DTO.mk(saved.getName()));
        }catch (Exception ex){
            logger.info(ex);
            return new ResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
