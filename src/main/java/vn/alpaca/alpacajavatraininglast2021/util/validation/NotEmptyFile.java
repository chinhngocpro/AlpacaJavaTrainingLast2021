package vn.alpaca.alpacajavatraininglast2021.util.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = FileValidator.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface NotEmptyFile {

    String message() default "File should not be empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
