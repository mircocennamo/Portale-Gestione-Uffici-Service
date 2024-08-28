package it.interno.gestioneuffici.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AlberoGerarchicoDto {

    private String codiceUfficio;
    private String descrizione;
    private List<AlberoGerarchicoDto> listaFigli;

}
