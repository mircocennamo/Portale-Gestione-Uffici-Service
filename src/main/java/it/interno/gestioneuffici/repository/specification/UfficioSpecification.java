package it.interno.gestioneuffici.repository.specification;

import it.interno.gestioneuffici.entity.CategoriaUfficio;
import it.interno.gestioneuffici.entity.ForzaPolizia;
import it.interno.gestioneuffici.entity.Luogo;
import it.interno.gestioneuffici.entity.Ufficio;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import java.util.List;

public interface UfficioSpecification {

    static Specification<Ufficio> codiceUfficioLike(String parametro) {
        return (root, query, cb) ->  cb.like(
                cb.upper(root.get("codiceUfficio")), "%" + parametro.toUpperCase() + "%"
        );
    }

    static Specification<Ufficio> parametroRicercaLike(String parametro) {
        return (root, query, cb) ->
                cb.or(
                        cb.like(cb.upper(root.get("codiceUfficio")), "%" + parametro.toUpperCase() + "%"),
                        cb.like(cb.upper(root.get("descrizioneUfficio")), "%" + parametro.toUpperCase() + "%")
                );
    }

    static Specification<Ufficio> forzaPoliziaEqual(Integer idForzaPolizia) {
        return (root, query, cb) -> {

            Join<Ufficio, CategoriaUfficio> joinCategoria = root.join("categoriaUfficio", JoinType.INNER);
            Join<CategoriaUfficio, ForzaPolizia> joinForzaPolizia = joinCategoria.join("forzaPolizia", JoinType.INNER);

            return cb.equal(joinForzaPolizia.get("idGruppo"), idForzaPolizia);
        };
    }

    static Specification<Ufficio> ufficiDaEscludere(List<String> listaUfficiDaEscludere) {
        return (root, query, cb) ->  cb.not(
                root.get("codiceUfficio").in(listaUfficiDaEscludere)
        );
    }

    static Specification<Ufficio> dataCancellazioneIsNull(){
        return (root, query, cb) -> cb.isNull(root.get("dataCancellazione"));
    }

    static Specification<Ufficio> luogoIs(String idLuogo){
        return (root, query, cb) -> {

            Join<Ufficio, Luogo> joinLuogo = root.join("luogoUfficio", JoinType.INNER);
            return cb.equal(joinLuogo.get("codiceLuogo"), idLuogo);
        };
    }

    static Specification<Ufficio> abilitatoEmissioni(char abilitatoEmissioni){
        return (root, query, cb) -> cb.equal(root.get("emissioneProvvedimento"), abilitatoEmissioni);
    }

    static Specification<Ufficio> statistichePersonale(char statistichePersonale){
        return (root, query, cb) -> cb.equal(root.get("statistichePersonale"), statistichePersonale);
    }

    static Specification<Ufficio> forzaPoliziaIn(List<Integer> idForzePolizia){
        return (root, query, cb) -> {

            Join<Ufficio, CategoriaUfficio> joinCategoria = root.join("categoriaUfficio", JoinType.INNER);
            Join<CategoriaUfficio, ForzaPolizia> joinForzaPolizia = joinCategoria.join("forzaPolizia", JoinType.INNER);

            return joinForzaPolizia.get("idGruppo").in(idForzePolizia);
        };
    }

    static Specification<Ufficio> siglaProvinciaIn(List<String> sigleProvincia){
        return (root, query, cb) -> {

            Join<Ufficio, Luogo> joinLuogo = root.join("luogoUfficio", JoinType.INNER);

            return joinLuogo.get("siglaProvincia").in(sigleProvincia);
        };
    }

    static Specification<Ufficio> categoriaUfficioIn(List<String> categorieUfficio){
        return (root, query, cb) -> {

            Join<Ufficio, CategoriaUfficio> joinCategoria = root.join("categoriaUfficio", JoinType.INNER);

            return cb.and(
                    joinCategoria.get("codiceCategoriaUfficio").in(categorieUfficio)
            );
        };
    }
}
