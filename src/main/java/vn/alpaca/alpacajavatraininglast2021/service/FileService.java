package vn.alpaca.alpacajavatraininglast2021.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class FileService {

    @Value( "${alpaca.resource.save.:C:/upload}" )
    private String savePath;

    public String saveFile(MultipartFile file) {
        try {
            String fileName = String.format("%s.%s", UUID.randomUUID(), FilenameUtils.getExtension(file.getName()));
            File f = new File(savePath, fileName);
            file.transferTo(f);

            return f.getPath();
        } catch (Exception e) {
            return null;
        }

    }
}
