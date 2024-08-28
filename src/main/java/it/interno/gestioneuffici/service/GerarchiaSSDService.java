package it.interno.gestioneuffici.service;

import it.interno.gestioneuffici.dto.GerarchiaSSDDto;

import java.sql.Timestamp;
import java.util.List;

public interface GerarchiaSSDService {

    List<GerarchiaSSDDto> getAll();
    List<GerarchiaSSDDto> getAllByUfficioParent(String codiceUfficioPrincipale);
    List<GerarchiaSSDDto> salvataggio(List<GerarchiaSSDDto> input, String utente, String ufficio, Timestamp data);
    void deleteAllByCodiceUfficio(String codiceUfficio, String utenteCancellazione, String ufficio, Timestamp dataCancellazione);
}
