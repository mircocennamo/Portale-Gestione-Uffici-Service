package it.interno.gestioneuffici.mapper;

import it.interno.gestioneuffici.dto.ForzaPoliziaDto;
import it.interno.gestioneuffici.entity.ForzaPolizia;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ForzaPoliziaMapper {

    ForzaPolizia toEntity(ForzaPoliziaDto forzaPoliziaDto);

    @InheritInverseConfiguration
    ForzaPoliziaDto toDto(ForzaPolizia forzaPolizia);

}
