package it.interno.gestioneuffici.service;

import it.interno.gestioneuffici.dto.GerarchiaUfficiFPDto;

import java.sql.Timestamp;
import java.util.List;

public interface GerarchiaUfficiFPService {

    List<GerarchiaUfficiFPDto> getAll();
    List<GerarchiaUfficiFPDto> getAllByUfficioParent(String codiceUfficioPrincipale);
    List<GerarchiaUfficiFPDto> salvataggio(List<GerarchiaUfficiFPDto> input, String utente, String ufficio, Timestamp data);
    void deleteAllByCodiceUfficio(String codiceUfficio, String utenteCancellazione, String ufficio, Timestamp dataCancellazione);
}
