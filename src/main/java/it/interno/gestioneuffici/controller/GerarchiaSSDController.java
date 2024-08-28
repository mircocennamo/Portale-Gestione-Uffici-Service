package it.interno.gestioneuffici.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.interno.gestioneuffici.dto.GerarchiaSSDDto;
import it.interno.gestioneuffici.dto.ResponseDto;
import it.interno.gestioneuffici.service.GerarchiaSSDService;
import it.interno.gestioneuffici.utils.ConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/gerarchia-ssd", produces = {MediaType.APPLICATION_JSON_VALUE})
@Tag(name = "Gerarchia SSD")
public class GerarchiaSSDController {

    @Autowired
    private GerarchiaSSDService gerarchiaSSDService;

    @Operation(summary = "API per recuperare tutte le gerarchie SSD")
    @GetMapping()
    public ResponseEntity<ResponseDto<List<GerarchiaSSDDto>>> getAll(){

        List<GerarchiaSSDDto> result = gerarchiaSSDService.getAll();

        return ResponseEntity.ok(ResponseDto.<List<GerarchiaSSDDto>>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "API per recuperare tutte le gerarchie SSD dato l'ufficio di riferimento")
    @GetMapping("/by-ufficio/{codiceUfficio}")
    public ResponseEntity<ResponseDto<List<GerarchiaSSDDto>>> getAllByUfficioParent(@PathVariable String codiceUfficio){

        List<GerarchiaSSDDto> result = gerarchiaSSDService.getAllByUfficioParent(codiceUfficio);

        return ResponseEntity.ok(ResponseDto.<List<GerarchiaSSDDto>>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "API per il salvataggio delle gerarchie")
    @PostMapping
    public ResponseEntity<ResponseDto<List<GerarchiaSSDDto>>> salvataggio(@RequestBody List<GerarchiaSSDDto> input,
                                                                          @RequestParam String utente,
                                                                          @RequestParam String ufficio){

        List<GerarchiaSSDDto> result = gerarchiaSSDService.salvataggio(input, utente, ufficio, ConversionUtils.getCurrentTimestamp());

        return ResponseEntity.ok(ResponseDto.<List<GerarchiaSSDDto>>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "Api per l'eliminazione di tutte le gerarchie dato il codice dell'ufficio")
    @DeleteMapping("/{codiceUfficio}")
    public ResponseEntity<ResponseDto<Object>> deleteAllByCodiceUfficio(@PathVariable String codiceUfficio,
                                                                        @RequestParam String utente,
                                                                        @RequestParam String ufficio){

        gerarchiaSSDService.deleteAllByCodiceUfficio(codiceUfficio, utente, ufficio, ConversionUtils.getCurrentTimestamp());

        return ResponseEntity.ok(ResponseDto.builder()
                .code(HttpStatus.OK.value())
                .build());
    }
}
