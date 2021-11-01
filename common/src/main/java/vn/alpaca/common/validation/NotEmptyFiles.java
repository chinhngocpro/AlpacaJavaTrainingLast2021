package vn.alpaca.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = FilesValidator.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface NotEmptyFiles {

    String message() default "File should not be empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}