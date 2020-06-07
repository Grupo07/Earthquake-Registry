
package data;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.time.LocalDateTime;
import java.util.Random;

/**
 *In charge of manipulating the information of all the earthquakes registered in the system.
 * 
 * @author Esteban Guzmán R.
 */
public class Data {

    private ArrayList<Earthquake> data = new ArrayList<Earthquake>();

    public Data() {
        readFile();
    }

    /**
     * Create and add a earthquake. Sort the list of Earthquake by date and save
     * the cvs file.
     *
     * @param province earthquake's province
     * @param date earthquake's date
     * @param depth depth in KM
     * @param lat latitude
     * @param lon longitud
     * @param originFailure earthquake's seismic origin
     * @param details earthquake's details
     * @param magnitude earthquake's magnitude
     * @throws IOException csv file reading exception
     */
    public void addEarthquake(Province province, LocalDateTime date,
            float depth, double lat, double lon,
            FaultOrigin originFailure, String details, float magnitude) 
            throws IOException {
        int id = generateId();
        Earthquake newData = new Earthquake(id, province, date, depth, lat,
                lon, originFailure, details, magnitude);
        data.add(newData);
        saveFile();
    }

    /**
     * Search an Earthquake by id
     *
     * @param id id of earthquake
     * @return earthquake when found it and null when not
     */
    public Earthquake getEarthquake(int id) {
        int index = 0;
        while (index < data.size()) {
            if (data.get(index).getId() == id) {
                return data.get(index);
            }
            index++;
        }
        System.out.println("El sismo id:" + id + " no fue encontrado");
        return null;
    }

    /**
     * Number of earthquakes registered
     *
     * @return Number of earthquakes registered
     */
    public int size() {
        return data.size();
    }

    public ArrayList<Earthquake> getAll() {
        return data;
    }

    /**
     * Update a earthquake and save csv file
     *
     * @param id id of earthquake to update
     * @param newData new earthquake update
     * @throws IOException csv file reading exception
     */
    public void updateEarthquake(int id, Earthquake newData) throws IOException {
        data.set(getIndexEarthquake(id), newData);
        saveFile();
    }

    /**
     * Delete earthquake and save csv file
     *
     * @param id id of earthquake to delete
     * @throws IOException csv file reading exception
     */
    public void deleteEarthquake(int id) throws IOException {
        data.remove(getIndexEarthquake(id));
        saveFile();
    }

    public void deleteAll() throws IOException {
        data = new ArrayList<Earthquake>();
        saveFile();
    }

    private int getIndexEarthquake(int id) {
        int index = 0;
        while (index < data.size()) {
            if (data.get(index).getId() == id) {
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     * Generate a random unique id for new earthquakes
     *
     * @return random unique id
     */
    private int generateId() {
        Random random = new Random();
        int newId = random.nextInt(Integer.MAX_VALUE);
        while (getEarthquake(newId) != null) {
            System.out.println("Id repetido:" + newId);
            newId = random.nextInt(Integer.MAX_VALUE);
        }
        return newId;
    }

    public String toString() {
        String result = "Data:\n[\n";
        for (int i = 0; i < data.size(); i++) {
            result += data.get(i).toString() + "\n";
        }
        return result + "]";
    }

    public ArrayList<String[]> toArrayString() {
        ArrayList<String[]> result = new ArrayList<String[]>();
        result.add(new String[]{"Id", "Province", "Date", "Depth", "Lat", 
            "Lon", "OriginFailure", "Details", "Magnitude", "MagnitudeType"});
        for (int i = 0; i < data.size(); i++) {
            result.add(data.get(i).toArrayString());
        }
        return result;
    }

    /**
     * Save the ArrayList of earthquakes in Data.csv
     *
     * @throws IOException csv file reading exception
     */
    private void saveFile() throws IOException {
        sortByDate();
        CsvWriter csvWriter = new CsvWriter("Data.csv");
        ArrayList<String[]> toSave = toArrayString();
        for (String[] fila : toSave) {
            csvWriter.writeRecord(fila);
        }
        csvWriter.close();
    }

    /**
     * Read Data.csv and charge the ArrayList of earthquakes
     */
    private void readFile() {
        try {
            data = new ArrayList<Earthquake>();
            CsvReader reader = new CsvReader("Data.csv");
            reader.readHeaders();
            while (reader.readRecord()) {
                int id = Integer.parseInt(reader.get("Id"));
                Province province = Province.valueOf(reader.get("Province"));
                LocalDateTime date = LocalDateTime.parse(reader.get("Date"));
                Float depth = Float.parseFloat(reader.get("Depth"));
                Double lat = Double.parseDouble(reader.get("Lat"));
                Double lon = Double.parseDouble(reader.get("Lon"));
                FaultOrigin originFailure = 
                        FaultOrigin.valueOf(reader.get("OriginFailure"));
                String details = reader.get("Details");
                Float magnitude = Float.parseFloat(reader.get("Magnitude"));
                MagnitudeType magnitudeType = 
                        MagnitudeType.valueOf(reader.get("MagnitudeType"));
                Earthquake newData = new Earthquake(id, province, date, depth, 
                        lat, lon, originFailure, details, magnitude, 
                        magnitudeType);
                data.add(newData);
            }

            reader.close();

        } catch (FileNotFoundException e) {
            System.out.println("Debería estar generando datos aleatorios");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A random selected enumeration.
     *
     * @param <T> enumeration class type
     * @param clazz enumeration class
     * @return random Enumeration selected
     */
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        Random random = new Random();
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    /**
     * Sort earthquakes registered by date
     */
    private void sortByDate() {
        Collections.sort(data, new Comparator<Earthquake>() {
            @Override
            public int compare(Earthquake earthquake1, Earthquake earthquake2) {
                return -earthquake1.getDate().compareTo(earthquake2.getDate());
            }
        });
    }
}
