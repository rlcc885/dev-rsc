/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Batch.Helpers;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Morgan
 */
public class ListLineaCSV {

    //public static ArrayList[] csv = new ArrayList[];
   
    public static ArrayList<ArrayList<String>> listLinea(String file, int dimension) throws FileNotFoundException, IOException {
        //dimension es el numero de campo en el archivo
        ArrayList<ArrayList<String>> documento = new ArrayList<ArrayList<String>>();
        ArrayList<String> linea = new ArrayList<String>();
        
        CSVReader reader = new CSVReader(new FileReader(file));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            linea.addAll(Arrays.asList(nextLine));
            
            documento.add(linea);
        }
        
        return documento;
    }
}
