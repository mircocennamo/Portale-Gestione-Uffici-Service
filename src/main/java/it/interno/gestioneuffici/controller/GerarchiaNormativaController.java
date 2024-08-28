package it.interno.gestioneuffici.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.interno.gestioneuffici.dto.AlberoGerarchicoDto;
import it.interno.gestioneuffici.dto.GerarchiaNormativaDto;
import it.interno.gestioneuffici.dto.ResponseDto;
import it.interno.gestioneuffici.service.GerarchiaNormativaService;
import it.interno.gestioneuffici.utils.ConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/gerarchia-normativa", produces = {MediaType.APPLICATION_JSON_VALUE})
@Tag(name = "Gerarchia Normativa")
public class GerarchiaNormativaController {

    @Autowired
    private GerarchiaNormativaService gerarchiaNormativaService;

    @Operation(summary = "API per recuperare tutte le gerarchie normative")
    @GetMapping()
    public ResponseEntity<ResponseDto<List<GerarchiaNormativaDto>>> getAll(){

        List<GerarchiaNormativaDto> result = gerarchiaNormativaService.getAll();

        return ResponseEntity.ok(ResponseDto.<List<GerarchiaNormativaDto>>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "API per recuperare tutte le gerarchie normative dato l'ufficio di riferimento")
    @GetMapping("/by-ufficio/{codiceUfficio}")
    public ResponseEntity<ResponseDto<List<GerarchiaNormativaDto>>> getAllByUfficioParent(@PathVariable String codiceUfficio){

        List<GerarchiaNormativaDto> result = gerarchiaNormativaService.getAllByUfficioParent(codiceUfficio);

        return ResponseEntity.ok(ResponseDto.<List<GerarchiaNormativaDto>>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "API per generare l'albero gerarchico dato un ufficio")
    @GetMapping("/albero-gerarchico/{codiceUfficio}")
    public ResponseEntity<ResponseDto<AlberoGerarchicoDto>> getAlberoGerarchico(@PathVariable String codiceUfficio){

        AlberoGerarchicoDto result = gerarchiaNormativaService.getAlberoGerarchico(codiceUfficio);

        return ResponseEntity.ok(ResponseDto.<AlberoGerarchicoDto>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "API per il salvataggio delle gerarchie")
    @PostMapping
    public ResponseEntity<ResponseDto<List<GerarchiaNormativaDto>>> salvataggio(@RequestBody List<GerarchiaNormativaDto> input,
                                                                                @RequestParam String utente,
                                                                                @RequestParam String ufficio){

        List<GerarchiaNormativaDto> result = gerarchiaNormativaService.salvataggio(input, utente, ufficio, ConversionUtils.getCurrentTimestamp());

        return ResponseEntity.ok(ResponseDto.<List<GerarchiaNormativaDto>>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "Api per l'eliminazione di tutte le gerarchie dato il codice dell'ufficio")
    @DeleteMapping("/{codiceUfficio}")
    public ResponseEntity<ResponseDto<Object>> deleteAllByCodiceUfficio(@PathVariable String codiceUfficio,
                                                                        @RequestParam String utente,
                                                                        @RequestParam String ufficio){

        gerarchiaNormativaService.deleteAllByCodiceUfficio(codiceUfficio, utente, ufficio, ConversionUtils.getCurrentTimestamp());

        return ResponseEntity.ok(ResponseDto.builder()
                .code(HttpStatus.OK.value())
                .build());
    }

}
