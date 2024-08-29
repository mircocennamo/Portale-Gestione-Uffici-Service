package it.interno.gestioneuffici.repository;

import it.interno.gestioneuffici.dto.UfficioDto;
import it.interno.gestioneuffici.entity.Ufficio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UfficioRepository extends JpaRepository<Ufficio, String>, JpaSpecificationExecutor<Ufficio> {

    @Query("SELECT new it.interno.gestioneuffici.dto.UfficioDto(u.codiceUfficio, u.descrizioneUfficio) " +
            "FROM Ufficio u " +
            "WHERE u.dataCancellazione IS NULL")
    List<UfficioDto> getAllUfficiDescrizione();

    @Query(value = "SELECT * FROM SSD_SECURITY.SEC_UFFICIO_LEVEL sul " +
            "WHERE (?1 IS NULL OR sul.COD_UFF LIKE %?1% OR sul.UFFICIO LIKE %?1%) " +
            "AND (?2 IS NULL OR sul.CODICELUOGOUFFICIO = ?2) " +
            "AND (?3 IS NULL OR sul.FORZA_POLIZIA = ?3) " +
            "AND sul.DATA_CAN IS NULL",
            countQuery = "SELECT * FROM SSD_SECURITY.SEC_UFFICIO_LEVEL sul " +
                    "WHERE (?1 IS NULL OR sul.COD_UFF LIKE %?1% OR sul.UFFICIO LIKE %?1%) " +
                    "AND (?2 IS NULL OR sul.CODICELUOGOUFFICIO = ?2) " +
                    "AND (?3 IS NULL OR sul.FORZA_POLIZIA = ?3) " +
                    "AND sul.DATA_CAN IS NULL",
            nativeQuery = true)
    Page<Ufficio> getUfficiByFiltroPaginated(String parametroRicerca, String codiceLuogo, Integer codiceForzaPolizia, Pageable pageable);

    @Query(value = "SELECT * FROM SSD_SECURITY.SEC_UFFICIO_LEVEL sul " +
            "WHERE (?1 IS NULL OR sul.COD_UFF LIKE %?1% OR sul.UFFICIO LIKE %?1%) " +
            "AND (?2 IS NULL OR sul.CODICELUOGOUFFICIO = ?2) " +
            "AND (?3 IS NULL OR sul.FORZA_POLIZIA = ?3) " +
            "AND sul.COD_UFF IN (SELECT * FROM JSON_TABLE(SSD_SECURITY.GET_UFF_GER(?4),'$[*]' COLUMNS VAL VARCHAR PATH '$')) " +
            "AND sul.DATA_CAN IS NULL",
            countQuery = "SELECT * FROM SSD_SECURITY.SEC_UFFICIO_LEVEL sul " +
                    "WHERE (?1 IS NULL OR sul.COD_UFF LIKE %?1% OR sul.UFFICIO LIKE %?1%) " +
                    "AND (?2 IS NULL OR sul.CODICELUOGOUFFICIO = ?2) " +
                    "AND (?3 IS NULL OR sul.FORZA_POLIZIA = ?3) " +
                    "AND sul.COD_UFF IN (SELECT * FROM JSON_TABLE(SSD_SECURITY.GET_UFF_GER(?4),'$[*]' COLUMNS VAL VARCHAR PATH '$')) " +
                    "AND sul.DATA_CAN IS NULL",
            nativeQuery = true)
    Page<Ufficio> getUfficiDipendentiByFiltroPaginated(String parametroRicerca, String codiceLuogo, Integer codiceForzaPolizia, String ufficioOperatore, Pageable pageable);

    @Query(value = "SELECT * " +
            "FROM SSD_SECURITY.SEC_UFFICIO_LEVEL sul " +
            "WHERE sul.DATA_CAN IS NULL " +
            "AND sul.COD_UFF LIKE %?1% " +
            "AND sul.COD_UFF NOT IN ?3 " +
            "AND (?2 IS NULL OR (?2 != 1 AND FORZA_POLIZIA = ?2) OR (?2 = 1 AND FORZA_POLIZIA NOT IN (2, 3, 4))) " +
            "ORDER BY sul.COD_UFF ASC", nativeQuery = true)
    List<Ufficio> autocompleteByForzaPoliziaRuoloUfficioSicurezza(String parametroRicerca, Integer forzaPolizia, List<String> ufficiDaEscludere, String ufficioOperatore);


    @Query(value = "SELECT * " +
            "FROM SSD_SECURITY.SEC_UFFICIO_LEVEL sul " +
            "WHERE sul.DATA_CAN IS NULL " +
            "AND sul.COD_UFF LIKE %?1% " +
            "AND sul.COD_UFF NOT IN ?3 " +
            "AND (?2 IS NULL OR (?2 != 1 AND FORZA_POLIZIA = ?2) OR (?2 = 1 AND FORZA_POLIZIA NOT IN (2, 3, 4))) " +
            "AND sul.COD_UFF IN (SELECT * FROM JSON_TABLE(SSD_SECURITY.GET_UFF_GER(?4),'$[*]' COLUMNS VAL VARCHAR PATH '$'))" +
            "ORDER BY sul.COD_UFF ASC", nativeQuery = true)
    List<Ufficio> autocompleteByForzaPolizia(String parametroRicerca, Integer forzaPolizia, List<String> ufficiDaEscludere, String ufficioOperatore);




    @Query(value = "SELECT * " +
            "FROM SSD_SECURITY.SEC_UFFICIO_LEVEL sul " +
            "WHERE sul.DATA_CAN IS NULL " +
            "AND sul.COD_UFF LIKE %?1% " +
            "AND sul.COD_UFF NOT IN ?3 " +
            "AND sul.DATA_FINE > SYSDATE " +
            "AND (?2 IS NULL OR FORZA_POLIZIA = ?2 OR (?2 IN (1, 2, 3, 4) AND sul.FORZA_POLIZIA = 12)) " +
            "ORDER BY sul.COD_UFF ASC", nativeQuery = true)
    List<Ufficio> autocompleteByForzaPoliziaFree(String parametroRicerca, Integer forzaPolizia, List<String> ufficiDaEscludere);

    @Query(name = "query_autocomplete_enti_geografia", nativeQuery = true)
    List<UfficioDto> autocompleteByEntiAndGeografia(String parametroRicerca, List<String> ufficiDaEscludere, List<String> idForzePolizia, List<String> codiciGeografici, String ufficioOperatore, String ruoloOperatore);

    @Query("FROM Ufficio u " +
            "WHERE u.codiceUfficio = ?1 " +
            "AND u.dataCancellazione IS NULL")
    Ufficio findUfficioById(String idUfficio);

    @Query(value = "SELECT SSD_SECURITY.SET_PROGRESSIVO_UFFICIO(?1, ?2) FROM DUAL", nativeQuery = true)
    String getNewCodiceUfficio(String provincia, Integer codiceForzaDiPolizia);

    @Query(value = "SELECT COUNT(*) " +
            "FROM SSD_SECURITY.USERS_ORARIO_LAVORO uo " +
            "WHERE G_UFFICIO = ?1", nativeQuery = true)
    Integer getNumeroUtentiTotaliByUfficio(String codiceUfficio);

    @Query(value = "SELECT COUNT(*) " +
            "FROM SSD_SECURITY.USERS_ORARIO_LAVORO uo " +
            "WHERE G_UFFICIO = ?1 " +
            "AND uo.DATA_CAN IS NULL", nativeQuery = true)
    Integer getNumeroUtentiAttualiByUfficio(String codiceUfficio);

}
