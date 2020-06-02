package map;


import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.JMapViewerTree;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;

/**
 * Map that shows a marker in the given 
 * latitude and longitude.
 * 
 * @author Luis Mariano Ramirez Segura
 */
public class CoordinateMap extends JFrame implements JMapViewerEventListener {
    
    /**
     * Map container. 
     * Used to set up the map itself and its interaction.
     */
    private final JMapViewerTree treeMap = new JMapViewerTree("");
    
    /**
     * Default level of map zoom
     */
    private final int defaultZoomLevel = 9;

    /**
     * Constructor receives the marker latitude and longitude
     * to setup the map look and location.
     * 
     * @param latitude marker's longitud
     * @param longitude marker's latitude
     * 
     */
    public CoordinateMap(double latitude, double longitude) {
        super("Origen Del Sismo");

        setUpMap();

        Coordinate coordinate = new Coordinate(latitude, longitude);

        addMarker(coordinate);

        centerOnCoordinate(coordinate);
    }
    
    /**
     * Sets up the window and map basic 
     * configurations.
     */
    private void setUpMap() {

        this.setSize(800, 800);

        this.setLocationRelativeTo(null);

        map().addJMVListener(this);

        this.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        JPanel infoPanel = new JPanel();

        this.add(panel, BorderLayout.NORTH);
        this.add(infoPanel, BorderLayout.SOUTH);

        JLabel helpLabel = new JLabel("Use right mouse button to move,\n " + "mouse wheel to zoom.");
        infoPanel.add(helpLabel);

        map().setTileSource(new OsmTileSource.TransportMap());

        map().setZoomContolsVisible(false);

        this.add(treeMap, BorderLayout.CENTER);
    }

    /**
     * Returns the map viewer object out of the treeMap member.
     * @return map viewer
     */
    private JMapViewer map() {
        return treeMap.getViewer();
    }
    
    /**
     * Adds a marker to the map in the given location.
     * 
     * @param coordinate marker location
     */
    private void addMarker(Coordinate coordinate) {
        CustomMarker marker = new CustomMarker("", coordinate);
        map().addMapMarker(marker);
    }
    
    /**
     * Centers the map view on a given coordinate.
     * 
     * @param coordinate coordinate to center to 
     */
    private void centerOnCoordinate(Coordinate coordinate) {
        map().setDisplayPosition(coordinate, this.defaultZoomLevel);
    }

    /**
     * Pops up the map window.
     */
    public void display() {
        this.setVisible(true);
    }

    /**
     * Required implemented method to enable map mouse interaction.
     * @param command user action being performed in the map
     */
    @Override
    public void processCommand(JMVCommandEvent command) {}

}
