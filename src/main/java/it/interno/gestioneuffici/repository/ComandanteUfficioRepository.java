package it.interno.gestioneuffici.repository;

import it.interno.gestioneuffici.entity.ComandanteUffici;
import it.interno.gestioneuffici.entity.pk.ComandanteUfficiPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ComandanteUfficioRepository extends JpaRepository<ComandanteUffici, ComandanteUfficiPK> {

    @Query("SELECT c.utenteComandante FROM ComandanteUffici c WHERE c.ufficio = ?1 AND c.dataCancellazione IS NULL")
    String getComandanteByUfficio(String codiceUfficio);

    @Query("FROM ComandanteUffici c WHERE c.ufficio = ?1 AND c.dataCancellazione IS NULL")
    ComandanteUffici getByUfficio(String codiceUfficio);
}
