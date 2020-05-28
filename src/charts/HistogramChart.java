
package charts;


import data.Earthquake;
import data.Province;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.Dataset;
import org.jfree.data.statistics.SimpleHistogramBin;
import org.jfree.data.statistics.SimpleHistogramDataset;

/**
 * Histogram of the magnitude ranges 
 * of a list of earthquakes. Can receive
 * a province as restriction or not.
 * 
 * @author Luis Mariano Ramirez Segura
 */
public class HistogramChart extends Chart {
    
    private Province provinceRestriction;
    
    /**
     * Sets the earthquakes to the parent constructor,
     * sets the province restriction to null and calls to create 
     * the chart panel again.
     * 
     * @param earthquakes list of unordered earthquakes 
     */
    public HistogramChart(ArrayList<Earthquake> earthquakes) {
        super(earthquakes);
        this.provinceRestriction = null;
        super.panel = super.getChartPanel();
    }
    
    /**
     * Sets the earthquakes to the parent constructor,
     * sets the province restriction and calls to create 
     * the chart panel again.
     * 
     * @param earthquakes list of unordered earthquakes 
     * @param province province restriction
     */
    public HistogramChart(ArrayList<Earthquake> earthquakes, Province province) {
        super(earthquakes);
        this.provinceRestriction = province;
        super.panel = super.getChartPanel();
    }
    
    /**
     * Creates the histogram.
     * 
     * @return the JFreeChart histogram
     */
    @Override
    protected JFreeChart createChart() {
        
        SimpleHistogramDataset dataset = (SimpleHistogramDataset) createDataSet();    
        
        String title = "Sismos Por Magnitud";
        
        if (this.provinceRestriction != null) {
            String location = provinceRestriction.toString().toLowerCase();
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
    
    /**
     * Creates the chart dataset
     * by counting the total 
     * amount of frequencies
     * in each magnitude range. 
     * The ranges go from 0 to 11, 
     * divided by units of 1.
     * 
     * @return the histogram dataset 
     */
    @Override
    protected Dataset createDataSet() {
        
        SimpleHistogramDataset dataset = new SimpleHistogramDataset("Histogram");
        
        SimpleHistogramBin[] bins = new SimpleHistogramBin[11]; 
        int[] magnitudeFrecuencies = new int[11];
        
        for (int i = 0; i < 11; i++)
            bins[i] = new SimpleHistogramBin(i, i + 1, true, false);
        
        boolean earthquakeIsValid;
        for (Earthquake earthquake : super.earthquakes) {
            
            earthquakeIsValid = true;
            
            if (this.provinceRestriction != null) {
                Province earthquakeLocation = earthquake.getProvince();
                if (provinceRestriction != earthquakeLocation) {
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
    
}

