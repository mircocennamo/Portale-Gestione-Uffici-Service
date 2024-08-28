package it.interno.gestioneuffici.service;

import it.interno.gestioneuffici.dto.ForzaPoliziaDto;
import it.interno.gestioneuffici.mapper.ForzaPoliziaMapper;
import it.interno.gestioneuffici.repository.ForzaPoliziaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForzaPoliziaServiceImpl implements ForzaPoliziaService{

    @Autowired
    private ForzaPoliziaRepository forzaPoliziaRepository;
    @Autowired
    private ForzaPoliziaMapper forzaPoliziaMapper;

    @Override
    public List<ForzaPoliziaDto> getAllForzePolizia() {
        return forzaPoliziaRepository.findAllForzePolizia()
                .stream().map(el -> forzaPoliziaMapper.toDto(el))
                .toList();
    }

    @Override
    public ForzaPoliziaDto getForzaPoliziaById(Integer idForzaPolizia) {
        return forzaPoliziaMapper.toDto(forzaPoliziaRepository.findForzaPoliziaById(idForzaPolizia));
    }

    @Override
    public List<ForzaPoliziaDto> getAllForzePoliziaConMapping() {
        return forzaPoliziaRepository.getAllForzePoliziaConMapping()
                .stream().map(el -> forzaPoliziaMapper.toDto(el))
                .toList();
    }
}
