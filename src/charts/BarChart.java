
package charts;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.Month;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;


public class BarChart {
    
    private JPanel panel;
    private int year;
    
    public BarChart(ArrayList<Earthquake> earthquakes, int year) {
        this.year = year;
        this.panel = getChartPanel(earthquakes);
    }
    
    private JPanel getChartPanel(ArrayList<Earthquake> earthquakes) {
        JFreeChart chart = createChart(earthquakes);
        return new ChartPanel(chart);
    }
    
    private JFreeChart createChart(ArrayList<Earthquake> earthquakes) {
        CategoryDataset dataset = createDataSet(earthquakes);
        JFreeChart chart = ChartFactory.createBarChart(
                "Sismos en " + String.valueOf(year), 
                "", 
                "", 
                dataset, 
                PlotOrientation.VERTICAL,
                true, 
                true, 
                false
        );
        ((CategoryPlot)chart.getPlot()).getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        return chart;
    }
    
    private CategoryDataset createDataSet(ArrayList<Earthquake> earthquakes) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for (Month month : Month.values()) 
            dataset.addValue(0, month.toString(), "");
   
        for (Earthquake earthquake : earthquakes) {
            
            LocalDateTime date = earthquake.getDate();
            
            if (date.getYear() == year) {
                String month = date.getMonth().toString();
                int currentAmount = dataset.getValue(month, "").intValue();
                dataset.addValue(currentAmount + 1, month, "");
            }
 
        }

        return dataset;
    }
    
    public JPanel getPanel() {
        return this.panel;
    }
    
}

