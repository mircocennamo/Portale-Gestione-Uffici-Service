package it.interno.gestioneuffici.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = ValidazioneDateUfficioValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidazioneDateUfficio {

    String message() default "Errore di validazione";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

}
