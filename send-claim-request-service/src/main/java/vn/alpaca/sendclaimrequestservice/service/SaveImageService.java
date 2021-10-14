package vn.alpaca.sendclaimrequestservice.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class SaveImageService {

    @Value("${alpaca.resource.save:C:/upload}")
    private String savePath;

    public String saveFile(MultipartFile file) {
        try {
            String fileName = String.format(
                    "%s.%s",
                    UUID.randomUUID(),
                    FilenameUtils.getExtension(file.getOriginalFilename())
            );
            File f = new File(savePath, fileName);
            file.transferTo(f);

            return fileName;
        } catch (Exception e) {
            return null;
        }

    }
}
