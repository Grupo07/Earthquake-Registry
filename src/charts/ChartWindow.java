
package charts;


import data.Earthquake;
import data.Province;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


public class ChartWindow extends JFrame{
    
    private ArrayList<Earthquake> earthquakes;
    
    public ChartWindow(ArrayList<Earthquake> earthquakes) {
        super("Graficos de Sismos");
        this.setSize(800, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.earthquakes = earthquakes;
    }
    
    public void showEarthquakesByFaultOrigin() {
        JPanel chartPanel = new PieChart(this.earthquakes).getPanel();
        this.setContentPane(chartPanel);
    }
    
    public void showEarthquakesPerMonth(int year) {
        JPanel chartPanel = new BarChart(this.earthquakes, year).getPanel();
        this.setContentPane(chartPanel);
    }
    
    public void showEarthquakesByMagnitudeRanges() {
        JPanel chartPanel = new HistogramChart(this.earthquakes).getPanel();
        this.setContentPane(chartPanel);
    }
    
    public void showEarthquakesByMagnitudeRanges(Province province) {
        JPanel chartPanel = new HistogramChart(this.earthquakes, province).getPanel();
        this.setContentPane(chartPanel);
    }
    
    public void display() {
        this.setVisible(true);
    }
    
}
