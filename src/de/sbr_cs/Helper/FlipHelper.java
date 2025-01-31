package de.sbr_cs.Helper;

import de.sbr_cs.Exception.InvalidPointException;
import de.sbr_cs.Exception.InvalidTriangleNeighboursException;
import de.sbr_cs.Point;
import de.sbr_cs.Triangle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class FlipHelper {

    /**
     * Performes a "flip" if the Triangles aren't delauney conform
     * @param t1 Triangle 1
     * @param t2 Triangle 2
     * @return Boolean value if a flip was performed
     * @throws InvalidTriangleNeighboursException Thrown if the two triangles aren't neighbours
     */
    public static boolean flipTrianglesNeighbours(Triangle t1, Triangle t2) throws InvalidTriangleNeighboursException {
        if(!t1.isDelaunayConform(t2)){
            List<Point> overLappingPoints = t1.getContactPoints(t2);
            List<Triangle> allNeighbours = new ArrayList<>();

            allNeighbours.addAll(t1.getNeighbours());
            for (Triangle t: t2.getNeighbours()) {
                if(!allNeighbours.contains(t)){
                    allNeighbours.add(t);
                }
            }

            //Filter points for points that are not in overLappingPoints
            Point nonOverlappingT1 = t1.getPoints().stream()
                    .filter(point -> !overLappingPoints.contains(point)).collect(Collectors.toList()).get(0);
            Point nonOverlappingT2 = t2.getPoints().stream()
                    .filter(point -> !overLappingPoints.contains(point)).collect(Collectors.toList()).get(0);


            try {
                t1.replacePoint(overLappingPoints.get(0), nonOverlappingT2);
                t2.replacePoint(overLappingPoints.get(1), nonOverlappingT1);
            } catch (InvalidPointException e){
                System.out.println(e.getMessage());
            }

            t1.updateNeighbours(allNeighbours);
            t2.updateNeighbours(allNeighbours);

            return true;
        } else {
            return false;
        }
    }
}
