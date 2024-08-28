package it.interno.gestioneuffici.repository;

import it.interno.gestioneuffici.entity.GerarchiaNormativa;
import it.interno.gestioneuffici.entity.pk.GerarchiaNormativaPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public interface GerarchiaNormativaRepository extends JpaRepository<GerarchiaNormativa, GerarchiaNormativaPK> {

    @Query("FROM GerarchiaNormativa g join g.ufficioDipendente u " +
            "WHERE g.dataCancellazione IS NULL " +
            "AND u.dataCancellazione IS NULL " +
            "AND u.dataFine > ?1")
    List<GerarchiaNormativa> getAll(LocalDate dataOdierna);

    @Query("FROM GerarchiaNormativa g join g.ufficioDipendente u " +
            "WHERE g.dataCancellazione IS NULL " +
            "AND g.codiceUfficioPrincipale = ?1 " +
            "AND u.dataCancellazione IS NULL " +
            "AND u.dataFine > ?2")
    List<GerarchiaNormativa> getAllByUfficioParent(String codiceUfficioPrincipale, LocalDate dataOdierna);

    @Modifying
    @Query("UPDATE GerarchiaNormativa g " +
            "SET g.utenteCancellazione = ?2, g.ufficioCancellazione = ?3, g.dataCancellazione = ?4 " +
            "WHERE g.codiceUfficioPrincipale = ?1 AND g.dataCancellazione IS NULL")
    void deleteAllByCodiceUfficio(String codiceUfficio, String utenteCancellazione, String ufficio, Timestamp dataCancellazione);

    @Modifying
    @Query(value = "INSERT INTO SSD_SECURITY.SEC_GERARCHIA_NORMATIVA (COD_UFF_PRINC, COD_UFF_DIP, DATA_INS, UTE_INS, UFF_INS) " +
            "VALUES (?1, ?2, ?5, ?3, ?4)", nativeQuery = true)
    void insert(String ufficioPrincipale, String ufficioDipendente, String utenteInserimento, String ufficioInserimento, Timestamp dataInserimento);

}
