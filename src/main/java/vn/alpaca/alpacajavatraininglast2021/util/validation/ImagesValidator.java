package vn.alpaca.alpacajavatraininglast2021.util.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class ImagesValidator
        implements ConstraintValidator<ValidImages, List<MultipartFile>> {

    @Override
    public boolean isValid(List<MultipartFile> files,
                           ConstraintValidatorContext context) {
        for (MultipartFile file : files) {
            String contentType = file.getContentType();
            if (!isSupportedContentType(contentType)) return false;
        }
        return true;
    }

    private boolean isSupportedContentType(String contentType) {
        List<String> supportedContents =
                Arrays.asList("image/jpg", "image/jpeg", "image/png");
        return supportedContents.contains(contentType);
    }
}