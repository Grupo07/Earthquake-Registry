/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

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
 *
 * @author esteb
 */
public class Data {
    private ArrayList<EarthquakeData> data = new ArrayList<EarthquakeData>();
    
    public Data(){ readFile(); }
    
    public void addEarthquake(ProvinceType province, LocalDateTime date, 
            float depth, double lat, double lon, 
            OriginFailureType originFailure, String details, float magnitude) throws IOException{
        int id = generateId();
        EarthquakeData newData = new EarthquakeData(id,province,date,depth,lat,
                lon,originFailure,details,magnitude);
        data.add(newData);
        saveFile();
    }
    
    public EarthquakeData getEarthquake(int id){
        int index = 0;
        while (index < data.size()){
            if(data.get(index).getId() == id){
                return data.get(index);
            }
            index++;
        }
        return null;
    }
    
    public int size(){
        return data.size();
    }
    
    public ArrayList<EarthquakeData> getAll(){
        return data;
    }
    
    public void updateEarthquake(int id,EarthquakeData newData) throws IOException{
        data.set(getIndexEarthquake(id), newData);
        saveFile();
    }
    
    public void deleteEarthquake(int id) throws IOException{
        data.remove(getIndexEarthquake(id));
        saveFile();
    }
    
    public void deleteAll() throws IOException {
        data = new ArrayList<EarthquakeData>();
        saveFile();
    }
    
    private int getIndexEarthquake(int id){
        int index = 0;
        while (index < data.size()){
            if(data.get(index).getId() == id){
                return index;
            }
            index++;
        }
        return -1;
    }
    
    private int generateId() {
        Random random = new Random();
        int newId = random.nextInt(Integer.MAX_VALUE);
        while(getEarthquake(newId) != null){
            System.out.println("Id repetido:"+newId);
            newId = random.nextInt(Integer.MAX_VALUE);
        }
        return newId;
    }
    public String toString(){
        String result = "Data:\n[\n";
        for(int i = 0 ; i<data.size() ; i++ ){
            result+= data.get(i).toString()+"\n";
        }
        return result+"]";
    }
    public ArrayList<String[]> toArrayString(){
        ArrayList<String[]> result = new ArrayList<String[]>();
        result.add(new String[] {"Id","Province","Date","Depth","Lat","Lon","OriginFailure","Details","Magnitude","MagnitudeType"});
        for(int i = 0 ; i<data.size() ; i++ ){
            result.add(data.get(i).toArrayString());
        }
        return result;
    }
    private void saveFile() throws IOException{
        sortByDate();
        CsvWriter csvWriter = new CsvWriter("Data.csv");
        ArrayList<String[]> toSave = toArrayString();
        for(String[] fila:toSave){
            csvWriter.writeRecord(fila);
        }
        csvWriter.close();
    }
    private void readFile(){
        try {
            data = new ArrayList<EarthquakeData>();
            CsvReader reader = new CsvReader("Data.csv");
            reader.readHeaders();
            while (reader.readRecord()) {
                int id = Integer.parseInt(reader.get("Id"));
                ProvinceType province = ProvinceType.valueOf(reader.get("Province"));
                LocalDateTime date = LocalDateTime.parse(reader.get("Date"));
                Float depth = Float.parseFloat(reader.get("Depth"));
                Double lat = Double.parseDouble(reader.get("Lat"));
                Double lon = Double.parseDouble(reader.get("Lon"));
                OriginFailureType originFailure = OriginFailureType.valueOf(reader.get("OriginFailure"));
                String details = reader.get("Details");
                Float magnitude = Float.parseFloat(reader.get("Magnitude"));
                MagnitudeType magnitudeType = MagnitudeType.valueOf(reader.get("MagnitudeType"));
                EarthquakeData newData = new EarthquakeData(id,province,date,depth,lat,
                lon,originFailure,details,magnitude,magnitudeType);
                data.add(newData);        
            }

            reader.close();

        } catch (FileNotFoundException e) {
            System.out.println("Debería estar generando datos aleatorios");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        Random random = new Random();
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
    
     private void sortByDate(){
        Collections.sort(data, new Comparator<EarthquakeData>(){
                @Override
                public int compare(EarthquakeData earthquake1, EarthquakeData earthquake2) {
                    return earthquake1.getDate().compareTo(earthquake2.getDate());
            }
        });
    }
}