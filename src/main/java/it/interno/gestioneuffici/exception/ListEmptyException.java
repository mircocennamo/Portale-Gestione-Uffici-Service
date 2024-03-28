package it.interno.gestioneuffici.exception;

public class ListEmptyException extends RuntimeException{

    public ListEmptyException(String message) {
        super(message);
    }
    public ListEmptyException() {
        super("La lista non può essere vuota");
    }
}
