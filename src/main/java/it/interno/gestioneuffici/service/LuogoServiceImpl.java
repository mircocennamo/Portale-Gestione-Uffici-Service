package it.interno.gestioneuffici.service;

import it.interno.gestioneuffici.dto.LuogoDto;
import it.interno.gestioneuffici.mapper.LuogoMapper;
import it.interno.gestioneuffici.repository.LuogoRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LuogoServiceImpl implements LuogoService{

    @Autowired
    private LuogoMapper luogoMapper;
    @Autowired
    private LuogoRepository luogoRepository;

    @Override
    public List<LuogoDto> findAllByDescrizioneLike(String parametro, LocalDate dataRiferimento, String inLuogo) {
        return luogoRepository.findAllByDescrizioneLike(StringUtils.isBlank(parametro) ? "" : parametro, inLuogo)
                .stream()
                .map(el -> {
                    el.setDescrizione(el.getDescrizione().trim());
                    return luogoMapper.toDto(el);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<LuogoDto> findAllByDescrizioneLikeAndProvincia(String parametro, LocalDate dataRiferimento, String inLuogo, String provincia) {
        return luogoRepository.findAllByDescrizioneLikeAndProvincia(parametro, inLuogo, provincia)
                .stream()
                .map(el -> {
                    el.setDescrizione(el.getDescrizione().trim());
                    return luogoMapper.toDto(el);
                })
                .collect(Collectors.toList());
    }
}
