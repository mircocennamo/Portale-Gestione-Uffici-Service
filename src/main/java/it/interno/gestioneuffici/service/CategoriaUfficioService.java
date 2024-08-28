package it.interno.gestioneuffici.service;

import it.interno.gestioneuffici.dto.CategoriaUfficioDto;

import java.util.List;

public interface CategoriaUfficioService {

    List<CategoriaUfficioDto> getAll();
    CategoriaUfficioDto getById(String codiceCategoriaUfficio, Integer idForzaPolizia);
    List<CategoriaUfficioDto> getAllByForzaPolizia(Integer idForzaPolizia);
    List<CategoriaUfficioDto> getAllByForzaPoliziaList(String descrizione, List<Integer> forzePolizia);
}
