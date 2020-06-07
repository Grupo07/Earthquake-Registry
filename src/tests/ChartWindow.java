
package tests;


import charts.BarChart;
import charts.HistogramChart;
import charts.PieChart;
import data.Data;
import data.Province;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


public class ChartWindow extends JFrame{
    
    private Data data;
    
    public ChartWindow(Data earthquakes) {
        super("Graficos de Sismos");
        this.setSize(800, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.data = earthquakes;
    }
    
    public void showEarthquakesByFaultOrigin() {
        JPanel chartPanel = new PieChart(this.data.getAll()).getPanel();
        this.setContentPane(chartPanel);
    }
    
    public void showEarthquakesPerMonth(int year) {
        JPanel chartPanel = new BarChart(this.data.getAll(), year).getPanel();
        this.setContentPane(chartPanel);
    }
    
    public void showEarthquakesByMagnitudeRanges() {
        JPanel chartPanel = new HistogramChart(this.data.getAll()).getPanel();
        this.setContentPane(chartPanel);
    }
    
    public void showEarthquakesByMagnitudeRanges(Province province) {
        JPanel chartPanel = new HistogramChart(this.data.getAll(), province).getPanel();
        this.setContentPane(chartPanel);
    }
    
    public void display() {
        this.setVisible(true);
    }
    
}
