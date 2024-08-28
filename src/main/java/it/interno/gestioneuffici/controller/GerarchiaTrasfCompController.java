package it.interno.gestioneuffici.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.interno.gestioneuffici.dto.GerarchiaTrasfCompDto;
import it.interno.gestioneuffici.dto.ResponseDto;
import it.interno.gestioneuffici.service.GerarchiaTrasfCompService;
import it.interno.gestioneuffici.utils.ConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/gerarchia-trasf-comp", produces = {MediaType.APPLICATION_JSON_VALUE})
@Tag(name = "Gerarchia Trasferimento Competenze")
public class GerarchiaTrasfCompController {

    @Autowired
    private GerarchiaTrasfCompService gerarchiaTrasfCompService;

    @Operation(summary = "API per recuperare tutte le gerarchie del trasferimento competenze")
    @GetMapping()
    public ResponseEntity<ResponseDto<List<GerarchiaTrasfCompDto>>> getAll(){

        List<GerarchiaTrasfCompDto> result = gerarchiaTrasfCompService.getAll();

        return ResponseEntity.ok(ResponseDto.<List<GerarchiaTrasfCompDto>>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "API per recuperare tutte le gerarchie SSD dato l'ufficio di riferimento")
    @GetMapping("/by-ufficio/{codiceUfficio}")
    public ResponseEntity<ResponseDto<List<GerarchiaTrasfCompDto>>> getAllByUfficioParent(@PathVariable String codiceUfficio){

        List<GerarchiaTrasfCompDto> result = gerarchiaTrasfCompService.getAllByUfficioParent(codiceUfficio);

        return ResponseEntity.ok(ResponseDto.<List<GerarchiaTrasfCompDto>>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "API per il salvataggio delle gerarchie")
    @PostMapping
    public ResponseEntity<ResponseDto<List<GerarchiaTrasfCompDto>>> salvataggio(@RequestBody List<GerarchiaTrasfCompDto> input,
                                                                                @RequestParam String utente,
                                                                                @RequestParam String ufficio){

        List<GerarchiaTrasfCompDto> result = gerarchiaTrasfCompService.salvataggio(input, utente, ufficio, ConversionUtils.getCurrentTimestamp());

        return ResponseEntity.ok(ResponseDto.<List<GerarchiaTrasfCompDto>>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "Api per l'eliminazione di tutte le gerarchie dato il codice dell'ufficio")
    @DeleteMapping("/{codiceUfficio}")
    public ResponseEntity<ResponseDto<Object>> deleteAllByCodiceUfficio(@PathVariable String codiceUfficio,
                                                                        @RequestParam String utente,
                                                                        @RequestParam String ufficio){

        gerarchiaTrasfCompService.deleteAllByCodiceUfficio(codiceUfficio, utente, ufficio, ConversionUtils.getCurrentTimestamp());

        return ResponseEntity.ok(ResponseDto.builder()
                .code(HttpStatus.OK.value())
                .build());
    }

    @Operation(summary = "API per verificare se un ufficio ha un trasferimento di competenze verso un altro ufficio")
    @GetMapping("/ufficio-princ/{codiceUfficioPrincipale}/ufficio-dip/{codiceUfficioDipendente}")
    public ResponseEntity<ResponseDto<Boolean>> ufficioAbilitatoConTrasfComp(@PathVariable String codiceUfficioPrincipale,
                                                                             @PathVariable String codiceUfficioDipendente){

        Boolean ufficioAbilitato = gerarchiaTrasfCompService.ufficioAbilitatoConTrasfComp(codiceUfficioPrincipale, codiceUfficioDipendente);

        return ResponseEntity.ok(ResponseDto.<Boolean>builder()
                .code(HttpStatus.OK.value())
                .body(ufficioAbilitato)
                .build());
    }

}
