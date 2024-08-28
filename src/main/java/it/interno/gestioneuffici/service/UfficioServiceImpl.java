package it.interno.gestioneuffici.service;

import it.interno.gestioneuffici.dto.UfficioDto;
import it.interno.gestioneuffici.dto.UfficioFilterDto;
import it.interno.gestioneuffici.entity.ComandanteUffici;
import it.interno.gestioneuffici.entity.Luogo;
import it.interno.gestioneuffici.entity.Ufficio;
import it.interno.gestioneuffici.exception.ComandanteObbligatorioException;
import it.interno.gestioneuffici.exception.GenerazioneCodiceFallitaException;
import it.interno.gestioneuffici.exception.UfficioNotDeletableException;
import it.interno.gestioneuffici.exception.UfficioNotFoundException;
import it.interno.gestioneuffici.mapper.UfficioMapper;
import it.interno.gestioneuffici.repository.ComandanteUfficioRepository;
import it.interno.gestioneuffici.repository.ForzaPoliziaRepository;
import it.interno.gestioneuffici.repository.LuogoRepository;
import it.interno.gestioneuffici.repository.UfficioRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UfficioServiceImpl implements UfficioService{

    @Autowired
    private UfficioRepository ufficioRepository;
    @Autowired
    private UfficioMapper ufficioMapper;
    @Autowired
    private ForzaPoliziaRepository forzaPoliziaRepository;
    @Autowired
    private ComandanteUfficioRepository comandanteUfficioRepository;
    @Autowired
    private LuogoRepository luogoRepository;

    @Override
    public List<UfficioDto> getAllUffici() {
        return ufficioRepository.getAllUfficiDescrizione();
    }

    @Override
    public UfficioDto getUfficioById(String idUfficio) {

        UfficioDto ufficio = ufficioMapper.toDto(ufficioRepository.findUfficioById(idUfficio), comandanteUfficioRepository);

        if(ufficio == null)
            throw new UfficioNotFoundException("L'ufficio non è presente in base dati");

        return ufficio;
    }

    @Override
    public List<UfficioDto> findAllByParametroAutocomplete(String parametroRicerca, Integer idForzaPolizia, List<String> codiciUfficioDaEscludere, String ufficioOperatore, String ruoloOperatore) {

        if(codiciUfficioDaEscludere.isEmpty())
            codiciUfficioDaEscludere.add(" ");

        List<Ufficio> uffici = ufficioRepository.autocompleteByForzaPolizia(
                parametroRicerca,
                idForzaPolizia,
                codiciUfficioDaEscludere,
                ufficioOperatore,
                ruoloOperatore);

        return uffici.stream().map(el -> ufficioMapper.toDto(el, comandanteUfficioRepository)).toList();
    }

    @Override
    public List<UfficioDto> findAllByParametroAutocompleteFree(String parametroRicerca, Integer idForzaPolizia, List<String> codiciUfficioDaEscludere) {

        if(codiciUfficioDaEscludere.isEmpty())
            codiciUfficioDaEscludere.add(" ");

        List<Ufficio> uffici = ufficioRepository.autocompleteByForzaPoliziaFree(
                parametroRicerca,
                idForzaPolizia,
                codiciUfficioDaEscludere);

        return uffici.stream().map(el -> ufficioMapper.toDto(el, comandanteUfficioRepository)).toList();
    }

    @Override
    public List<UfficioDto> autocompleteByEntiAndGeografia(String parametroRicerca, List<String> idForzePolizia, List<String> codiciGeografici, List<String> codiciUfficioDaEscludere, String ufficioOperatore, String ruoloOperatore) {

        if(codiciUfficioDaEscludere.isEmpty())
            codiciUfficioDaEscludere.add(" ");
        // SE NON SONO SPECIFICATI FILTRI SUGLI ENTI PRENDO TUTTI GLI ENTI
        if(idForzePolizia == null || idForzePolizia.isEmpty())
            idForzePolizia = forzaPoliziaRepository.findAllForzePolizia().stream().map(el -> el.getIdGruppo().toString()).toList();
        // SE NON SONO SPECIFICATI FILTRI GEOGRAFICI PRENDO TUTTE LE PROVINCE
        if(codiciGeografici == null || codiciGeografici.isEmpty())
            codiciGeografici = luogoRepository.getByDescrizioneInLuogoAndFiltroGeografico("", "02", LocalDate.now()).stream().map(Luogo::getCodiceRegione).toList();

        return ufficioRepository.autocompleteByEntiAndGeografia(
                StringUtils.isBlank(parametroRicerca) ? "%" : parametroRicerca + "%",
                codiciUfficioDaEscludere,
                idForzePolizia,
                codiciGeografici,
                ufficioOperatore,
                ruoloOperatore);
    }

    @Override
    public Page<UfficioDto> searchAndPaginate(UfficioFilterDto filtro, String ufficioOperatore, String ruoloOperatore) {

        // Mi creo le specifiche di paginazione
        Pageable pageable = PageRequest.of(
                filtro.getPagina(),
                filtro.getNumeroElementi(),
                Sort.by(new Sort.Order(Sort.Direction.fromString(filtro.getSortDirection()), filtro.getSortBy()).ignoreCase())
        );

        Page<Ufficio> pages;

        if(ruoloOperatore.equalsIgnoreCase("R_UFFICIO_SICUREZZA"))
            pages = ufficioRepository.getUfficiByFiltroPaginated(
                StringUtils.isBlank(filtro.getParametroRicerca()) ? "" : filtro.getParametroRicerca(),
                filtro.getLuogo(), filtro.getForzaPolizia(), pageable);
        else
            pages = ufficioRepository.getUfficiDipendentiByFiltroPaginated(
                    StringUtils.isBlank(filtro.getParametroRicerca()) ? "" : filtro.getParametroRicerca(),
                    filtro.getLuogo(), filtro.getForzaPolizia(), ufficioOperatore, pageable);

        // Converto la lista in dto
        List<UfficioDto> dtos = pages.stream()
                .map(el -> ufficioMapper.toDto(el, comandanteUfficioRepository))
                .toList();

        return new PageImpl<>(dtos, pageable, pages.getTotalElements());
    }

    @Override
    @Transactional
    public UfficioDto insertNewUfficio(UfficioDto ufficio, String utenteInserimento, Timestamp data, String codiceUfficio) {

        // Imposto le proprietà necessarie
        ufficio.setUtenteInserimento(utenteInserimento);
        ufficio.setDataInserimento(data);
        ufficio.setUfficioInserimento(codiceUfficio);

        // Calcolo codici geografici
        Luogo luogo = luogoRepository.getById(ufficio.getLuogoUfficio().getCodiceLuogo(), data.toLocalDateTime().toLocalDate());
        ufficio.setCodiceComune(Integer.parseInt(luogo.getCodiceComune()));
        ufficio.setCodiceProvincia(Integer.parseInt(luogo.getCodiceProvincia()));
        ufficio.setCodiceRegione(Integer.parseInt(luogo.getCodiceRegione()));

        if(ufficio.getDataFine() == null)
            ufficio.setDataFine(LocalDate.of(9999, 12, 31));

        // Salvataggio
        Ufficio nuovoUfficio = ufficioRepository.save(ufficioMapper.toEntity(ufficio));

        // Inserimento comandante
        if(!StringUtils.isBlank(ufficio.getComandante())){
            ComandanteUffici comandante = new ComandanteUffici(
                ufficio.getComandante(),
                ufficio.getCodiceUfficio(),
                data,
                utenteInserimento,
                codiceUfficio,
                null,
                null,
                null
            );

            comandanteUfficioRepository.save(comandante);
        }

        return ufficioMapper.toDto(nuovoUfficio, comandanteUfficioRepository);
    }

    @Override
    @Transactional
    public List<UfficioDto> insertNewUffici(List<UfficioDto> uffici, String utenteInserimento, Timestamp data, String codiceUfficio) {

        List<UfficioDto> result = new ArrayList<>();
        uffici.forEach(el -> result.add(this.insertNewUfficio(el, utenteInserimento, data, codiceUfficio)));

        return result;
    }

    @Override
    @Transactional
    public UfficioDto updateUfficio(UfficioDto ufficio, String utenteAggiornamento, Timestamp data, String codiceUfficio) {

        // Imposto le proprietà necessarie
        ufficio.setUtenteAggiornamento(utenteAggiornamento);
        ufficio.setDataAggiornamento(data);

        // Calcolo codici geografici
        Luogo luogo = luogoRepository.getById(ufficio.getLuogoUfficio().getCodiceLuogo(), data.toLocalDateTime().toLocalDate());
        ufficio.setCodiceComune(Integer.parseInt(luogo.getCodiceComune()));
        ufficio.setCodiceProvincia(Integer.parseInt(luogo.getCodiceProvincia()));
        ufficio.setCodiceRegione(Integer.parseInt(luogo.getCodiceRegione()));

        // Salvataggio
        Ufficio ufficioAggiornato = ufficioRepository.save(ufficioMapper.toEntity(ufficio));

        ComandanteUffici comandante = comandanteUfficioRepository.getByUfficio(ufficioAggiornato.getCodiceUfficio());
        Integer numeroUtentiUfficio = ufficioRepository.getNumeroUtentiAttualiByUfficio(ufficioAggiornato.getCodiceUfficio());
        if(comandante != null && StringUtils.isBlank(ufficio.getComandante()) && numeroUtentiUfficio > 0)
            // Rimozione comandante non ammessa
            throw new ComandanteObbligatorioException("Non è possibile rimuovere il comandante, ci sono ancora utenti assegnati all'ufficio.");
        else if(comandante != null && StringUtils.isBlank(ufficio.getComandante()) && numeroUtentiUfficio == 0){
            // Rimozione comandante ammessa
            // Cancellazione vecchio comandante
            comandante.setUtenteCancellazione(utenteAggiornamento);
            comandante.setUfficioCancellazione(codiceUfficio);
            comandante.setDataCancellazione(data);
            comandanteUfficioRepository.save(comandante);
        }else if(!StringUtils.isBlank(ufficio.getComandante()) && comandante == null){
            // Nuovo inserimento classico
            comandanteUfficioRepository.save(new ComandanteUffici(
                    ufficio.getComandante(),
                    ufficio.getCodiceUfficio(),
                    data,
                    utenteAggiornamento,
                    codiceUfficio,
                    null, null, null
            ));
        }else if(!StringUtils.isBlank(ufficio.getComandante()) && comandante != null && !ufficio.getComandante().equalsIgnoreCase(comandante.getUtenteComandante())){
            // Cancellazione vecchio comandante
            comandante.setUtenteCancellazione(utenteAggiornamento);
            comandante.setUfficioCancellazione(codiceUfficio);
            comandante.setDataCancellazione(data);
            comandanteUfficioRepository.save(comandante);
            // Inserimento nuovo comandante
            comandanteUfficioRepository.save(new ComandanteUffici(
                    ufficio.getComandante(),
                    ufficio.getCodiceUfficio(),
                    data,
                    utenteAggiornamento,
                    codiceUfficio,
                    null, null, null
            ));
        }

        return ufficioMapper.toDto(ufficioAggiornato, comandanteUfficioRepository);
    }

    @Override
    @Transactional
    public UfficioDto deleteUfficio(String idUfficio, String utenteCancellazione, Timestamp data, String codiceUfficio) {

        // Cerco l'ufficio da eliminare
        Ufficio ufficioDaEliminare = ufficioRepository.findUfficioById(idUfficio);

        if(ufficioDaEliminare == null)
            throw new UfficioNotFoundException("L'ufficio da eliminare non è stato trovato");

        if(ufficioRepository.getNumeroUtentiTotaliByUfficio(idUfficio) > 0)
            throw new UfficioNotDeletableException("L'ufficio ha o ha avuto utenti assegnati, non si può eliminare");

        // Imposto le proprietà necessarie
        ufficioDaEliminare.setUtenteCancellazione(utenteCancellazione);
        ufficioDaEliminare.setDataCancellazione(data);
        ufficioDaEliminare.setUfficioCancellazione(codiceUfficio);

        return ufficioMapper.toDto(ufficioRepository.save(ufficioDaEliminare), comandanteUfficioRepository);
    }

    @Override
    @Transactional
    public String generazioneCodiceUfficio(String codiceProvincia, Integer idForzaDiPolizia) {

        String codiceGenerato = ufficioRepository.getNewCodiceUfficio(codiceProvincia.toUpperCase(), idForzaDiPolizia);

        if(codiceGenerato == null)
            throw new GenerazioneCodiceFallitaException("Si è verificato un problema nella generazione del codice ufficio");

        return codiceGenerato;
    }
}
