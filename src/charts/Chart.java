
package charts;

import data.Earthquake;
import java.util.ArrayList;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.Dataset;

/**
 * Representation of a generic JFreeChart. Which goal
 * is to be instantiated with data and to return
 * a JPanel to be displayed.
 * 
 * @author Luis Mariano Ramirez Segura
 * 
 */
public abstract class Chart {
    
    /**
     * Panel that contains the 
     * created chart.
     */
    protected JPanel panel;

    /**
     * List of earthquakes that contain
     * the data processed to make the
     * chart.
     */
    protected ArrayList<Earthquake> earthquakes; 
    
    /**
     * The constructor sets the earthquakes variable 
     * to make the chart and calls to create it's
     * panel.
     * 
     * @param earthquakes list of unordered earthquakes 
     */
    public Chart(ArrayList<Earthquake> earthquakes) {
        this.earthquakes = earthquakes;
        this.panel = getChartPanel();
    }
    
    /**
     * Asks for the chart and creates and embedded
     * chart panel.
     * 
     * @return embedded chart panel
     */
    protected JPanel getChartPanel() {
        JFreeChart chart = createChart();
        return new ChartPanel(chart);
    }
    
    protected abstract JFreeChart createChart();
    
    protected abstract Dataset createDataSet();
    
    /**
     * Returns the chart panel.
     * 
     * @return chart panel
     */
    public JPanel getPanel() {
        return this.panel;
    }
    
}
