package it.interno.gestioneuffici.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.interno.gestioneuffici.dto.ResponseDto;
import it.interno.gestioneuffici.dto.ForzaPoliziaDto;
import it.interno.gestioneuffici.service.ForzaPoliziaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/forza-polizia", produces = {MediaType.APPLICATION_JSON_VALUE})
@Tag(name = "Forza di Polizia")
public class ForzaPoliziaController {

    @Autowired
    private ForzaPoliziaService forzaPoliziaService;

    @Operation(summary = "API per recuperare tutte le forze di polizia")
    @GetMapping()
    public ResponseEntity<ResponseDto<List<ForzaPoliziaDto>>> getAllUffici(){

        List<ForzaPoliziaDto> result = forzaPoliziaService.getAllForzePolizia();

        return ResponseEntity.ok(ResponseDto.<List<ForzaPoliziaDto>>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "API per recuperare la forza di polizia dato l'id")
    @GetMapping("/{idForzaPolizia}")
    public ResponseEntity<ResponseDto<ForzaPoliziaDto>> getUfficioById(@PathVariable Integer idForzaPolizia){

        ForzaPoliziaDto result = forzaPoliziaService.getForzaPoliziaById(idForzaPolizia);

        return ResponseEntity.ok(ResponseDto.<ForzaPoliziaDto>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "API per recuperare la lista di forze di polizia con un mapping valido")
    @GetMapping("/mapping")
    public ResponseEntity<ResponseDto<List<ForzaPoliziaDto>>> getAllForzePoliziaConMapping(){

        List<ForzaPoliziaDto> result = forzaPoliziaService.getAllForzePoliziaConMapping();

        return ResponseEntity.ok(ResponseDto.<List<ForzaPoliziaDto>>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }
}
