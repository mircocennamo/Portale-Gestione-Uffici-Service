package it.interno.gestioneuffici.repository;

import it.interno.gestioneuffici.entity.Luogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface LuogoRepository extends JpaRepository<Luogo, String> {

    @Query("FROM Luogo l " +
            "WHERE TRIM(l.descrizione) LIKE ?1% " +
            "AND l.inLuogo = ?2 " +
            "ORDER BY l.descrizione ASC")
    List<Luogo> findAllByDescrizioneLike(String parametro, String inLuogo);

    @Query("FROM Luogo l " +
            "WHERE TRIM(l.descrizione) LIKE ?1% " +
            "AND l.inLuogo = ?2 " +
            "AND l.siglaProvincia = ?3")
    List<Luogo> findAllByDescrizioneLikeAndProvincia(String parametro, String inLuogo, String provincia);

    @Query("FROM Luogo l " +
            "WHERE l.codiceLuogo = ?1 " +
            "AND ?2 >= l.dataInizioValidita " +
            "AND (?2 <= l.dataFineValidita OR l.dataFineValidita IS NULL) ")
    Luogo getById(Integer codiceLuogo, LocalDate dataRif);

    @Query(value = "SELECT * FROM SSD_SECURITY.TLUOGO " +
            "WHERE " +
            "DATAINIZIOVALIDITA <= ?3 " +
            "AND (DATAFINEVALIDITA IS NULL OR DATAFINEVALIDITA >= ?3) " +
            "AND DESCRIZIONELUOGO LIKE ?1% " +
            "AND INLUOGO = ?2 " +
            "ORDER BY DESCRIZIONELUOGO ASC", nativeQuery = true)
    List<Luogo> getByDescrizioneInLuogoAndFiltroGeografico(String descrizioneLuogo, String inLuogo, LocalDate dataRif);
}
