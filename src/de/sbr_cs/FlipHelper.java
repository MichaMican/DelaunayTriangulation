package de.sbr_cs;

import java.util.List;
import java.util.stream.Collectors;

public class FlipHelper {
    public FlipHelper(){

    }

    public void flipTrianglesNeighbours(TriangleNeighbours triangleNeighbours) throws InvalidPointException {
        if(!triangleNeighbours.isDelaunayConform()){
            List<Point> overLappingPoints = triangleNeighbours.getContactPoints();

            //Filter points for points that are not in overLappingPoints
            Point nonOverlappingT1 = triangleNeighbours.triangle1.getPoints().stream()
                    .filter(point -> !overLappingPoints.contains(point)).collect(Collectors.toList()).get(0);
            Point nonOverlappingT2 = triangleNeighbours.triangle2.getPoints().stream()
                    .filter(point -> !overLappingPoints.contains(point)).collect(Collectors.toList()).get(0);

            try {
                triangleNeighbours.triangle1.replacePoint(overLappingPoints.get(0), nonOverlappingT2);
                triangleNeighbours.triangle2.replacePoint(overLappingPoints.get(1), nonOverlappingT1);
            } catch (InvalidPointException e){
                System.out.println(e.getMessage());
                throw e;
            }

            //Recallculates delaunay conformity
            triangleNeighbours.updateTriangleNeighbours();
        } else {
            System.out.println("Triangle Neighbours are already Delaunay Conform");
        }
    }
}
