package it.interno.gestioneuffici.entity.pk;

import it.interno.gestioneuffici.entity.ForzaPolizia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoriaUfficioPK implements Serializable {
    private String codiceCategoriaUfficio;
    private ForzaPolizia forzaPolizia;
}
