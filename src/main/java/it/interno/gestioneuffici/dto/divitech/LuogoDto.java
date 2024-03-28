package it.interno.gestioneuffici.dto.divitech;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class LuogoDto {

    Integer code;
    Body body;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Body {
        Integer codiceLuogo;
        String descrizioneLuogo;
        String codiceComune;
        String siglaProvincia;
        String coordinataX;
        String coordinataY;
        String numeroCivico;
        String indirizzo;
        boolean flagIndirizzoValidoSdi;
    }
}
