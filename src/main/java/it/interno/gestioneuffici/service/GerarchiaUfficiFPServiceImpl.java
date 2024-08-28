package it.interno.gestioneuffici.service;

import it.interno.gestioneuffici.dto.GerarchiaUfficiFPDto;
import it.interno.gestioneuffici.entity.GerarchiaUfficiFP;
import it.interno.gestioneuffici.exception.ListEmptyException;
import it.interno.gestioneuffici.mapper.GerarchiaUfficiFPMapper;
import it.interno.gestioneuffici.repository.GerarchiaUfficiFPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class GerarchiaUfficiFPServiceImpl implements GerarchiaUfficiFPService{

    @Autowired
    private GerarchiaUfficiFPRepository gerarchiaUfficiFPRepository;
    @Autowired
    private GerarchiaUfficiFPMapper gerarchiaUfficiFPMapper;

    @Override
    @Transactional
    public List<GerarchiaUfficiFPDto> getAll() {
        return gerarchiaUfficiFPRepository.getAll()
                .stream().map(el -> gerarchiaUfficiFPMapper.toDto(el))
                .toList();
    }

    @Override
    @Transactional
    public List<GerarchiaUfficiFPDto> getAllByUfficioParent(String codiceUfficioPrincipale) {
        return gerarchiaUfficiFPRepository.getAllByUfficioParent(codiceUfficioPrincipale)
                .stream().map(el -> gerarchiaUfficiFPMapper.toDto(el))
                .toList();
    }

    @Override
    @Transactional
    public List<GerarchiaUfficiFPDto> salvataggio(List<GerarchiaUfficiFPDto> input, String utente, String ufficio, Timestamp data) {

        if(input == null || input.isEmpty())
            throw new ListEmptyException();

        List<GerarchiaUfficiFP> inputEntity = input.stream().map(el -> gerarchiaUfficiFPMapper.toEntity(el)).toList();
        List<GerarchiaUfficiFP> sulDB = gerarchiaUfficiFPRepository.getAllByUfficioParent(input.get(0).getCodiceUfficioPrincipale());

        List<GerarchiaUfficiFP> daInserire = new ArrayList<>(inputEntity);
        daInserire.removeAll(sulDB);

        List<GerarchiaUfficiFP> daEliminare = new ArrayList<>(sulDB);
        daEliminare.removeAll(inputEntity);

        this.inserimento(daInserire, utente, ufficio, data);
        this.cancellazione(daEliminare, utente, ufficio, data);

        return this.getAllByUfficioParent(input.get(0).getCodiceUfficioPrincipale());
    }

    @Override
    @Transactional
    public void deleteAllByCodiceUfficio(String codiceUfficio, String utenteCancellazione, String ufficio, Timestamp dataCancellazione) {
        gerarchiaUfficiFPRepository.deleteAllByCodiceUfficio(codiceUfficio, utenteCancellazione, ufficio, dataCancellazione);
    }

    // Metodo per l'inserimento
    @Transactional
    private List<GerarchiaUfficiFP> inserimento(List<GerarchiaUfficiFP> daInserire, String utente, String ufficio, Timestamp data){
        daInserire.forEach(el -> gerarchiaUfficiFPRepository.insert(el.getCodiceUfficioPrincipale(), el.getUfficioDipendente().getCodiceUfficio(), utente, ufficio, data));
        return null;
    }

    // Metodo per la cancellazione
    @Transactional
    private void cancellazione(List<GerarchiaUfficiFP> daEliminare, String utente, String ufficio, Timestamp data){

        daEliminare = daEliminare.stream().map(el -> {
            el.setDataCancellazione(data);
            el.setUtenteCancellazione(utente);
            el.setUfficioCancellazione(ufficio);
            return el;
        }).toList();

        gerarchiaUfficiFPRepository.saveAll(daEliminare);
    }
}
