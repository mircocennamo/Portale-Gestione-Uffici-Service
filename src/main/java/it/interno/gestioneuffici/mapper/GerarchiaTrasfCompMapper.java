package it.interno.gestioneuffici.mapper;

import it.interno.gestioneuffici.dto.GerarchiaTrasfCompDto;
import it.interno.gestioneuffici.entity.GerarchiaTrasfComp;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface GerarchiaTrasfCompMapper {

    GerarchiaTrasfComp toEntity(GerarchiaTrasfCompDto gerarchiaTrasfCompDto);

    @InheritInverseConfiguration
    GerarchiaTrasfCompDto toDto(GerarchiaTrasfComp gerarchiaTrasfComp);
}
