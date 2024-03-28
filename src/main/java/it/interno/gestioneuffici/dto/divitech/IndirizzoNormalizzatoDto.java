package it.interno.gestioneuffici.dto.divitech;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class IndirizzoNormalizzatoDto {

    private String comune;
    private String indirizzo;
    private String coordinataX;
    private String coordinataY;

}
