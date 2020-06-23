package de.sbr_cs;

import com.opencsv.exceptions.CsvValidationException;
import de.sbr_cs.Helper.TriangleNetParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        List<Triangle> triangles = new ArrayList<>();
        try {
            triangles = TriangleNetParser.parseCSV("E:\\GITs\\DelunayTriangulation\\testTriangle.csv");
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }

        GUI gui = new GUI();

        gui.resetAll();
        gui.draw(triangles);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        boolean flipWasPerformed;
        do {
            flipWasPerformed = false;
            for (Triangle t : triangles) {
                flipWasPerformed |= t.attainDelauneyForNeighbours();
            }


        } while (flipWasPerformed); //if a flip was performed the net has to be checked again for side effected delauney invalidity

        gui.resetAll();
        gui.draw(triangles);

        System.out.println("FINISHED");
    }
}
