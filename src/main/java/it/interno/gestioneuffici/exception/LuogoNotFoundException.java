package it.interno.gestioneuffici.exception;

public class LuogoNotFoundException extends RuntimeException{
    public LuogoNotFoundException(String message) {
        super(message);
    }
}
