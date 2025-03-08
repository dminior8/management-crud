package pl.dminior.management_crud.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CategoryPriceValidator.class)
@Target({ElementType.TYPE}) // Walidacja dot. klasy (Product)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCategoryPrice {
    String message() default "Cena nie pasuje do podanej kategorii";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

