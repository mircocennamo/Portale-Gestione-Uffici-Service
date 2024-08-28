package it.interno.gestioneuffici.service;

import it.interno.gestioneuffici.dto.UfficioDto;
import it.interno.gestioneuffici.dto.UfficioFilterDto;
import org.springframework.data.domain.Page;

import java.sql.Timestamp;
import java.util.List;

public interface UfficioService {

    List<UfficioDto> getAllUffici();
    UfficioDto getUfficioById(String idUfficio);
    List<UfficioDto> findAllByParametroAutocomplete(String parametroRicerca, Integer idForzaPolizia, List<String> codiciUfficioDaEscludere, String ufficioOperatore, String ruoloOperatore);
    List<UfficioDto> findAllByParametroAutocompleteFree(String parametroRicerca, Integer idForzaPolizia, List<String> codiciUfficioDaEscludere);
    List<UfficioDto> autocompleteByEntiAndGeografia(String parametroRicerca, List<String> idForzePolizia, List<String> codiciGeografici, List<String> codiciUfficioDaEscludere, String ufficioOperatore, String ruoloOperatore);
    Page<UfficioDto> searchAndPaginate(UfficioFilterDto filtro, String ufficioOperatore, String ruoloOperatore);
    UfficioDto insertNewUfficio(UfficioDto ufficio, String utenteInserimento, Timestamp data, String codiceUfficio);
    List<UfficioDto> insertNewUffici(List<UfficioDto> uffici, String utenteInserimento, Timestamp data, String codiceUfficio);
    UfficioDto updateUfficio(UfficioDto ufficio, String utenteAggiornamento, Timestamp data, String codiceUfficio);
    UfficioDto deleteUfficio(String idUfficio, String utenteCancellazione, Timestamp data, String codiceUfficio);

    String generazioneCodiceUfficio(String codiceProvincia, Integer idForzaDiPolizia);
}
