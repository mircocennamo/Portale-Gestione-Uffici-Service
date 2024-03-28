package it.interno.gestioneuffici.annotation;

import it.interno.gestioneuffici.dto.UfficioDto;
import org.apache.commons.lang3.StringUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidazioneUfficioValidator implements ConstraintValidator<ValidazioneUfficio, UfficioDto> {

    @Override
    public void initialize(ValidazioneUfficio constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UfficioDto ufficioDto, ConstraintValidatorContext constraintValidatorContext) {

        // Indirizzo Normalizzato
        if(ufficioDto.getIndirizzoNormalizzato().equals("S")
                && (!ufficioDto.isCoordinate() || StringUtils.isBlank(ufficioDto.getStrada())))
            return false;

        // Coordinate
        if(ufficioDto.isCoordinate()
                && (ufficioDto.getCoordinataX() == null || ufficioDto.getCoordinataY() == null))
            return false;

        return !StringUtils.isBlank(ufficioDto.getCodiceUfficio())
                && !StringUtils.isBlank(ufficioDto.getDescrizioneUfficio())
                && ufficioDto.getDataInizio() != null
                && ufficioDto.getCategoriaUfficio() != null
                && ufficioDto.getCategoriaUfficio().getCodiceCategoriaUfficio() != null
                && ufficioDto.getCategoriaUfficio().getForzaPolizia() != null
                && ufficioDto.getCategoriaUfficio().getForzaPolizia().getIdGruppo() != null
                && (ufficioDto.getStatistichePersonale() == null || ufficioDto.getNumeroGiorniControllo() != null)
                && ufficioDto.getLuogoUfficio() != null
                && ufficioDto.getLuogoUfficio().getCodiceLuogo() != null;
    }

}
