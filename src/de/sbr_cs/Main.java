package de.sbr_cs;

import com.opencsv.exceptions.CsvValidationException;
import org.math.plot.Plot2DPanel;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        List<Triangle> triangles = new ArrayList<>();
        try {
            triangles = TriangleNetParser.parse("E:\\GITs\\DelunayTriangulation\\testTriangle.csv");
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
                if (flipWasPerformed) {
                    gui.resetAll();
                    gui.draw(triangles);
                    try {
                        //This Thread sleep causes an RuntimeException in GUI Thread (because the gui thread gets frozen too)
                        //However this thread.sleep is only for visual purposes => I won't fix it
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        } while (flipWasPerformed); //if a flip was performed the net has to be checked again for sideeffected delauney invalidity




        System.out.println("FINISHED");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void demo1() {
        Triangle t1 = new Triangle(new Point(2, 0), new Point(1, 2), new Point(2, 3));
        Triangle t2 = new Triangle(new Point(2, 0), new Point(2, 3), new Point(3, 2));

        GUI gui = new GUI();

        if (t1.isNeighbour(t2)) {
            try {
                Plot2DPanel plot = new Plot2DPanel();

                gui.draw(t1);
                gui.draw(t2);
                gui.draw(t1.getPoints());
                gui.draw(t2.getPoints());

                Thread.sleep(2000);

                System.out.println("Current Delaunay Status: " + t1.isDelaunayConform(t2));

                FlipHelper.flipTrianglesNeighbours(t1, t2);

                System.out.println("Delaunay Status after rotation: " + t1.isDelaunayConform(t2));

                gui.resetAll();

                gui.draw(t1);
                gui.draw(t2);
                gui.draw(t1.getPoints());
                gui.draw(t2.getPoints());

                Thread.sleep(2000);
                System.exit(0);


            } catch (InvalidTriangleNeighboursException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
