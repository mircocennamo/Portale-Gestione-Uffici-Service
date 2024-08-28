package it.interno.gestioneuffici.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.interno.gestioneuffici.serializer.LocalDateDeserializer;
import it.interno.gestioneuffici.serializer.LocalDateSerializer;
import it.interno.gestioneuffici.serializer.TimestampDeserializer;
import it.interno.gestioneuffici.serializer.TimestampSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LuogoDto {

    private Integer codiceLuogo;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataInizioValidita;
    private String inLuogo;
    private String descrizione;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataFineValidita;
    private String codiceRegione;
    private String codiceProvincia;
    private String codiceComune;
    private String codiceCatastale;
    private String siglaProvincia;
    @JsonSerialize(using = TimestampSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    private Timestamp dataCancellazione;
}
