package it.interno.gestioneuffici.entity.pk;

import it.interno.gestioneuffici.entity.Ufficio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GerarchiaUfficiFPPK implements Serializable {

    private String codiceUfficioPrincipale;
    private Ufficio ufficioDipendente;
    private Timestamp dataInserimento;

}
