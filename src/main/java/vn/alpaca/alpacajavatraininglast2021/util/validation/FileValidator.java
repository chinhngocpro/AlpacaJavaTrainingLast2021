package vn.alpaca.alpacajavatraininglast2021.util.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileValidator
        implements ConstraintValidator<NotEmptyFile, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile file,
                           ConstraintValidatorContext context) {
        return !file.isEmpty();
    }
}
