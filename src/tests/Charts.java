package tests;

import data.Province;
import charts.*;
import data.Data;

public class Charts {
    
    public static void main(String[] args) {
        
        //testPieChart();
        testBarChart(2005);
        //testHistogram();
        //testHistogram(Province.CARTAGO);
    }
    
    private static void testPieChart() {
        ChartWindow chartWindow = new ChartWindow(new Data());
        chartWindow.showEarthquakesByFaultOrigin();
        chartWindow.display();
    }
    
    private static void testBarChart(int year) {
        Data data = new Data();
        ChartWindow chartWindow = new ChartWindow(new Data());
        chartWindow.showEarthquakesPerMonth(year);
        chartWindow.display();
    }
    
    private static void testHistogram() {
        Data data = new Data();
        ChartWindow chartWindow = new ChartWindow(new Data());
        chartWindow.showEarthquakesByMagnitudeRanges();
        chartWindow.display();
    }
    
    public static void testHistogram(Province province) {
        ChartWindow chartWindow = new ChartWindow(new Data());
        chartWindow.showEarthquakesByMagnitudeRanges(province);
        chartWindow.display();
    }
    
}
