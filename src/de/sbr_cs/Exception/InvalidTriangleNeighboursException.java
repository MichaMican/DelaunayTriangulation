package de.sbr_cs.Exception;

public class InvalidTriangleNeighboursException extends Exception {
    public InvalidTriangleNeighboursException(String errorMessage) {
        super(errorMessage);
    }
    public InvalidTriangleNeighboursException() {
        super("The triangles aren't neighbours");
    }
}
