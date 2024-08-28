package it.interno.gestioneuffici.repository;

import it.interno.gestioneuffici.entity.GerarchiaSSD;
import it.interno.gestioneuffici.entity.pk.GerarchiaSSDPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface GerarchiaSSDRepository extends JpaRepository<GerarchiaSSD, GerarchiaSSDPK> {

    @Query("FROM GerarchiaSSD g " +
            "WHERE g.dataCancellazione IS NULL")
    List<GerarchiaSSD> getAll();

    @Query(value = "SELECT g.* " +
            "FROM SSD_SECURITY.SEC_GERARCHIA_SSD g INNER JOIN SSD_SECURITY.SEC_UFFICIO_LEVEL su ON g.COD_UFF_DIP = su.COD_UFF " +
            "WHERE g.DATA_CANC IS NULL " +
            "AND g.COD_UFF_PRINC = ?1", nativeQuery = true)
    List<GerarchiaSSD> getAllByUfficioParent(String codiceUfficioPrincipale);

    @Modifying
    @Query("UPDATE GerarchiaSSD g " +
            "SET g.utenteCancellazione = ?2, g.ufficioCancellazione = ?3, g.dataCancellazione = ?4 " +
            "WHERE g.codiceUfficioPrincipale = ?1 AND g.dataCancellazione IS NULL")
    void deleteAllByCodiceUfficio(String codiceUfficio, String utenteCancellazione, String ufficio, Timestamp dataCancellazione);

    @Modifying
    @Query(value = "INSERT INTO SSD_SECURITY.SEC_GERARCHIA_SSD (COD_UFF_PRINC, COD_UFF_DIP, DATA_INS, UTE_INS, UFF_INS, COD_UFF_STAFF) " +
            "VALUES (?1, ?2, ?6, ?4, ?5, ?3)", nativeQuery = true)
    void insert(String ufficioPrincipale, String ufficioDipendente, String codiceUfficioStaff, String utenteInserimento, String ufficioInserimento, Timestamp dataInserimento);

}
