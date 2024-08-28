package it.interno.gestioneuffici.service;

import it.interno.gestioneuffici.dto.LuogoDto;
import it.interno.gestioneuffici.mapper.LuogoMapper;
import it.interno.gestioneuffici.repository.LuogoRepository;
import it.interno.gestioneuffici.utils.ConversionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
                .toList();
    }

    @Override
    public List<LuogoDto> findAllByDescrizioneLikeAndProvincia(String parametro, LocalDate dataRiferimento, String inLuogo, String provincia) {
        return luogoRepository.findAllByDescrizioneLikeAndProvincia(parametro, inLuogo, provincia)
                .stream()
                .map(el -> {
                    el.setDescrizione(el.getDescrizione().trim());
                    return luogoMapper.toDto(el);
                })
                .toList();
    }

    @Override
    public List<LuogoDto> getByDescrizioneInLuogoAndFiltroGeografico(String descrizioneLuogo, String inLuogo, LocalDate dataRif, List<String> filtroGeografico) {
        List<LuogoDto> result =  luogoRepository.getByDescrizioneInLuogoAndFiltroGeografico(
                StringUtils.isBlank(descrizioneLuogo) ? "" : descrizioneLuogo,
                inLuogo,
                dataRif == null ? ConversionUtils.getCurrentTimestamp().toLocalDateTime().toLocalDate() : dataRif
        )
                .stream()
                .map(luogoMapper::toDto)
                .toList();

        if(filtroGeografico == null || filtroGeografico.isEmpty())
            return result;

        return result.stream().filter(el -> {
            if(inLuogo.equals("04") && filtroGeografico.contains(el.getCodiceProvincia()))
                return true;
            if(inLuogo.equals("03") && filtroGeografico.contains(el.getCodiceRegione()))
                return true;

            return false;
        }).toList();
    }
}
