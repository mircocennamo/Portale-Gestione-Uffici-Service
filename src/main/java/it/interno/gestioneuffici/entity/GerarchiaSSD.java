package it.interno.gestioneuffici.entity;

import it.interno.gestioneuffici.entity.pk.GerarchiaSSDPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "SEC_GERARCHIA_SSD", schema = "SSD_SECURITY")
@NoArgsConstructor
@AllArgsConstructor
@Data
@IdClass(GerarchiaSSDPK.class)
public class GerarchiaSSD {

    @Id
    @Column(name = "COD_UFF_PRINC")
    private String codiceUfficioPrincipale;
    @Id
    @ManyToOne
    @JoinColumn(name = "COD_UFF_DIP")
    private Ufficio ufficioDipendente;
    @Id
    @Column(name = "DATA_INS")
    private Timestamp dataInserimento;
    @Column(name = "UTE_INS")
    private String utenteInserimento;
    @Column(name = "UFF_INS")
    private String ufficioInserimento;
    @Column(name = "DATA_CANC")
    private Timestamp dataCancellazione;
    @Column(name = "UTE_CANC")
    private String utenteCancellazione;
    @Column(name = "UFF_CANC")
    private String ufficioCancellazione;
    @Column(name = "COD_UFF_STAFF")
    private String codiceUfficioStaff;

    public GerarchiaSSD(String codiceUfficioPrincipale, Ufficio ufficioDipendente, Timestamp dataInserimento) {
        this.codiceUfficioPrincipale = codiceUfficioPrincipale;
        this.ufficioDipendente = ufficioDipendente;
        this.dataInserimento = dataInserimento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GerarchiaSSD that = (GerarchiaSSD) o;
        return getCodiceUfficioPrincipale().equals(that.getCodiceUfficioPrincipale()) && getUfficioDipendente().equals(that.getUfficioDipendente()) && !StringUtils.isBlank(getCodiceUfficioStaff()) && getCodiceUfficioStaff().equals(that.getCodiceUfficioStaff());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodiceUfficioPrincipale(), getUfficioDipendente(), getCodiceUfficioStaff());
    }
}
