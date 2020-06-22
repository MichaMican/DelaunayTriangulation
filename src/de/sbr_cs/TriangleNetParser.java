package de.sbr_cs;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class TriangleNetParser {
    public static List<Triangle> parse(String path) throws CsvValidationException, IOException {
        List<Triangle> triangles = new ArrayList<>();
        List<List<String>> records = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(path));) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
            throw e;
        }

        for (var record : records) {
            triangles.add(new Triangle(Double.parseDouble(record.get(0)), Double.parseDouble(record.get(1)),
                    Double.parseDouble(record.get(2)), Double.parseDouble(record.get(3)),
                    Double.parseDouble(record.get(4)), Double.parseDouble(record.get(5))));
        }

        for (Triangle t1 : triangles){
            for (Triangle t2 : triangles){
                if(!t1.equals(t2) && t1.isNeighbour(t2)){
                    try {
                        t1.addNeighbour(t2);
                    } catch (InvalidTriangleNeighboursException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return triangles;
    }
}
