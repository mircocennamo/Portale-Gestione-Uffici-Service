package it.interno.gestioneuffici.service;

import it.interno.gestioneuffici.dto.GerarchiaTrasfCompDto;
import it.interno.gestioneuffici.entity.GerarchiaTrasfComp;
import it.interno.gestioneuffici.exception.ListEmptyException;
import it.interno.gestioneuffici.mapper.GerarchiaTrasfCompMapper;
import it.interno.gestioneuffici.repository.GerarchiaTrasfCompRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GerarchiaTrasfCompServiceImpl implements GerarchiaTrasfCompService{

    @Autowired
    private GerarchiaTrasfCompRepository gerarchiaTrasfCompRepository;
    @Autowired
    private GerarchiaTrasfCompMapper gerarchiaTrasfCompMapper;

    @Override
    public List<GerarchiaTrasfCompDto> getAll() {
        return gerarchiaTrasfCompRepository.getAll()
                .stream().map(el -> gerarchiaTrasfCompMapper.toDto(el))
                .collect(Collectors.toList());
    }

    @Override
    public List<GerarchiaTrasfCompDto> getAllByUfficioParent(String codiceUfficioPrincipale) {
        return gerarchiaTrasfCompRepository.getAllByUfficioParent(codiceUfficioPrincipale)
                .stream().map(el -> gerarchiaTrasfCompMapper.toDto(el))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<GerarchiaTrasfCompDto> salvataggio(List<GerarchiaTrasfCompDto> input, String utente, String ufficio, Timestamp data) {

        if(input == null || input.isEmpty())
            throw new ListEmptyException();

        List<GerarchiaTrasfComp> inputEntity = input.stream().map(el -> gerarchiaTrasfCompMapper.toEntity(el))
                .collect(Collectors.toList());

        List<GerarchiaTrasfComp> sulDB = gerarchiaTrasfCompRepository.getAllByUfficioParent(input.get(0).getCodiceUfficioPrincipale());

        List<GerarchiaTrasfComp> daInserire = new ArrayList<>(inputEntity);
        daInserire.removeAll(sulDB);

        List<GerarchiaTrasfComp> daEliminare = new ArrayList<>(sulDB);
        daEliminare.removeAll(inputEntity);

        this.inserimento(daInserire, utente, ufficio, data);
        this.cancellazione(daEliminare, utente, ufficio, data);

        return this.getAllByUfficioParent(input.get(0).getCodiceUfficioPrincipale());
    }

    @Override
    @Transactional
    public void deleteAllByCodiceUfficio(String codiceUfficio, String utenteCancellazione, String ufficio, Timestamp dataCancellazione) {
        gerarchiaTrasfCompRepository.deleteAllByCodiceUfficio(codiceUfficio, utenteCancellazione, ufficio, dataCancellazione);
    }

    // Metodo per l'inserimento
    private List<GerarchiaTrasfComp> inserimento(List<GerarchiaTrasfComp> daInserire, String utente, String ufficio, Timestamp data){

        daInserire = daInserire.stream().map(el -> {
            el.setDataInserimento(data);
            el.setUtenteInserimento(utente);
            el.setUfficioInserimento(ufficio);
            return el;
        }).collect(Collectors.toList());

        return gerarchiaTrasfCompRepository.saveAll(daInserire);
    }

    // Metodo per la cancellazione
    private void cancellazione(List<GerarchiaTrasfComp> daEliminare, String utente, String ufficio, Timestamp data){

        daEliminare = daEliminare.stream().map(el -> {
            el.setDataCancellazione(data);
            el.setUtenteCancellazione(utente);
            el.setUfficioCancellazione(ufficio);
            return el;
        }).collect(Collectors.toList());

        gerarchiaTrasfCompRepository.saveAll(daEliminare);
    }

    @Override
    public Boolean ufficioAbilitatoConTrasfComp(String codiceUfficioPrincipale, String codiceUfficioDipendente) {
        return gerarchiaTrasfCompRepository.ufficioAbilitatoConTrasfComp(
                codiceUfficioPrincipale, codiceUfficioDipendente).isPresent();
    }

}
