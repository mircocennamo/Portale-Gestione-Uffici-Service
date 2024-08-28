package it.interno.gestioneuffici.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.interno.gestioneuffici.dto.CategoriaUfficioDto;
import it.interno.gestioneuffici.dto.ResponseDto;
import it.interno.gestioneuffici.service.CategoriaUfficioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/categoria-ufficio", produces = {MediaType.APPLICATION_JSON_VALUE})
@Tag(name = "Categoria Ufficio")
public class CategoriaUfficioController {

    @Autowired
    private CategoriaUfficioService categoriaUfficioService;

    @Operation(summary = "API per recuperare tutte le categorie ufficio")
    @GetMapping()
    public ResponseEntity<ResponseDto<List<CategoriaUfficioDto>>> getAll(){

        List<CategoriaUfficioDto> result = categoriaUfficioService.getAll();

        return ResponseEntity.ok(ResponseDto.<List<CategoriaUfficioDto>>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "API per recuperare la categoria ufficio dati codice categoria e id forza di polizia")
    @GetMapping("/by-id")
    public ResponseEntity<ResponseDto<CategoriaUfficioDto>> getById(@RequestParam String codiceCategoriaUfficio,
                                                                    @RequestParam Integer idForzaPolizia){

        CategoriaUfficioDto result = categoriaUfficioService.getById(codiceCategoriaUfficio, idForzaPolizia);

        return ResponseEntity.ok(ResponseDto.<CategoriaUfficioDto>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "API per recuperare tutte le categorie ufficio data la forza di polizia")
    @GetMapping("/by-forza-polizia/{idForzaPolizia}")
    public ResponseEntity<ResponseDto<List<CategoriaUfficioDto>>> getAllByForzaPolizia(@PathVariable Integer idForzaPolizia){

        List<CategoriaUfficioDto> result = categoriaUfficioService.getAllByForzaPolizia(idForzaPolizia);

        return ResponseEntity.ok(ResponseDto.<List<CategoriaUfficioDto>>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "API per recuperare tutte le categorie ufficio data la forza di polizia")
    @GetMapping("/by-forza-polizia-list")
    public ResponseEntity<ResponseDto<List<CategoriaUfficioDto>>> getAllByForzaPoliziaList(@RequestParam(required = false) String descrizione,
                                                                                           @RequestParam(required = false) List<Integer> forzePolizia){

        List<CategoriaUfficioDto> result = categoriaUfficioService.getAllByForzaPoliziaList(descrizione, forzePolizia);

        return ResponseEntity.ok(ResponseDto.<List<CategoriaUfficioDto>>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

}
