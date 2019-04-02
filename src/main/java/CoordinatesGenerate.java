package main.java;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ThreadLocalRandom;

public class CoordinatesGenerate {
    private static Integer no_of_requests = 1000;
    private static String file_path = "coordinates.csv";
    private static Double lat_lower_bound = -15.0, lat_upper_bound = 20.0;
    private static Double lon_lower_bound = 80.0,lon_upper_bound = 120.0;
    public static void main(String[] args) {
        String value="";
        //range-geo-bunds
        /*for(int i=0;i<20000;i++) {
            Double max_lat = -0.513 + ThreadLocalRandom.current().nextDouble(0, 4);
            Double min_lon = 98.66285305514746 + ThreadLocalRandom.current().nextDouble(0, 7);
            Double min_lat = -8.35832242320117376 + ThreadLocalRandom.current().nextDouble(0, 4);
            Double max_lon = 113.68352688327246 - ThreadLocalRandom.current().nextDouble(0, 4);
            value = value + String.valueOf(min_lat) + "," + String.valueOf(min_lon) + "," +String.valueOf(max_lat) + "," +String.valueOf(max_lon) + "\n";
        }*/
        //Lat Long For SEA region only
        for(int i=0;i<no_of_requests;i++) {
            Double lat = lat_lower_bound + ThreadLocalRandom.current().nextDouble(0, lat_upper_bound-lat_lower_bound);
            Double lon = lon_lower_bound + ThreadLocalRandom.current().nextDouble(0, lon_upper_bound-lon_lower_bound);
            value = value + String.valueOf(lat) + "," + String.valueOf(lon) + "\n";
        }
        writeUsingOutputStream(value);
    }

    private static void writeUsingOutputStream(String data) {
        OutputStream os = null;
        try {
            File outputFile = new File(file_path);
            os = new FileOutputStream(outputFile);
            os.write(data.getBytes(), 0, data.length());
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
