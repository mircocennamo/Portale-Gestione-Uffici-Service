package it.interno.gestioneuffici.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.interno.gestioneuffici.dto.LuogoDto;
import it.interno.gestioneuffici.dto.ResponseDto;
import it.interno.gestioneuffici.service.LuogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/luogo", produces = {MediaType.APPLICATION_JSON_VALUE})
@Tag(name = "Luogo")
public class LuogoController {

    @Autowired
    private LuogoService luogoService;

    @Operation(summary = "API per recuperare i luoghi tramite descrizione like e inLuogo")
    @GetMapping("/by-inluogo")
    public ResponseEntity<ResponseDto<List<LuogoDto>>> findAllByDescrizioneLike(@RequestParam(required = false) String descrizione,
                                                       @DateTimeFormat(pattern = "dd/MM/yyyy") @RequestParam LocalDate dataRiferimento,
                                                       @RequestParam String inLuogo){

        List<LuogoDto> result = luogoService.findAllByDescrizioneLike(descrizione, dataRiferimento, inLuogo);

        return ResponseEntity.ok(ResponseDto.<List<LuogoDto>>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "API per recuperare i luoghi tramite descrizione like, inLuogo e provincia")
    @GetMapping("/by-provincia/{descrizione}")
    public ResponseEntity<ResponseDto<List<LuogoDto>>> findAllByDescrizioneLikeAndProvincia(@PathVariable String descrizione,
                                                        @DateTimeFormat(pattern = "dd/MM/yyyy") @RequestParam LocalDate dataRiferimento,
                                                        @RequestParam String inLuogo,
                                                        @RequestParam String provincia){

        List<LuogoDto> result = luogoService.findAllByDescrizioneLikeAndProvincia(descrizione, dataRiferimento, inLuogo, provincia);

        return ResponseEntity.ok(ResponseDto.<List<LuogoDto>>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @Operation(summary = "API per recuperare i luoghi tramite descrizione like, inLuogo e data validita e filtro geografico superiore")
    @GetMapping("/filtro")
    public ResponseEntity<ResponseDto<List<LuogoDto>>> getByDescrizioneInLuogoAndFiltroGeografico(@RequestParam(required = false) String descrizioneLuogo,
                                                              @RequestParam String inLuogo,
                                                              @DateTimeFormat(pattern = "dd/MM/yyyy") @RequestParam(required = false) LocalDate dataRif,
                                                              @RequestParam(required = false) List<String> filtroGeografico){

        return ResponseEntity.ok(ResponseDto.<List<LuogoDto>>builder()
                .code(HttpStatus.OK.value())
                .body(luogoService.getByDescrizioneInLuogoAndFiltroGeografico(descrizioneLuogo, inLuogo, dataRif, filtroGeografico))
                .build());
    }

}
