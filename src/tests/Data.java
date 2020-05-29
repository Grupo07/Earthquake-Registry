/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import data.Earthquake;
import data.FaultOrigin;
import data.Province;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 *
 * @author esteb
 */
public class Data {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        data.Data data = new data.Data();
        
        System.out.println("Agregando un sismo");
        data.addEarthquake(Province.CARTAGO, LocalDateTime.now(), 1, 9.855694,
                -83.912874, FaultOrigin.CHOQUE_DE_PLACAS, 
                "Temblor en el pretil", 3);
        System.out.println("PD: para verificar un id único, revisa los sismos registrados");
        
        System.out.println("\nObteniendo sismo por Id");
        int idNewEarthquake = data.getAll().get(data.getAll().size()-1).getId();
        System.out.println(data.getEarthquake(idNewEarthquake).toString());
        
        System.out.println("\nActualizando el sismo");
        Earthquake update = new Earthquake(idNewEarthquake,Province.CARTAGO, 
                LocalDateTime.now(), 1, 9.855694, -83.912874, 
                FaultOrigin.CHOQUE_DE_PLACAS, "Temblor en el TEC de Cartago",
                3);
        data.updateEarthquake(idNewEarthquake, update);
        System.out.println("Resultado: "+data.getEarthquake(idNewEarthquake));
        
        System.out.println("\nEliminando el sismo");
        data.deleteEarthquake(idNewEarthquake);
        data.getEarthquake(idNewEarthquake);
    }
    
}
