package tests;

import charts.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

public class Charts {
    
    public static void main(String[] args) {
        //testPieChart();
        //testBarChart(2002);
        //testHistogram();
        testHistogram(Province.ALAJUELA);
    }
    
    private static void testPieChart() {
        ChartWindow chartWindow = new ChartWindow(createEarthquakes());
        chartWindow.showEarthquakesByFaultOrigin();
        chartWindow.display();
    }
    
    private static void testBarChart(int year) {
        ChartWindow chartWindow = new ChartWindow(createEarthquakes());
        chartWindow.showEarthquakesPerMonth(year);
        chartWindow.display();
    }
    
    private static void testHistogram() {
        ChartWindow chartWindow = new ChartWindow(createEarthquakes());
        chartWindow.showEarthquakesByMagnitudeRanges();
        chartWindow.display();
    }
    
    public static void testHistogram(Province province) {
        ChartWindow chartWindow = new ChartWindow(createEarthquakes());
        chartWindow.showEarthquakesByMagnitudeRanges(province);
        chartWindow.display();
    }
    
    private static ArrayList<Earthquake> createEarthquakes() { 
        ArrayList<Earthquake> earthquakes = new ArrayList<Earthquake>();
        earthquakes.add(new Earthquake(LocalDateTime.of(2002, Month.JANUARY, 12, 22, 30, 10), 1.5f, Province.ALAJUELA, FaultOrigin.CHOQUE_DE_PLACAS));
        earthquakes.add(new Earthquake(LocalDateTime.of(2002, Month.JANUARY, 13, 22, 30, 10), 1.7f, Province.ALAJUELA, FaultOrigin.CHOQUE_DE_PLACAS));
        earthquakes.add(new Earthquake(LocalDateTime.of(2002, Month.FEBRUARY, 13, 22, 30, 10), 1.7f, Province.ALAJUELA, FaultOrigin.CHOQUE_DE_PLACAS));
        earthquakes.add(new Earthquake(LocalDateTime.of(2002, Month.FEBRUARY, 20, 22, 30, 10), 3.2f, Province.ALAJUELA, FaultOrigin.DEFORMACION_INTERNA));
        earthquakes.add(new Earthquake(LocalDateTime.of(2002, Month.FEBRUARY, 21, 22, 30, 10), 4.2f, Province.ALAJUELA, FaultOrigin.DEFORMACION_INTERNA));
        earthquakes.add(new Earthquake(LocalDateTime.of(2002, Month.JULY, 29, 22, 30, 10), 4.2f, Province.ALAJUELA, FaultOrigin.FALLAMIENTO_LOCAL));
        earthquakes.add(new Earthquake(LocalDateTime.of(2002, Month.OCTOBER, 26, 22, 30, 10), 4.3f, Province.CARTAGO, FaultOrigin.SUBDUCCION_DE_PLACA));
        earthquakes.add(new Earthquake(LocalDateTime.of(2002, Month.DECEMBER, 26, 22, 30, 10), 4.5f, Province.CARTAGO, FaultOrigin.TECTONICO_POR_FALLA_LOCAL));
        earthquakes.add(new Earthquake(LocalDateTime.of(2002, Month.DECEMBER, 26, 22, 30, 10), 8.5f, Province.HEREDIA, FaultOrigin.TECTONICO_POR_SUBDUCCION));
        return earthquakes;
    }
    
}
