package it.interno.gestioneuffici.mapper;

import it.interno.gestioneuffici.dto.UfficioDto;
import it.interno.gestioneuffici.entity.Ufficio;
import it.interno.gestioneuffici.repository.ComandanteUfficioRepository;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UfficioMapper {

    Ufficio toEntity(UfficioDto ufficioDto);

    @InheritInverseConfiguration
    @Mapping(target = "comandante", expression = "java(repo == null ? null : repo.getComandanteByUfficio(ufficio.getCodiceUfficio()))")
    @Mapping(target = "descrizioneUfficio", expression = "java(ufficio.getDescrizioneUfficio() == null ? null : ufficio.getDescrizioneUfficio().trim())")
    UfficioDto toDto(Ufficio ufficio, ComandanteUfficioRepository repo);
}
