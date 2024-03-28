package it.interno.gestioneuffici.service;

import it.interno.gestioneuffici.dto.AlberoGerarchicoDto;
import it.interno.gestioneuffici.dto.GerarchiaNormativaDto;
import it.interno.gestioneuffici.entity.GerarchiaNormativa;
import it.interno.gestioneuffici.exception.ListEmptyException;
import it.interno.gestioneuffici.mapper.GerarchiaNormativaMapper;
import it.interno.gestioneuffici.repository.GerarchiaNormativaRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GerarchiaNormativaServiceImpl implements GerarchiaNormativaService{

    @Autowired
    private GerarchiaNormativaRepository gerarchiaNormativaRepository;
    @Autowired
    private GerarchiaNormativaMapper gerarchiaNormativaMapper;

    @Override
    public List<GerarchiaNormativaDto> getAll() {
        return gerarchiaNormativaRepository.getAll(LocalDate.now())
                .stream().map(el -> gerarchiaNormativaMapper.toDto(el))
                .collect(Collectors.toList());
    }

    @Override
    public List<GerarchiaNormativaDto> getAllByUfficioParent(String codiceUfficioPrincipale) {
        return gerarchiaNormativaRepository.getAllByUfficioParent(codiceUfficioPrincipale, LocalDate.now())
                .stream().map(el -> gerarchiaNormativaMapper.toDto(el))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<GerarchiaNormativaDto> salvataggio(List<GerarchiaNormativaDto> input, String utente, String ufficio, Timestamp data) {

        if(input == null || input.isEmpty())
            throw new ListEmptyException();

        List<GerarchiaNormativa> inputEntity = input.stream().map(el -> gerarchiaNormativaMapper.toEntity(el))
                .collect(Collectors.toList());

        List<GerarchiaNormativa> sulDB = gerarchiaNormativaRepository.getAllByUfficioParent(input.get(0).getCodiceUfficioPrincipale(), LocalDate.now());

        List<GerarchiaNormativa> daInserire = new ArrayList<>(inputEntity);
        daInserire.removeAll(sulDB);

        List<GerarchiaNormativa> daEliminare = new ArrayList<>(sulDB);
        daEliminare.removeAll(inputEntity);

        this.inserimento(daInserire, utente, ufficio, data);
        this.cancellazione(daEliminare, utente, ufficio, data);

        return this.getAllByUfficioParent(input.get(0).getCodiceUfficioPrincipale());
    }

    @Override
    @Transactional
    public void deleteAllByCodiceUfficio(String codiceUfficio, String utenteCancellazione, String ufficio, Timestamp dataCancellazione) {
        gerarchiaNormativaRepository.deleteAllByCodiceUfficio(codiceUfficio, utenteCancellazione, ufficio, dataCancellazione);
    }

    // Metodo per l'inserimento
    private List<GerarchiaNormativa> inserimento(List<GerarchiaNormativa> daInserire, String utente, String ufficio, Timestamp data){

        daInserire = daInserire.stream().map(el -> {
            el.setDataInserimento(data);
            el.setUtenteInserimento(utente);
            el.setUfficioInserimento(ufficio);
            return el;
        }).collect(Collectors.toList());

        return gerarchiaNormativaRepository.saveAll(daInserire);
    }

    // Metodo per la cancellazione
    private void cancellazione(List<GerarchiaNormativa> daEliminare, String utente, String ufficio, Timestamp data){

        daEliminare = daEliminare.stream().map(el -> {
            el.setDataCancellazione(data);
            el.setUtenteCancellazione(utente);
            el.setUfficioCancellazione(ufficio);
            return el;
        }).collect(Collectors.toList());

        gerarchiaNormativaRepository.saveAll(daEliminare);
    }

    @Override
    public AlberoGerarchicoDto getAlberoGerarchico(String codiceUfficio) {

        AlberoGerarchicoDto albero = new AlberoGerarchicoDto(codiceUfficio, null);
        albero.setListaFigli(getAlberoRicorsivo(codiceUfficio, new ArrayList<>()));

        return albero;
    }

    private List <AlberoGerarchicoDto> getAlberoRicorsivo(String codiceUfficio, List <AlberoGerarchicoDto> sottoalbero){

        if(StringUtils.isBlank(codiceUfficio))
            return new ArrayList<>();

        List<GerarchiaNormativa> figli = gerarchiaNormativaRepository.getAllByUfficioParent(codiceUfficio, LocalDate.now());
        figli.forEach(el -> {

            AlberoGerarchicoDto temp = new AlberoGerarchicoDto(el.getUfficioDipendente().getCodiceUfficio(), new ArrayList<>());
            sottoalbero.add(temp);
            getAlberoRicorsivo(temp.getCodiceUfficio(), temp.getListaFigli());
        });

        return sottoalbero;
    }
}
