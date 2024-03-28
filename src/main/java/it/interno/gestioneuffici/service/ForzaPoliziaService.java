package it.interno.gestioneuffici.service;

import it.interno.gestioneuffici.dto.ForzaPoliziaDto;

import java.util.List;

public interface ForzaPoliziaService {

    List<ForzaPoliziaDto> getAllForzePolizia();
    ForzaPoliziaDto getForzaPoliziaById(Integer idForzaPolizia);
    List<ForzaPoliziaDto> getAllForzePoliziaConMapping();

}
