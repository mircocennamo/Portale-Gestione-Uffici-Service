package it.interno.gestioneuffici.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.interno.gestioneuffici.serializer.TimestampDeserializer;
import it.interno.gestioneuffici.serializer.TimestampSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GerarchiaSSDDto {
    private String codiceUfficioPrincipale;
    private UfficioDto ufficioDipendente;
    @JsonSerialize(using = TimestampSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    private Timestamp dataInserimento;
    private String utenteInserimento;
    private String ufficioInserimento;
    @JsonSerialize(using = TimestampSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    private Timestamp dataCancellazione;
    private String utenteCancellazione;
    private String ufficioCancellazione;
    private String codiceUfficioStaff;
}
