package it.interno.gestioneuffici.service;

import it.interno.gestioneuffici.dto.AlberoGerarchicoDto;
import it.interno.gestioneuffici.dto.GerarchiaNormativaDto;

import java.sql.Timestamp;
import java.util.List;

public interface GerarchiaNormativaService {

    List<GerarchiaNormativaDto> getAll();
    List<GerarchiaNormativaDto> getAllByUfficioParent(String codiceUfficioPrincipale);
    List<GerarchiaNormativaDto> salvataggio(List<GerarchiaNormativaDto> input, String utente, String ufficio, Timestamp data);
    void deleteAllByCodiceUfficio(String codiceUfficio, String utenteCancellazione, String ufficio, Timestamp dataCancellazione);

    AlberoGerarchicoDto getAlberoGerarchico(String codiceUfficio);
}
