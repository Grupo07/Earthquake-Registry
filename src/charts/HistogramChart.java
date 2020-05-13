
package charts;


import data.Earthquake;
import data.Province;
import java.util.ArrayList;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.SimpleHistogramBin;
import org.jfree.data.statistics.SimpleHistogramDataset;

public class HistogramChart {
    
    private JPanel panel;
    private Province locationRestriction;
    
    public HistogramChart(ArrayList<Earthquake> earthquakes) {
        this.locationRestriction = null;
        this.panel = getChartPanel(earthquakes);
    }
    
    public HistogramChart(ArrayList<Earthquake> earthquakes, Province location) {
        this.locationRestriction = location;
        this.panel = getChartPanel(earthquakes);
    }
    
    private JPanel getChartPanel(ArrayList<Earthquake> earthquakes) {
        JFreeChart chart = createChart(earthquakes);
        return new ChartPanel(chart);
    }
    
    private JFreeChart createChart(ArrayList<Earthquake> earthquakes) {
        
        SimpleHistogramDataset dataset = createDataSet(earthquakes);    
        
        String title = "Sismos Por Magnitud";
        
        if (this.locationRestriction != null) {
            String location = locationRestriction.toString().toLowerCase();
            title = title + " En " + location.substring(0, 1).toUpperCase() + location.substring(1);
        }
        
        JFreeChart chart = ChartFactory.createHistogram(
                title, 
                "Magnitud", 
                "Frecuencia", 
                dataset, 
                PlotOrientation.VERTICAL,
                false, 
                false, 
                false
        );
        
        chart.getXYPlot().getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        chart.getXYPlot().getDomainAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        return chart;
    }
    
    private SimpleHistogramDataset createDataSet(ArrayList<Earthquake> earthquakes) {
        
        SimpleHistogramDataset dataset = new SimpleHistogramDataset("Histogram");
        
        SimpleHistogramBin[] bins = new SimpleHistogramBin[11]; 
        int[] magnitudeFrecuencies = new int[11];
        
        for (int i = 0; i < 11; i++)
            bins[i] = new SimpleHistogramBin(i, i + 1, true, false);
        
        boolean earthquakeIsValid;
        for (Earthquake earthquake : earthquakes) {
            
            earthquakeIsValid = true;
            
            if (this.locationRestriction != null) {
                Province earthquakeLocation = earthquake.getProvince();
                if (locationRestriction != earthquakeLocation) {
                    earthquakeIsValid = false;
                }
            }
            
            if (earthquakeIsValid) {
                int flooredMagnitude = (int) earthquake.getMagnitude();
                if (flooredMagnitude > 10) {
                    magnitudeFrecuencies[10] += 1;
                } else {
                    magnitudeFrecuencies[flooredMagnitude] += 1;
                }
            }
        };
        
        for (int i = 0; i < 11; i++)
            bins[i].setItemCount(magnitudeFrecuencies[i]);
        
        for (SimpleHistogramBin bin : bins)
            dataset.addBin(bin); 

        return dataset;
    }
    
    public JPanel getPanel() {
        return this.panel;
    }
    
}

