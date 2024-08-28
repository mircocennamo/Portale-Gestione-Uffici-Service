package it.interno.gestioneuffici.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = ValidazioneUfficioValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ValidazioneUfficio.List.class)
@Documented
public @interface ValidazioneUfficio {

    String message() default "Errore di validazione ufficio.";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List{
        ValidazioneUfficio[] value();
    }
}
