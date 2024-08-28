package it.interno.gestioneuffici.service;

import it.interno.gestioneuffici.dto.CategoriaUfficioDto;
import it.interno.gestioneuffici.entity.CategoriaUfficio;
import it.interno.gestioneuffici.mapper.CategoriaUfficioMapper;
import it.interno.gestioneuffici.repository.CategoriaUfficioRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import static it.interno.gestioneuffici.repository.specification.CategoriaUfficioSpecification.*;

@Service
public class CategoriaUfficioServiceImpl implements CategoriaUfficioService{

    @Autowired
    private CategoriaUfficioRepository categoriaUfficioRepository;
    @Autowired
    private CategoriaUfficioMapper categoriaUfficioMapper;

    @Override
    public List<CategoriaUfficioDto> getAll() {
        return categoriaUfficioRepository.getAll()
                .stream().map(el -> {
                    el.setDescrizione(el.getDescrizione().trim());
                    return categoriaUfficioMapper.toDto(el);
                })
                .toList();
    }

    @Override
    public CategoriaUfficioDto getById(String codiceCategoriaUfficio, Integer idForzaPolizia) {
        CategoriaUfficioDto result = categoriaUfficioMapper.toDto(categoriaUfficioRepository.getById(codiceCategoriaUfficio, idForzaPolizia));
        result.setDescrizione(result.getDescrizione().trim());

        return result;
    }

    @Override
    public List<CategoriaUfficioDto> getAllByForzaPolizia(Integer idForzaPolizia) {
        return categoriaUfficioRepository.getAllByForzaPolizia(idForzaPolizia)
                .stream().map(el -> {
                    el.setDescrizione(el.getDescrizione().trim());
                    return categoriaUfficioMapper.toDto(el);
                })
                .toList();
    }

    @Override
    public List<CategoriaUfficioDto> getAllByForzaPoliziaList(String descrizione, List<Integer> forzePolizia) {

        Specification<CategoriaUfficio> spec = dataCancellazioneIsNull();

        if(!StringUtils.isBlank(descrizione))
            spec = spec.and(descrizioneLike(descrizione));

        if(forzePolizia != null && !forzePolizia.isEmpty())
            spec = spec.and(forzaPoliziaIn(forzePolizia));

        return categoriaUfficioRepository.findAll(spec).stream().map(el -> {
                    el.setDescrizione(el.getDescrizione().trim());
                    return categoriaUfficioMapper.toDto(el);
                })
                .toList();
    }
}
