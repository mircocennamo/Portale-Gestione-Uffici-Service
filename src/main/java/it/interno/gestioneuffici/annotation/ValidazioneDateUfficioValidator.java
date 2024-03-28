package it.interno.gestioneuffici.annotation;

import it.interno.gestioneuffici.dto.UfficioDto;
import it.interno.gestioneuffici.entity.CategoriaUfficio;
import it.interno.gestioneuffici.entity.Luogo;
import it.interno.gestioneuffici.exception.CategoriaUfficioNotFoundException;
import it.interno.gestioneuffici.exception.LuogoNotFoundException;
import it.interno.gestioneuffici.repository.CategoriaUfficioRepository;
import it.interno.gestioneuffici.repository.LuogoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidazioneDateUfficioValidator implements ConstraintValidator<ValidazioneDateUfficio, UfficioDto> {

    @Autowired
    private CategoriaUfficioRepository categoriaUfficioRepository;
    @Autowired
    private LuogoRepository luogoRepository;

    @Override
    public void initialize(ValidazioneDateUfficio constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UfficioDto ufficioDto, ConstraintValidatorContext context) {

        CategoriaUfficio categoria = categoriaUfficioRepository.getById(ufficioDto.getCategoriaUfficio().getCodiceCategoriaUfficio(), ufficioDto.getCategoriaUfficio().getForzaPolizia().getIdGruppo());
        Luogo luogo = luogoRepository.getById(ufficioDto.getLuogoUfficio().getCodiceLuogo(), ufficioDto.getDataInizio());

        if(luogo == null)
            throw new LuogoNotFoundException("Il luogo con codice " + ufficioDto.getLuogoUfficio().getCodiceLuogo() + " non è presente.");

        if(categoria == null)
            throw new CategoriaUfficioNotFoundException("La categoria con codice " + ufficioDto.getCategoriaUfficio().getCodiceCategoriaUfficio() + " non è presente.");

        // Validazione tra date ufficio
        if(ufficioDto.getDataFine() != null && ufficioDto.getDataInizio().isAfter(ufficioDto.getDataFine())){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("La data inizio validita deve essere minore o uguale alla data fine validita").addConstraintViolation();
            return false;
        }

        // Controllo obbligatorietà data fine validita
        if(categoria.getDataFine() != null && ufficioDto.getDataFine() == null){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("La categoria ha data fine validita " + categoria.getDataFine() + ", inserirne una per l'ufficio antecedente").addConstraintViolation();
            return false;
        }

        // Validazione con luogo
        if(ufficioDto.getDataInizio().isBefore(luogo.getDataInizioValidita())
                || (luogo.getDataFineValidita() != null && ufficioDto.getDataInizio().isAfter(luogo.getDataFineValidita()))
                || (ufficioDto.getDataFine() != null && ufficioDto.getDataFine().isBefore(luogo.getDataInizioValidita()))
                || (ufficioDto.getDataFine() != null && luogo.getDataFineValidita() != null && ufficioDto.getDataFine().isAfter(luogo.getDataFineValidita()))){

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Le date validità devono essere comprese tra quelle del comune: " + luogo.getDataInizioValidita() + " e " + (luogo.getDataFineValidita() == null ? "2999-12-31" : luogo.getDataFineValidita())).addConstraintViolation();
            return false;
        }

        // Validazione con categoria
        if(ufficioDto.getDataInizio().isBefore(categoria.getDataInizio())
                || (categoria.getDataFine() != null && ufficioDto.getDataInizio().isAfter(categoria.getDataFine()))
                || (ufficioDto.getDataFine() != null && categoria.getDataFine() != null && ufficioDto.getDataFine().isAfter(categoria.getDataFine()))){

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Le date validità devono essere comprese tra quelle della categoria ufficio: " + categoria.getDataInizio() + " e " + (categoria.getDataFine() == null ? "2999-12-31" : categoria.getDataFine())).addConstraintViolation();
            return false;
        }

        return true;
    }
}
