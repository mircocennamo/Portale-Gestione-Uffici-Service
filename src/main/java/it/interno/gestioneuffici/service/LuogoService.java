package it.interno.gestioneuffici.service;

import it.interno.gestioneuffici.dto.LuogoDto;

import java.time.LocalDate;
import java.util.List;

public interface LuogoService {

    List<LuogoDto> findAllByDescrizioneLike(String parametro, LocalDate dataRiferimento, String inLuogo);
    List<LuogoDto> findAllByDescrizioneLikeAndProvincia(String parametro, LocalDate dataRiferimento, String inLuogo, String provincia);
    List<LuogoDto> getByDescrizioneInLuogoAndFiltroGeografico(String descrizioneLuogo, String inLuogo, LocalDate dataRif, List<String> filtroGeografico);
}
