package it.interno.gestioneuffici.exception;

public class UfficioNotFoundException extends RuntimeException{

    public UfficioNotFoundException(String message) {
        super(message);
    }
}
