package map;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.JMapViewerTree;
import org.openstreetmap.gui.jmapviewer.MapMarkerCircle;
import org.openstreetmap.gui.jmapviewer.Style;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;

public class CoordinateMap extends JFrame implements JMapViewerEventListener {

    private final JMapViewerTree treeMap = new JMapViewerTree("");
    private final int defaultZoomLevel = 9;

    public CoordinateMap(double latitude, double longitude) {
        super("Origen Del Sismo");

        setUpMap();

        Coordinate coordinate = new Coordinate(latitude, longitude);

        addMarker(coordinate);

        centerOnCoordinate(coordinate);
    }

    private void setUpMap() {

        this.setSize(800, 800);

        this.setLocationRelativeTo(null);

        map().addJMVListener(this);

        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

    private JMapViewer map() {
        return treeMap.getViewer();
    }

    private void addMarker(Coordinate coordinate) {
        CustomMarker marker = new CustomMarker("", coordinate);
        map().addMapMarker(marker);
    }


    private static class CustomMarker extends MapMarkerCircle {
        public CustomMarker(String name, Coordinate coordinate) {
            super(null, name, coordinate, 10, MapMarker.STYLE.FIXED, new Style(Color.RED, Color.RED, new BasicStroke(1.23f), new Font("Helvetica", Font.BOLD, 25)));
        }
    }

    private void centerOnCoordinate(Coordinate coordinate) {
        map().setDisplayPosition(coordinate, this.defaultZoomLevel);
    }

    public void display() {
        this.setVisible(true);
    }

    @Override
    public void processCommand(JMVCommandEvent command) {}

}
