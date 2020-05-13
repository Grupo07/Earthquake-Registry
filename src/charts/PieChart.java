
package charts;


import data.Earthquake;
import java.util.ArrayList;
import javax.swing.JPanel;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;


public class PieChart {
    
    private JPanel panel;
    
    public PieChart(ArrayList<Earthquake> earthquakes) {
        this.panel = getChartPanel(earthquakes);
    }
    
    private JPanel getChartPanel(ArrayList<Earthquake> earthquakes) {
        JFreeChart chart = createChart(earthquakes);
        return new ChartPanel(chart);
    }
    
    private JFreeChart createChart(ArrayList<Earthquake> earthquakes) {
        PieDataset dataset = createDataSet(earthquakes);
        JFreeChart  chart = ChartFactory.createPieChart(
                "Sismos Por Origen", 
                dataset, 
                true, 
                true, 
                false
        );
        return chart;
    }
    
    private PieDataset createDataSet(ArrayList<Earthquake> earthquakes) {
        
        DefaultPieDataset dataset = new DefaultPieDataset( );
        
        for (Earthquake earthquake : earthquakes) {
            
            String faultOrigin = earthquake.getFaultOrigin().toString().replace("_", " ");
            
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
    
    public JPanel getPanel() {
        return this.panel;
    }
    
}
