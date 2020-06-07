
package charts;

import data.Earthquake;
import java.awt.BorderLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Table on earthquakes limited within a date range.
 * 
 * @author Luis Mariano Ramirez Segura
 */
public class DateRangeTable {
    
    /**
     * Panel that contains the earthquake table.
     */
    private JPanel panel;
    
    public DateRangeTable(ArrayList<Earthquake> earthquakes, LocalDateTime start, LocalDateTime end) {
        
        DefaultTableModel tableModel = getEmptyTableModel();
        
        for (Earthquake earthquake : earthquakes) {
            
            LocalDateTime rawDate = earthquake.getDate();
            
            boolean dateInLowerBound = (start.isEqual(rawDate) || start.isBefore(rawDate));
            boolean dateInUpperBound = (end.isEqual(rawDate) || end.isAfter(rawDate));
            if(dateInLowerBound && dateInUpperBound) {
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
                String date = earthquake.getDate().format(dateFormat);
                String time = earthquake.getDate().format(timeFormat);
                String province = earthquake.getProvince().toString();
                String latitude = String.valueOf(earthquake.getLat());
                String longitude = String.valueOf(earthquake.getLon());
                String depth = String.valueOf(earthquake.getDepth());
                String magnitude = String.valueOf(earthquake.getMagnitude());
                String magnitudeType = earthquake.getMagnitudeType().toString();
                String failureOrigin = earthquake.getOriginFailure().toString();
                String details = earthquake.getDetails().replace(";", ",");
            
                tableModel.addRow(new Object[]{date, time, province, latitude, longitude, 
                              depth, magnitude, magnitudeType, failureOrigin, details}); 
            }
        }
        
        JTable table = new JTable(tableModel);
        
        JScrollPane scrollPane = new JScrollPane(table);
        
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        this.panel = tablePanel;
    }
    
    /**
     * Creates a empty table model with the earthquake data columns.
     * 
     * @return empty earthquake table model
     */
    private DefaultTableModel getEmptyTableModel() {
        Object columns[] = { "Fecha", "Hora", "Provincia", "Latitud", "Longitud", "Profundidad", "Magnitud", "Clasificación", "Origen", "Descripción"};
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columns);
        return tableModel;
    }
    
    /**
     * Returns the filled earthquake table panel.
     * 
     * @return earthquakes table panel
     */
    public JPanel getPanel() {
        return panel;
    }
}
