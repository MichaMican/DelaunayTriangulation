package de.sbr_cs;

public class InvalidTriangleNeighboursException extends Exception {
    public InvalidTriangleNeighboursException(String errorMessage) {
        super(errorMessage);
    }
    public InvalidTriangleNeighboursException() {
        super("The triangles aren't neighbours");
    }
}
