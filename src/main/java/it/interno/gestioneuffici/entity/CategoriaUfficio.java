package it.interno.gestioneuffici.entity;

import it.interno.gestioneuffici.entity.pk.CategoriaUfficioPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "SEC_CATEGORIA_UFFICIO", schema = "SSD_SECURITY")
@NoArgsConstructor
@AllArgsConstructor
@Data
@IdClass(CategoriaUfficioPK.class)
public class CategoriaUfficio {

    @Id
    @Column(name = "COD_CATEG_UFF")
    private String codiceCategoriaUfficio;
    @Id
    @ManyToOne
    @JoinColumn(name = "GROUP_ID")
    private ForzaPolizia forzaPolizia;

    @Column(name = "DATA_INS")
    private Timestamp dataInserimento;
    @Column(name = "DATA_INIZIO")
    private LocalDate dataInizio;
    @Column(name = "DATA_FINE")
    private LocalDate dataFine;
    @Column(name = "DES_CATEG_UFF")
    private String descrizione;
    @Column(name = "UTE_INS")
    private String utenteInserimento;
    @Column(name = "DATA_CANC")
    private Timestamp dataCancellazione;
    @Column(name = "UTE_CANC")
    private String utenteCancellazione;

}
