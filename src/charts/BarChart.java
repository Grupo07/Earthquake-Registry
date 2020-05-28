
package charts;

import data.Earthquake;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.Month;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;

/**
 * Bar chart of the amount of 
 * earthquakes per month in a year
 * 
 * @author Luis Mariano Ramirez Segura
 */
public class BarChart extends Chart{
    
    private int year;
    
    /**
     * Sets the earthquakes to the parent constructor,
     * sets the year member and calls to create 
     * the chart panel again.
     * 
     * @param earthquakes list of unordered earthquakes 
     * @param year year year to count the earthquakes per month
     */
    public BarChart(ArrayList<Earthquake> earthquakes, int year) {
        super(earthquakes);
        this.year = year;
        super.panel = super.getChartPanel();
    }
    
    /**
     * Creates the bar chart.
     * 
     * @return the JFreeChart bar chart
     */
    @Override
    protected JFreeChart createChart() {
        CategoryDataset dataset = (CategoryDataset) createDataSet();
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
    
    /**
     * Creates the chart dataset
     * by counting the total 
     * amount of earthquakes 
     * per month in the given year
     * 
     * @return bar chart dataset 
     */
    @Override
    protected Dataset createDataSet() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for (Month month : Month.values()) 
            dataset.addValue(0, month.toString(), "");
   
        for (Earthquake earthquake : super.earthquakes) {
            
            LocalDateTime date = earthquake.getDate();
            
            if (date.getYear() == year) {
                String month = date.getMonth().toString();
                int currentAmount = dataset.getValue(month, "").intValue();
                dataset.addValue(currentAmount + 1, month, "");
            }
 
        }

        return dataset;
    }
    
}

