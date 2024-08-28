package it.interno.gestioneuffici.service;

import it.interno.gestioneuffici.dto.GerarchiaTrasfCompDto;

import java.sql.Timestamp;
import java.util.List;

public interface GerarchiaTrasfCompService {

    List<GerarchiaTrasfCompDto> getAll();
    List<GerarchiaTrasfCompDto> getAllByUfficioParent(String codiceUfficioPrincipale);
    List<GerarchiaTrasfCompDto> salvataggio(List<GerarchiaTrasfCompDto> input, String utente, String ufficio, Timestamp data);
    void deleteAllByCodiceUfficio(String codiceUfficio, String utenteCancellazione, String ufficio, Timestamp dataCancellazione);
    Boolean ufficioAbilitatoConTrasfComp(String codiceUfficioPrincipale, String codiceUfficioDipendente) ;

}
