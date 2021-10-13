package vn.alpaca.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class FilesValidator
        implements ConstraintValidator<NotEmptyFiles, List<MultipartFile>> {
    @Override
    public boolean isValid(List<MultipartFile> files,
                           ConstraintValidatorContext context) {
        for (MultipartFile file : files) {
            if (file.isEmpty()) return false;
        }
        return true;
    }
}