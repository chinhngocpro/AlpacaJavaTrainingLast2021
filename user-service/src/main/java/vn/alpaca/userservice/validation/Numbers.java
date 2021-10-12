package vn.alpaca.userservice.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = IsNumbersValidator.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface Numbers {

    String message() default "Strings can not parse to numbers";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
