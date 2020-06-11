package de.sbr_cs;

import java.util.ArrayList;
import java.util.List;

public class TriangleNeighbours {
    public Triangle triangle1;
    public Triangle triangle2;
    private boolean isDelaunayConform;

    public TriangleNeighbours(Triangle triangle1, Triangle triangle2) throws InvalidTriangleNeighboursException {
        if(!triangle1.isNeighbour(triangle2)){
            throw new InvalidTriangleNeighboursException("The Triangles aren't Neighbours");
        }

        this.triangle1 = triangle1;
        this.triangle2 = triangle2;
        this.isDelaunayConform = callculateDelaunayConformity();
    }

    private boolean callculateDelaunayConformity(){
        return 180 >= (triangle1.getLargestAngle() + triangle2.getLargestAngle());
    }

    //Has to be called if the triangles changes
    public void updateTriangleNeighbours(){
        this.isDelaunayConform = callculateDelaunayConformity();
    }

    public boolean isDelaunayConform() {
        return isDelaunayConform;
    }

    public List<Point> getContactPoints(){

        List<Point> overlappingPoints = new ArrayList<Point>();
        List<Point> neighbourPoints = triangle2.getPoints();

        for (Point p : triangle1.getPoints()) {
            if(neighbourPoints.contains(p)){
                overlappingPoints.add(p);
            }
        }

        return overlappingPoints;
    }
}
