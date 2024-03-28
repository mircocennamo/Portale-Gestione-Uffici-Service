package it.interno.gestioneuffici.mapper;

import it.interno.gestioneuffici.dto.GerarchiaSSDDto;
import it.interno.gestioneuffici.entity.GerarchiaSSD;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface GerarchiaSSDMapper {

    GerarchiaSSD toEntity(GerarchiaSSDDto gerarchiaSSDDto);

    @InheritInverseConfiguration
    GerarchiaSSDDto toDto(GerarchiaSSD gerarchiaSSD);
}
