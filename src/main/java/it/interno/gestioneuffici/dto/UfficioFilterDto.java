package it.interno.gestioneuffici.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UfficioFilterDto {

    @NotNull(message = "Il paramentro di ricerca deve essere valorizzato")
    private String parametroRicerca;

    private String luogo;

    private Integer forzaPolizia;

    private Character abilitatoEmissioni;

    private Character statistichePersonale;

    @NotNull(message = "Il numero di pagina deve essere valorizzato")
    @Min(value = 0, message = "Il numero di pagina non può essere minore di 0")
    private Integer pagina;

    @NotNull(message = "Il numero di elementi deve essere valorizzato")
    @Min(value = 1, message = "Il numero di elementi non può essere minore di 1")
    private Integer numeroElementi;

    @NotBlank(message = "Il parametro di ordinamento deve essere valorizzato")
    private String sortBy;

    @NotBlank(message = "L'ordine di ordinamento deve essere valorizzato")
    private String sortDirection;

}
