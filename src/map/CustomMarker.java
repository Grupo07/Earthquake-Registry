
package map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapMarkerCircle;
import org.openstreetmap.gui.jmapviewer.Style;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

/**
 * Custom red dot marker. 
 * For map display usage.
 * 
 * @author Luis Mariano Ramirez Segura
 */
public class CustomMarker extends MapMarkerCircle {
    
        /**
         * This constructor calls it's parent constructor with 
         * custom arguments to give the marker it's unique look.   
         * 
         * @param name marker's name
         * @param coordinate marker's coordinate
         */
        public CustomMarker(String name, Coordinate coordinate) {
            super(null, name, coordinate, 10, MapMarker.STYLE.FIXED, new Style(Color.RED, Color.RED, new BasicStroke(1.23f), new Font("Helvetica", Font.BOLD, 25)));
        }
}
