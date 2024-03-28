package it.interno.gestioneuffici.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoriaUfficioDto {

    private String codiceCategoriaUfficio;
    private ForzaPoliziaDto forzaPolizia;

    private Timestamp dataInserimento;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private String descrizione;
    private String utenteInserimento;
    private Timestamp dataCancellazione;
    private String utenteCancellazione;

}
