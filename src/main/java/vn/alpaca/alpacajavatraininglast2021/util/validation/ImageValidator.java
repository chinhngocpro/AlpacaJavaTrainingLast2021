package vn.alpaca.alpacajavatraininglast2021.util.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class ImageValidator
        implements ConstraintValidator<ValidImage, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile file,
                           ConstraintValidatorContext context) {
        String contentType = file.getContentType();
        return isSupportedContentType(contentType);
    }

    private boolean isSupportedContentType(String contentType) {
        List<String> supportedContents =
                Arrays.asList("image/jpg", "image/jpeg", "image/png");
        return supportedContents.contains(contentType);
    }
}