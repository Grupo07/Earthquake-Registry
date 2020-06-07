package data;

import java.time.LocalDateTime;

/**
 * Records the information of an earthquake.
 *
 * @author Esteban Guzmán R.
 */
public class Earthquake {

    private int id;
    private Province province;
    private LocalDateTime date;
    private float depth;
    private double lat;
    private double lon;
    private FaultOrigin originFailure;
    private String details;
    private float magnitude;
    private MagnitudeType magnitudeType;

    public Earthquake() {
    }

    public Earthquake(int id, Province province, LocalDateTime date,
            float depth, double lat, double lon, FaultOrigin originFailure,
            String details, float magnitude, MagnitudeType magnitudeType) {
        this.id = id;
        this.province = province;
        this.date = date;
        this.depth = depth;
        this.lat = lat;
        this.lon = lon;
        this.originFailure = originFailure;
        this.details = details;
        this.magnitude = magnitude;
        this.magnitudeType = magnitudeType;
    }

    public Earthquake(int id, Province province, LocalDateTime date,
            float depth, double lat, double lon, FaultOrigin originFailure,
            String details, float magnitude) {
        this.id = id;
        this.province = province;
        this.date = date;
        this.depth = depth;
        this.lat = lat;
        this.lon = lon;
        this.originFailure = originFailure;
        this.details = details;
        this.magnitude = magnitude;
        detectMagnitudeType();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public float getDepth() {
        return depth;
    }

    public void setDepth(float depth) {
        this.depth = depth;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public FaultOrigin getOriginFailure() {
        return originFailure;
    }

    public void setOriginFailure(FaultOrigin originFailure) {
        this.originFailure = originFailure;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public float getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(float magnitude) {
        this.magnitude = magnitude;
    }

    public MagnitudeType getMagnitudeType() {
        return magnitudeType;
    }

    public void setMagnitudeType(MagnitudeType magnitudeType) {
        this.magnitudeType = magnitudeType;
    }

    /**
     * When creating an earthquake without magnitude type, it detects the
     * magnitude type.
     */
    private void detectMagnitudeType() {

        if (magnitude < 2) {
            magnitudeType = MagnitudeType.MICRO;
        } else if (magnitude >= 2 && magnitude < 4) {
            magnitudeType = MagnitudeType.MENOR;
        } else if (magnitude >= 4 && magnitude < 5) {
            magnitudeType = MagnitudeType.LIGERO;
        } else if (magnitude >= 5 && magnitude < 6) {
            magnitudeType = MagnitudeType.MODERADO;
        } else if (magnitude >= 6 && magnitude < 7) {
            magnitudeType = MagnitudeType.FUERTE;
        } else if (magnitude >= 7 && magnitude < 8) {
            magnitudeType = MagnitudeType.MAYOR;
        } else if (magnitude >= 8 && magnitude < 10) {
            magnitudeType = MagnitudeType.GRAN;
        } else if (magnitude > 10) {
            magnitudeType = MagnitudeType.ÉPICO;
        } else {
            System.out.println("Valor de magnitud no valido:" + magnitude);
        }
    }

    public String toString() {
        return "EarthquakeData{" + "id=" + id + ", province=" + province
                + ", date=" + date + ", depth=" + depth + ", lat=" + lat
                + ", lon=" + lon + ", originFailure=" + originFailure
                + ", details=" + details + ", magnitude=" + magnitude
                + ", magnitudeType=" + magnitudeType + '}';
    }

    public String[] toArrayString() {
        String id = Integer.toString(this.id);
        String date = this.date.toString();
        String depth = Float.toString(this.depth);
        String lat = Double.toString(this.lat);
        String lon = Double.toString(this.lon);
        String magnitude = Float.toString(this.magnitude);
        String[] result = {id, province.name(), date, depth, lat, lon, 
            originFailure.name(), details, magnitude, magnitudeType.name()};
        return result;
    }

}
