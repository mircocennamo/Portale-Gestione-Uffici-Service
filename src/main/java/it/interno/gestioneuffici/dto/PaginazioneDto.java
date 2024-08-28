package it.interno.gestioneuffici.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaginazioneDto {
    private Integer pagina;
    private Integer numeroElementi;
    private String sortBy;
    private String sortDirection;
}
