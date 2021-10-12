package vn.alpaca.userservice.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.regex.Pattern;

public class IsNumbersValidator
        implements ConstraintValidator<Numbers, List<String>> {

    private final Pattern pattern = Pattern.compile("\\d+");

    @Override
    public boolean isValid(List<String> values,
                           ConstraintValidatorContext context) {
        for (String value : values) {
            if (!isNumeric(value)) return false;
        }
        return true;
    }

    private boolean isNumeric(String value) {
        if (value == null) return false;
        return pattern.matcher(value).matches();
    }
}
