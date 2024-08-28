package it.interno.gestioneuffici.mapper;

import it.interno.gestioneuffici.dto.GerarchiaUfficiFPDto;
import it.interno.gestioneuffici.entity.GerarchiaUfficiFP;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface GerarchiaUfficiFPMapper {

    GerarchiaUfficiFP toEntity(GerarchiaUfficiFPDto gerarchiaUfficiFPDto);

    @InheritInverseConfiguration
    GerarchiaUfficiFPDto toDto(GerarchiaUfficiFP gerarchiaUfficiFP);
}
