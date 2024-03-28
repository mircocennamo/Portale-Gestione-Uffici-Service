package it.interno.gestioneuffici.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.interno.gestioneuffici.dto.ResponseDto;
import it.interno.gestioneuffici.dto.divitech.IndirizzoNormalizzatoDto;
import it.interno.gestioneuffici.utils.NormalizzatoreUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/normalizzatore", produces = {MediaType.APPLICATION_JSON_VALUE})
@Tag(name = "Normalizzatore")
@Validated
public class NormalizzatoreController {

    @Operation(summary = "API per normalizzare l'indirizzo")
    @GetMapping
    @Validated
    public ResponseEntity<ResponseDto<List<IndirizzoNormalizzatoDto>>> normalizzaIndirizzo(@RequestParam @NotBlank(message = "Il comune deve essere valorizzato") String comune,
                                                                   @RequestParam @NotBlank(message = "L'indirizzo deve essere valorizzato") String indirizzo) throws IOException {

        List<IndirizzoNormalizzatoDto> result = NormalizzatoreUtils.callDivitech(comune, indirizzo);

        return ResponseEntity.ok(ResponseDto.<List<IndirizzoNormalizzatoDto>>builder()
                .code(HttpStatus.OK.value())
                .body(result)
                .build());
    }

}
