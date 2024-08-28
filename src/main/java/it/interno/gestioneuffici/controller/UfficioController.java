package it.interno.gestioneuffici.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.interno.gestioneuffici.dto.ResponseDto;
import it.interno.gestioneuffici.dto.UfficioDto;
import it.interno.gestioneuffici.dto.UfficioFilterDto;
import it.interno.gestioneuffici.service.UfficioService;
import it.interno.gestioneuffici.utils.ConversionUtils;
import it.interno.gestioneuffici.validation.ValidazioneAggiornamento;
import it.interno.gestioneuffici.validation.ValidazioneInserimento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "/ufficio", produces = {MediaType.APPLICATION_JSON_VALUE})
@Tag(name = "Ufficio")
@Validated
public class UfficioController {

    @Autowired
    private UfficioService ufficioService;

    @Operation(summary = "API per recuperare tutti gli uffici")
    @GetMapping
    public ResponseEntity<ResponseDto<List<UfficioDto>>> getAllUffici(){

        List<UfficioDto> result = ufficioService.getAllUffici();

        return ResponseEntity.ok(ResponseDto.<List<UfficioDto>>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "API per generare il codice ufficio")
    @GetMapping("/calcolo-codice-ufficio")
    public ResponseEntity<ResponseDto<String>> calcoloCodiceUfficio(@RequestParam String codiceProvincia, @RequestParam Integer idForzaDiPolizia){

        String result = ufficioService.generazioneCodiceUfficio(codiceProvincia, idForzaDiPolizia);

        return ResponseEntity.ok(ResponseDto.<String>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "API per recuperare l'ufficio dato l'id")
    @GetMapping("/{idUfficio}")
    public ResponseEntity<ResponseDto<UfficioDto>> getUfficioById(@PathVariable String idUfficio){

        UfficioDto result = ufficioService.getUfficioById(idUfficio.toUpperCase());

        return ResponseEntity.ok(ResponseDto.<UfficioDto>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "API per recuperare la lista di uffici dato un parametro di ricerca")
    @PostMapping("/autocomplete/{parametroRicerca}")
    @Validated
    public ResponseEntity<ResponseDto<List<UfficioDto>>> findAllByParametroAutocomplete(@PathVariable String parametroRicerca,
                                                                                        @RequestParam(required = false) Integer idForzaPolizia,
                                                                                        @RequestBody List<@NotNull String> codiciUfficioDaEscludere,
                                                                                        @RequestParam String ufficioOperatore,
                                                                                        @RequestParam String ruoloOperatore){

        List<UfficioDto> result = ufficioService.findAllByParametroAutocomplete(parametroRicerca, idForzaPolizia, codiciUfficioDaEscludere, ufficioOperatore, ruoloOperatore);

        return ResponseEntity.ok(ResponseDto.<List<UfficioDto>>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "API per recuperare la lista di uffici dato un parametro di ricerca senza limitazione di visibilita")
    @PostMapping("/autocomplete-free/{parametroRicerca}")
    @Validated
    public ResponseEntity<ResponseDto<List<UfficioDto>>> findAllByParametroAutocompleteFree(@PathVariable String parametroRicerca,
                                                                                        @RequestParam(required = false) Integer idForzaPolizia,
                                                                                        @RequestBody List<@NotNull String> codiciUfficioDaEscludere){

        List<UfficioDto> result = ufficioService.findAllByParametroAutocompleteFree(parametroRicerca, idForzaPolizia, codiciUfficioDaEscludere);

        return ResponseEntity.ok(ResponseDto.<List<UfficioDto>>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "API per recuperare la lista di uffici dato un parametro di ricerca")
    @PostMapping("/autocomplete/enti-geografia")
    public ResponseEntity<ResponseDto<List<UfficioDto>>> autocompleteByEntiAndGeografia(@RequestParam String parametroRicerca,
                                                                                        @RequestParam(required = false) List<String> idForzePolizia,
                                                                                        @RequestParam(required = false) List<String> codiciGeografici,
                                                                                        @RequestBody List<String> codiciUfficioDaEscludere,
                                                                                        @RequestParam String ufficioOperatore,
                                                                                        @RequestParam String ruoloOperatore){

        List<UfficioDto> result = ufficioService.autocompleteByEntiAndGeografia(parametroRicerca, idForzePolizia, codiciGeografici, codiciUfficioDaEscludere, ufficioOperatore, ruoloOperatore);

        return ResponseEntity.ok(ResponseDto.<List<UfficioDto>>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "API per recuperare la lista di uffici paginata")
    @PostMapping("/search-and-paginate")
    public ResponseEntity<ResponseDto<Page<UfficioDto>>> searchAndPaginateUffici(@RequestBody @Valid UfficioFilterDto filtro,
                                                                                 @RequestParam String ufficioOperatore,
                                                                                 @RequestParam String ruoloOperatore){

        Page<UfficioDto> result = ufficioService.searchAndPaginate(filtro, ufficioOperatore, ruoloOperatore);

        return ResponseEntity.ok(ResponseDto.<Page<UfficioDto>>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "API per inserire un nuovo ufficio")
    @PostMapping()
    @Validated(ValidazioneInserimento.class)
    public ResponseEntity<ResponseDto<UfficioDto>> insertNewUfficio(@RequestBody @Valid UfficioDto ufficio,
                                                                    @RequestParam @NotBlank String utenteInserimento,
                                                                    @RequestParam @NotBlank String codiceUfficio){

        UfficioDto result = ufficioService.insertNewUfficio(ufficio, utenteInserimento,
                ConversionUtils.getCurrentTimestamp(), codiceUfficio);

        return ResponseEntity.ok(ResponseDto.<UfficioDto>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "API per inserire una lista di uffici")
    @PostMapping("/inserimento-massivo")
    @Validated(ValidazioneInserimento.class)
    public ResponseEntity<ResponseDto<List<UfficioDto>>> insertNewUffici(@RequestBody List<@Valid UfficioDto> uffici,
                                                                        @RequestParam @NotBlank String utenteInserimento,
                                                                        @RequestParam @NotBlank String codiceUfficio){

        List<UfficioDto> result = ufficioService.insertNewUffici(uffici, utenteInserimento,
                ConversionUtils.getCurrentTimestamp(), codiceUfficio);

        return ResponseEntity.ok(ResponseDto.<List<UfficioDto>>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "API per aggiornare un ufficio")
    @PutMapping()
    @Validated(ValidazioneAggiornamento.class)
    public ResponseEntity<ResponseDto<UfficioDto>> updateUfficio(@RequestBody @Valid UfficioDto ufficio,
                                                                 @RequestParam @NotBlank String utenteAggiornamento,
                                                                 @RequestParam String codiceUfficio){

        UfficioDto result = ufficioService.updateUfficio(ufficio, utenteAggiornamento, ConversionUtils.getCurrentTimestamp(), codiceUfficio);

        return ResponseEntity.ok(ResponseDto.<UfficioDto>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "API per eliminare un ufficio")
    @DeleteMapping("/{idUfficio}")
    public ResponseEntity<ResponseDto<UfficioDto>> deleteUfficio(@PathVariable String idUfficio,
                                                                 @RequestParam @NotBlank String utenteCancellazione,
                                                                 @RequestParam @NotBlank String codiceUfficio){

        UfficioDto result = ufficioService.deleteUfficio(idUfficio, utenteCancellazione,
                ConversionUtils.getCurrentTimestamp(), codiceUfficio);

        return ResponseEntity.ok(ResponseDto.<UfficioDto>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

}
