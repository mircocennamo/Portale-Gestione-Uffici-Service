package it.interno.gestioneuffici.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.interno.gestioneuffici.dto.GerarchiaUfficiFPDto;
import it.interno.gestioneuffici.dto.ResponseDto;
import it.interno.gestioneuffici.service.GerarchiaUfficiFPService;
import it.interno.gestioneuffici.utils.ConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/gerarchia-uffici-fp", produces = {MediaType.APPLICATION_JSON_VALUE})
@Tag(name = "Gerarchia Uffici FP")
public class GerarchiaUfficiFPController {

    @Autowired
    private GerarchiaUfficiFPService gerarchiaUfficiFPService;

    @Operation(summary = "API per recuperare tutte le gerarchie degli uffici FP")
    @GetMapping()
    public ResponseEntity<ResponseDto<List<GerarchiaUfficiFPDto>>> getAll(){

        List<GerarchiaUfficiFPDto> result = gerarchiaUfficiFPService.getAll();

        return ResponseEntity.ok(ResponseDto.<List<GerarchiaUfficiFPDto>>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "API per recuperare tutte le gerarchie uffici FP dato l'ufficio di riferimento")
    @GetMapping("/by-ufficio/{codiceUfficio}")
    public ResponseEntity<ResponseDto<List<GerarchiaUfficiFPDto>>> getAllByUfficioParent(@PathVariable String codiceUfficio){

        List<GerarchiaUfficiFPDto> result = gerarchiaUfficiFPService.getAllByUfficioParent(codiceUfficio);

        return ResponseEntity.ok(ResponseDto.<List<GerarchiaUfficiFPDto>>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "API per il salvataggio delle gerarchie")
    @PostMapping
    public ResponseEntity<ResponseDto<List<GerarchiaUfficiFPDto>>> salvataggio(@RequestBody List<GerarchiaUfficiFPDto> input,
                                                                                @RequestParam String utente,
                                                                                @RequestParam String ufficio){

        List<GerarchiaUfficiFPDto> result = gerarchiaUfficiFPService.salvataggio(input, utente, ufficio, ConversionUtils.getCurrentTimestamp());

        return ResponseEntity.ok(ResponseDto.<List<GerarchiaUfficiFPDto>>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "Api per l'eliminazione di tutte le gerarchie dato il codice dell'ufficio")
    @DeleteMapping("/{codiceUfficio}")
    public ResponseEntity<ResponseDto<Object>> deleteAllByCodiceUfficio(@PathVariable String codiceUfficio,
                                                                        @RequestParam String utente,
                                                                        @RequestParam String ufficio){

        gerarchiaUfficiFPService.deleteAllByCodiceUfficio(codiceUfficio, utente, ufficio, ConversionUtils.getCurrentTimestamp());

        return ResponseEntity.ok(ResponseDto.builder()
                .code(HttpStatus.OK.value())
                .build());
    }
}
