package it.interno.gestioneuffici.mapper;

import it.interno.gestioneuffici.dto.GerarchiaNormativaDto;
import it.interno.gestioneuffici.entity.GerarchiaNormativa;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface GerarchiaNormativaMapper {

    GerarchiaNormativa toEntity(GerarchiaNormativaDto gerarchiaNormativaDto);

    @InheritInverseConfiguration
    GerarchiaNormativaDto toDto(GerarchiaNormativa gerarchiaNormativa);
}
