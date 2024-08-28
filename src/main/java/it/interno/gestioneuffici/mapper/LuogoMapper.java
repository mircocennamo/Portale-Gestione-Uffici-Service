package it.interno.gestioneuffici.mapper;

import it.interno.gestioneuffici.dto.LuogoDto;
import it.interno.gestioneuffici.entity.Luogo;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface LuogoMapper {

    Luogo toEntity(LuogoDto luogoDto);

    @InheritInverseConfiguration
    @Mapping(target = "descrizione", expression = "java(luogo.getDescrizione() == null ? null : luogo.getDescrizione().trim())")
    LuogoDto toDto(Luogo luogo);
}
