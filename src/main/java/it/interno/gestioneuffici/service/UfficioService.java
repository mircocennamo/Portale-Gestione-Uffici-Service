package it.interno.gestioneuffici.service;

import it.interno.gestioneuffici.dto.PaginazioneDto;
import it.interno.gestioneuffici.dto.UfficioDto;
import it.interno.gestioneuffici.dto.UfficioFilterDto;
import org.springframework.data.domain.Page;

import java.sql.Timestamp;
import java.util.List;

public interface UfficioService {

    List<UfficioDto> getAllUffici();
    UfficioDto getUfficioById(String idUfficio);
    Page<UfficioDto> getUfficioByProvinciaAndForzaPoliziaAndCategoria(String parametroRicerca, List<String> siglaProvincia, List<Integer> idForzaPolizia, List<String> codiceCategoria, PaginazioneDto paginazione);
    List<UfficioDto> findAllByParametroAutocomplete(String parametroRicerca, Integer idForzaPolizia, List<String> codiciUfficioDaEscludere);
    Page<UfficioDto> searchAndPaginate(UfficioFilterDto filtro, String ufficioOperatore);
    UfficioDto insertNewUfficio(UfficioDto ufficio, String utenteInserimento, Timestamp data, String codiceUfficio);
    List<UfficioDto> insertNewUffici(List<UfficioDto> uffici, String utenteInserimento, Timestamp data, String codiceUfficio);
    UfficioDto updateUfficio(UfficioDto ufficio, String utenteAggiornamento, Timestamp data, String codiceUfficio);
    UfficioDto deleteUfficio(String idUfficio, String utenteCancellazione, Timestamp data, String codiceUfficio);

    String generazioneCodiceUfficio(String codiceProvincia, Integer idForzaDiPolizia);
}
