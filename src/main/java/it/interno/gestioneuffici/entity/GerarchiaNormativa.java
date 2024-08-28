package it.interno.gestioneuffici.entity;

import it.interno.gestioneuffici.entity.pk.GerarchiaNormativaPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "SEC_GERARCHIA_NORMATIVA", schema = "SSD_SECURITY")
@NoArgsConstructor
@AllArgsConstructor
@Data
@IdClass(GerarchiaNormativaPK.class)
public class GerarchiaNormativa {

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

    public GerarchiaNormativa(String codiceUfficioPrincipale, Ufficio ufficioDipendente, Timestamp dataInserimento) {
        this.codiceUfficioPrincipale = codiceUfficioPrincipale;
        this.ufficioDipendente = ufficioDipendente;
        this.dataInserimento = dataInserimento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GerarchiaNormativa that = (GerarchiaNormativa) o;
        return getCodiceUfficioPrincipale().equals(that.getCodiceUfficioPrincipale()) && getUfficioDipendente().equals(that.getUfficioDipendente());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodiceUfficioPrincipale(), getUfficioDipendente());
    }
}
