package de.sbr_cs;

public class AlreadyRegisteredNeighboursException extends RuntimeException {
    public AlreadyRegisteredNeighboursException(String errorMessage) {
        super(errorMessage);
    }
    public AlreadyRegisteredNeighboursException() {
        super("The triangle is already registered as neighbour");
    }
}
