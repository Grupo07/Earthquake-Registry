
package charts;


import data.Earthquake;
import java.util.ArrayList;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.Dataset;

/**
 * Pie chart of frequencies 
 * of seismic origins of a 
 * list of earthquakes.
 * 
 * @author Luis Mariano Ramirez Segura
 */
public class PieChart extends Chart {
    
    /**
     * Sets the earthquakes to the parent constructor.
     * 
     * @param earthquakes list of unordered earthquakes 
     */
    public PieChart(ArrayList<Earthquake> earthquakes) {
        super(earthquakes);
    }
    
    /**
     * Creates the pie chart.
     * 
     * @return the JFreeChart pie chart
     */
    @Override
    protected JFreeChart createChart() {
        PieDataset dataset = (PieDataset) createDataSet();
        JFreeChart  chart = ChartFactory.createPieChart(
                "Sismos Por Origen", 
                dataset, 
                true, 
                true, 
                false
        );
        return chart;
    }

    /**
     * Creates the chart dataset
     * by counting the total 
     * amount of frequencies
     * per seismic origin.
     * 
     * @return pie chart dataset 
     */
    @Override
    protected Dataset createDataSet() {
        DefaultPieDataset dataset = new DefaultPieDataset( );
        
        for (Earthquake earthquake : super.earthquakes) {
            
            String faultOrigin = earthquake.getOriginFailure().toString().replace("_", " ");
            
            boolean originInDataSet = (dataset.getIndex(faultOrigin) != -1);
            
            if (originInDataSet) {
                int currentAmount = dataset.getValue(faultOrigin).intValue();
                dataset.setValue(faultOrigin, currentAmount + 1);
            } else {
                dataset.setValue(faultOrigin, 1);
            }
            
        }

        return dataset;
    }
    
}
