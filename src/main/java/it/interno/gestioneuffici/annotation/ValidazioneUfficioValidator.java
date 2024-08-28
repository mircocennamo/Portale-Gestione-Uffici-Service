package it.interno.gestioneuffici.annotation;

import it.interno.gestioneuffici.dto.UfficioDto;
import org.apache.commons.lang3.StringUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

public class ValidazioneUfficioValidator implements ConstraintValidator<ValidazioneUfficio, UfficioDto> {



    @Override
    public void initialize(ValidazioneUfficio constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UfficioDto ufficioDto, ConstraintValidatorContext constraintValidatorContext) {
        if(!validateAddress(ufficioDto)){
               return false;
           }
           // Coordinate
        else if(ufficioDto.isCoordinate()
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

    private Boolean validateAddress(UfficioDto ufficioDto){
        // Indirizzo Normalizzato
        if(ufficioDto.getIndirizzoNormalizzato() != null &&  ("S").equals(ufficioDto.getIndirizzoNormalizzato())
                &&  ufficioDto.getStrada()!=null &&  !StringUtils.isBlank(ufficioDto.getStrada()) && ufficioDto.isCoordinate())
            return true;
        else if( (ufficioDto.getIndirizzoNormalizzato() == null ||  ("N").equals(ufficioDto.getIndirizzoNormalizzato()))
                &&  ufficioDto.getStrada()!=null &&  !StringUtils.isBlank(ufficioDto.getStrada()) && !ufficioDto.isCoordinate())
            return true;

        else if( (ufficioDto.getIndirizzoNormalizzato() == null ||  ("N").equals(ufficioDto.getIndirizzoNormalizzato()))
                &&  ufficioDto.getStrada()==null &&  StringUtils.isBlank(ufficioDto.getStrada()) && ufficioDto.isCoordinate())
            return true;

        return false;
    }

}
