package it.interno.gestioneuffici.repository.specification;

import it.interno.gestioneuffici.entity.CategoriaUfficio;
import it.interno.gestioneuffici.entity.ForzaPolizia;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import java.util.List;

public interface CategoriaUfficioSpecification {

    static Specification<CategoriaUfficio> forzaPoliziaIn(List<Integer> idForzePolizia){
        return (root, query, cb) -> {

            Join<CategoriaUfficio, ForzaPolizia> joinForzaPolizia = root.join("forzaPolizia", JoinType.INNER);

            return joinForzaPolizia.get("idGruppo").in(idForzePolizia);
        };
    }

    static Specification<CategoriaUfficio> dataCancellazioneIsNull(){
        return (root, query, cb) -> cb.isNull(root.get("dataCancellazione"));
    }

    static Specification<CategoriaUfficio> descrizioneLike(String parametro) {
        return (root, query, cb) ->  cb.like(
                cb.upper(root.get("descrizione")), parametro.toUpperCase() + "%"
        );
    }
}
