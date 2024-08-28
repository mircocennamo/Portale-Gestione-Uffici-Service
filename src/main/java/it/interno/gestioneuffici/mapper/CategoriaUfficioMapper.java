package it.interno.gestioneuffici.mapper;

import it.interno.gestioneuffici.dto.CategoriaUfficioDto;
import it.interno.gestioneuffici.entity.CategoriaUfficio;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CategoriaUfficioMapper {

    CategoriaUfficio toEntity(CategoriaUfficioDto categoriaUfficioDto);

    @InheritInverseConfiguration
    @Mapping(target = "descrizione", expression = "java(categoriaUfficio.getDescrizione() == null ? null : categoriaUfficio.getDescrizione().trim())")
    CategoriaUfficioDto toDto(CategoriaUfficio categoriaUfficio);

}
