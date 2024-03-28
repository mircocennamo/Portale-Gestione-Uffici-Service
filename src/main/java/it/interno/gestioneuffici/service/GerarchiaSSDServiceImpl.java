package it.interno.gestioneuffici.service;

import it.interno.gestioneuffici.dto.GerarchiaSSDDto;
import it.interno.gestioneuffici.entity.GerarchiaSSD;
import it.interno.gestioneuffici.exception.ListEmptyException;
import it.interno.gestioneuffici.mapper.GerarchiaSSDMapper;
import it.interno.gestioneuffici.repository.GerarchiaSSDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GerarchiaSSDServiceImpl implements GerarchiaSSDService{

    @Autowired
    private GerarchiaSSDRepository gerarchiaSSDRepository;
    @Autowired
    private GerarchiaSSDMapper gerarchiaSSDMapper;

    @Override
    public List<GerarchiaSSDDto> getAll() {
        return gerarchiaSSDRepository.getAll()
                .stream().map(el -> gerarchiaSSDMapper.toDto(el))
                .collect(Collectors.toList());
    }

    @Override
    public List<GerarchiaSSDDto> getAllByUfficioParent(String codiceUfficioPrincipale) {
        return gerarchiaSSDRepository.getAllByUfficioParent(codiceUfficioPrincipale)
                .stream().map(el -> gerarchiaSSDMapper.toDto(el))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<GerarchiaSSDDto> salvataggio(List<GerarchiaSSDDto> input, String utente, String ufficio, Timestamp data) {

        if(input == null || input.isEmpty())
            throw new ListEmptyException();

        List<GerarchiaSSD> inputEntity = input.stream().map(el -> gerarchiaSSDMapper.toEntity(el)).collect(Collectors.toList());
        List<GerarchiaSSD> sulDB = gerarchiaSSDRepository.getAllByUfficioParent(input.get(0).getCodiceUfficioPrincipale());

        List<GerarchiaSSD> daInserire = new ArrayList<>(inputEntity);
        daInserire.removeAll(sulDB);

        List<GerarchiaSSD> daEliminare = new ArrayList<>(sulDB);
        daEliminare.removeAll(inputEntity);

        this.inserimento(daInserire, utente, ufficio, data);
        this.cancellazione(daEliminare, utente, ufficio, data);

        return this.getAllByUfficioParent(input.get(0).getCodiceUfficioPrincipale());
    }

    @Override
    @Transactional
    public void deleteAllByCodiceUfficio(String codiceUfficio, String utenteCancellazione, String ufficio, Timestamp dataCancellazione) {
        gerarchiaSSDRepository.deleteAllByCodiceUfficio(codiceUfficio, utenteCancellazione, ufficio, dataCancellazione);
    }

    // Metodo per l'inserimento
    private List<GerarchiaSSD> inserimento(List<GerarchiaSSD> daInserire, String utente, String ufficio, Timestamp data){

        daInserire = daInserire.stream().map(el -> {
            el.setDataInserimento(data);
            el.setUtenteInserimento(utente);
            el.setUfficioInserimento(ufficio);
            return el;
        }).collect(Collectors.toList());

        return gerarchiaSSDRepository.saveAll(daInserire);
    }

    // Metodo per la cancellazione
    private void cancellazione(List<GerarchiaSSD> daEliminare, String utente, String ufficio, Timestamp data){

        daEliminare = daEliminare.stream().map(el -> {
            el.setDataCancellazione(data);
            el.setUtenteCancellazione(utente);
            el.setUfficioCancellazione(ufficio);
            return el;
        }).collect(Collectors.toList());

        gerarchiaSSDRepository.saveAll(daEliminare);
    }
}
