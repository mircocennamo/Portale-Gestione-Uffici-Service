package it.interno.gestioneuffici.entity;

import it.interno.gestioneuffici.dto.UfficioDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "SEC_UFFICIO_LEVEL", schema = "SSD_SECURITY")
@NoArgsConstructor
@AllArgsConstructor
@Data
@DynamicUpdate
@NamedNativeQuery(name = "query_autocomplete_enti_geografia",
        query = "SELECT COD_UFF codiceUfficio, UFFICIO descrizioneUfficio " +
                "FROM SSD_SECURITY.SEC_UFFICIO_LEVEL sul " +
                "WHERE sul.DATA_CAN IS NULL " +
                "AND sul.COD_UFF LIKE :parametroRicerca " +
                "AND COD_UFF NOT IN :ufficiDaEscludere " +
                "AND TO_CHAR(FORZA_POLIZIA) IN :idForzePolizia " +
                "AND ( " +
                "   LPAD(COD_REGIONE,2,0) IN :codiciGeografici " +
                "   OR LPAD(COD_PROVINCIA,3,0) IN :codiciGeografici " +
                "   OR TO_CHAR(CODICELUOGOUFFICIO) IN :codiciGeografici " +
                ") " +
                "AND (:ruoloOperatore = 'R_UFFICIO_SICUREZZA' OR sul.COD_UFF IN (SELECT * FROM JSON_TABLE(SSD_SECURITY.GET_UFF_GER(:ufficioOperatore),'$[*]' COLUMNS VAL VARCHAR PATH '$'))) " +
                "ORDER BY sul.COD_UFF ASC", resultSetMapping = "mapping_codice_descrizione")
@SqlResultSetMapping(name = "mapping_codice_descrizione", classes = @ConstructorResult(targetClass = UfficioDto.class,
        columns = {@ColumnResult(name = "codiceUfficio"), @ColumnResult(name = "descrizioneUfficio")}))
public class Ufficio {

    @Id
    @Column(name = "COD_UFF")
    private String codiceUfficio;
    @Column(name = "UFFICIO")
    private String descrizioneUfficio;
    @Column(name = "COD_REGIONE")
    private Integer codiceRegione;
    @Column(name = "COD_PROVINCIA")
    private Integer codiceProvincia;
    @Column(name = "DATA_INIZIO")
    private LocalDate dataInizio;
    @Column(name = "DATA_FINE")
    private LocalDate dataFine;
    @Column(name = "COD_COMUNE")
    private Integer codiceComune;
    @Column(name = "STRADA")
    private String strada;
    @Column(name = "UTE_INS")
    private String utenteInserimento;
    @Column(name = "DATA_INS")
    private Timestamp dataInserimento;
    @Column(name = "UTE_AGG")
    private String utenteAggiornamento;
    @Column(name = "DATA_AGG")
    private Timestamp dataAggiornamento;
    @Column(name = "UTE_CAN")
    private String utenteCancellazione;
    @Column(name = "DATA_CAN")
    private Timestamp dataCancellazione;
    @Column(name = "NUMERO_TELEFONO")
    private String numeroTelefono;

    @ManyToOne
    @JoinColumn(name = "FORZA_POLIZIA", referencedColumnName = "GROUP_ID")
    @JoinColumn(name = "COD_CATEG_UFF", referencedColumnName = "COD_CATEG_UFF")
    private CategoriaUfficio categoriaUfficio;

    @ManyToOne
    @JoinColumn(name = "CODICELUOGOUFFICIO", referencedColumnName = "CODICELUOGO")
    private Luogo luogoUfficio;

    @Column(name = "UFF_INS")
    private String ufficioInserimento;
    @Column(name = "UFF_CAN")
    private String ufficioCancellazione;
    @Column(name = "FLGEMISSIONEPROVVEDIMENTO")
    private Character emissioneProvvedimento;
    @Column(name = "FLGNOSTATISTICHEPERSONALE")
    private Character statistichePersonale;
    @Column(name = "NR_GG_CONTROLLO")
    private Integer numeroGiorniControllo;
    @Column(name = "INDIRIZZONORMALIZZATO")
    private String indirizzoNormalizzato;
    @Column(name = "CAPNORMALIZZATO")
    private String capNormalizzato;
    @Column(name = "COORDINATAX")
    private Double coordinataX;
    @Column(name = "COORDINATAY")
    private Double coordinataY;

    public Ufficio(String codiceUfficio) {
        this.codiceUfficio = codiceUfficio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ufficio ufficio = (Ufficio) o;
        return getCodiceUfficio().equals(ufficio.getCodiceUfficio());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodiceUfficio());
    }
}
