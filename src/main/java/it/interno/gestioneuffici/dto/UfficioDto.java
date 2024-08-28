package it.interno.gestioneuffici.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.interno.gestioneuffici.annotation.ValidazioneDateUfficio;
import it.interno.gestioneuffici.annotation.ValidazioneUfficio;
import it.interno.gestioneuffici.serializer.LocalDateDeserializer;
import it.interno.gestioneuffici.serializer.LocalDateSerializer;
import it.interno.gestioneuffici.serializer.TimestampDeserializer;
import it.interno.gestioneuffici.serializer.TimestampSerializer;
import it.interno.gestioneuffici.validation.ValidazioneAggiornamento;
import it.interno.gestioneuffici.validation.ValidazioneInserimento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ValidazioneUfficio(groups = {ValidazioneInserimento.class, ValidazioneAggiornamento.class})
@ValidazioneDateUfficio
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UfficioDto {

    @Size(max = 6, message = "Il codice ufficio deve avere massimo 6 cifre")
    private String codiceUfficio;

    @Size(max = 400, message = "La descrizione deve avere massimo 400 cifre")
    private String descrizioneUfficio;

    private Integer codiceRegione;

    private Integer codiceProvincia;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataInizio;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataFine;

    private Integer codiceComune;

    private String strada;

    private String utenteInserimento;

    @JsonSerialize(using = TimestampSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    private Timestamp dataInserimento;

    private String utenteAggiornamento;

    @JsonSerialize(using = TimestampSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    private Timestamp dataAggiornamento;

    private String utenteCancellazione;

    @JsonSerialize(using = TimestampSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    private Timestamp dataCancellazione;

    @Size(max = 25, message = "Il numero di telefono deve avere massimo 25 cifre")
    @Pattern(regexp = "^\\d+$", message = "Il numero di telefono può contenere solo numeri")
    private String numeroTelefono;

    private CategoriaUfficioDto categoriaUfficio;

    private LuogoDto luogoUfficio;

    @Size(max = 6, message = "L'ufficio inserimento deve avere massimo 6 cifre")
    private String ufficioInserimento;

    @Size(max = 6, message = "L'ufficio cancellazione deve avere massimo 6 cifre")
    private String ufficioCancellazione;

    private Character emissioneProvvedimento;

    private Character statistichePersonale;

    private Integer numeroGiorniControllo;

    private String indirizzoNormalizzato;

    private String capNormalizzato;

    private boolean coordinate;

    private Double coordinataX;

    private Double coordinataY;

    private String comandante;

    public UfficioDto(String codiceUfficio, String descrizioneUfficio) {
        this.codiceUfficio = codiceUfficio;
        this.descrizioneUfficio = descrizioneUfficio;
    }
}
