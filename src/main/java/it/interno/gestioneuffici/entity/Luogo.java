package it.interno.gestioneuffici.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "TLUOGO", schema = "SSD_SECURITY")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Luogo {

    @Id
    @Column(name = "CODICELUOGO")
    public Integer codiceLuogo;
    @Column(name = "DATAINIZIOVALIDITA")
    public LocalDate dataInizioValidita;
    @Column(name = "INLUOGO")
    public String inLuogo;
    @Column(name = "DESCRIZIONELUOGO")
    public String descrizione;
    @Column(name = "DATAFINEVALIDITA")
    public LocalDate dataFineValidita;
    @Column(name = "CODICEREGIONE")
    public String codiceRegione;
    @Column(name = "CODICEPROVINCIA")
    public String codiceProvincia;
    @Column(name = "CODICECOMUNE")
    public String codiceComune;
    @Column(name = "CODICECATASTALE")
    public String codiceCatastale;
    @Column(name = "SIGLAPROVINCIA")
    public String siglaProvincia;
    @Column(name = "TSCANCELLAZIONE")
    public Timestamp dataCancellazione;

}
