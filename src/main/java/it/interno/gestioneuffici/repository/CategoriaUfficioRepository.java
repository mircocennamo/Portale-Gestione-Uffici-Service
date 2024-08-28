package it.interno.gestioneuffici.repository;

import it.interno.gestioneuffici.entity.CategoriaUfficio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoriaUfficioRepository extends JpaRepository<CategoriaUfficio, Integer>, JpaSpecificationExecutor<CategoriaUfficio> {

    @Query("FROM CategoriaUfficio c " +
            "WHERE c.dataCancellazione IS NULL " +
            "ORDER BY c.forzaPolizia.idOrdinamento ASC")
    List<CategoriaUfficio> getAll();

    @Query("FROM CategoriaUfficio c WHERE c.dataCancellazione IS NULL " +
            "ANd c.codiceCategoriaUfficio = ?1 " +
            "AND c.forzaPolizia.idGruppo = ?2")
    CategoriaUfficio getById(String codiceCategoriaUfficio, Integer idForzaPolizia);

    @Query("FROM CategoriaUfficio c WHERE c.dataCancellazione IS NULL " +
            "AND c.forzaPolizia.idGruppo = ?1")
    List<CategoriaUfficio> getAllByForzaPolizia(Integer idForzaPolizia);

}
