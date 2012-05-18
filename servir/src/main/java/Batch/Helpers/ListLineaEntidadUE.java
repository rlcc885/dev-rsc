/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Batch.Helpers;

import au.com.bytecode.opencsv.CSVReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Morgan
 */
public class ListLineaEntidadUE {
    
    public static List<LineasArchivos> getListLineaEntidadUE(String path, ArrayList<String> misCSVs) throws FileNotFoundException, IOException{
        List<LineasArchivos> llo = new LinkedList<LineasArchivos>();
        LineasArchivos lo;
       
        if(!misCSVs.get(14).equals("")){
            lo = new LineasArchivos();
            lo.setLineas(lineaArchivo(path.concat(misCSVs.get(14).concat(".csv"))));
            lo.setArchivo("Antecedent Laboral");
            llo.add(lo);
        }
        
        if(!misCSVs.get(19).equals("")){
            lo = new LineasArchivos();
            lo.setLineas(lineaArchivo(path.concat(misCSVs.get(19).concat(".csv"))));
            lo.setArchivo("Ausencia y Licencia");
            llo.add(lo);
        }
            
        if(!misCSVs.get(6).equals("")){
            lo = new LineasArchivos();
            lo.setLineas(lineaArchivo(path.concat(misCSVs.get(6).concat(".csv"))));
            lo.setArchivo("Cargo");
            llo.add(lo);
        }
            
        if(!misCSVs.get(9).equals("")){
            lo = new LineasArchivos();
            lo.setLineas(lineaArchivo(path.concat(misCSVs.get(9).concat(".csv"))));
            lo.setArchivo("Cargo Asignado");
            llo.add(lo);
        }
            
        if(!misCSVs.get(12).equals("")){
            lo = new LineasArchivos();
            lo.setLineas(lineaArchivo(path.concat(misCSVs.get(12).concat(".csv"))));
            lo.setArchivo("Certificacion");
            llo.add(lo);
        }
            
        if(!misCSVs.get(1).equals("")){
            lo = new LineasArchivos();
            lo.setLineas(lineaArchivo(path.concat(misCSVs.get(1).concat(".csv"))));
            lo.setArchivo("Concepto Remunerativo");
            llo.add(lo);
        }
            
        if(!misCSVs.get(20).equals("")){
            lo = new LineasArchivos();
            lo.setLineas(lineaArchivo(path.concat(misCSVs.get(20).concat(".csv"))));
            lo.setArchivo("Constancia Documental");
            llo.add(lo);
        }
            
        if(!misCSVs.get(13).equals("")){
            lo = new LineasArchivos();
            lo.setLineas(lineaArchivo(path.concat(misCSVs.get(13).concat(".csv"))));
            lo.setArchivo("Curso");
            llo.add(lo);
        }
            
        if(!misCSVs.get(2).equals("")){
            lo = new LineasArchivos();
            lo.setLineas(lineaArchivo(path.concat(misCSVs.get(2).concat(".csv"))));
            lo.setArchivo("Esquema Remunerativa");
            llo.add(lo);
        }
            
        if(!misCSVs.get(18).equals("")){
            lo = new LineasArchivos();
            lo.setLineas(lineaArchivo(path.concat(misCSVs.get(18).concat(".csv"))));
            lo.setArchivo("Evaluacion");
            llo.add(lo);
        }
            
        if(!misCSVs.get(10).equals("")){
            lo = new LineasArchivos();
            lo.setLineas(lineaArchivo(path.concat(misCSVs.get(10).concat(".csv"))));
            lo.setArchivo("Familiar");
            llo.add(lo);
        }
            
        if(!misCSVs.get(8).equals("")){
            lo = new LineasArchivos();
            lo.setLineas(lineaArchivo(path.concat(misCSVs.get(8).concat(".csv"))));
            lo.setArchivo("Legajo");
            llo.add(lo);
        }
            
        if(!misCSVs.get(15).equals("")){
            lo = new LineasArchivos();
            lo.setLineas(lineaArchivo(path.concat(misCSVs.get(15).concat(".csv"))));
            lo.setArchivo("Merito Demerito");
            llo.add(lo);
        }
            
        if(!misCSVs.get(0).equals("")){
            lo = new LineasArchivos();
            lo.setLineas(lineaArchivo(path.concat(misCSVs.get(0).concat(".csv"))));
            lo.setArchivo("Entidad Unidad Ejecutora");
            llo.add(lo);
        }
            
        /*if(!misCSVs.get(4).equals("")){
            lo = new LineasArchivos();
            lo.setLineas(lineaArchivo(path.concat(misCSVs.get(4).concat(".csv"))));
            lo.setArchivo("Organo");
            llo.add(lo);
        }*/
            
        if(!misCSVs.get(3).equals("")){
            lo = new LineasArchivos();
            lo.setLineas(lineaArchivo(path.concat(misCSVs.get(3).concat(".csv"))));
            lo.setArchivo("Plantilla Remunerativa");
            llo.add(lo);
        }
            
        if(!misCSVs.get(16).equals("")){
            lo = new LineasArchivos();
            lo.setLineas(lineaArchivo(path.concat(misCSVs.get(16).concat(".csv"))));
            lo.setArchivo("Produccion Intelectual");
            llo.add(lo);
        }
            
        if(!misCSVs.get(17).equals("")){
            lo = new LineasArchivos();
            lo.setLineas(lineaArchivo(path.concat(misCSVs.get(17).concat(".csv"))));
            lo.setArchivo("Remuneracion Personal");
            llo.add(lo);
        }
            
        if(!misCSVs.get(11).equals("")){
            lo = new LineasArchivos();
            lo.setLineas(lineaArchivo(path.concat(misCSVs.get(11).concat(".csv"))));
            lo.setArchivo("Titulo");
            llo.add(lo);
        }
            
        if(!misCSVs.get(7).equals("")){
            lo = new LineasArchivos();
            lo.setLineas(lineaArchivo(path.concat(misCSVs.get(7).concat(".csv"))));
            lo.setArchivo("Trabajador");
            llo.add(lo);
        }
            
        if(!misCSVs.get(5).equals("")){
            lo = new LineasArchivos();
            lo.setLineas(lineaArchivo(path.concat(misCSVs.get(5).concat(".csv"))));
            lo.setArchivo("Unidad Organica");
            llo.add(lo);
        }
                    
        return llo;
    }
    
     public static int lineaArchivo(String archivo) throws FileNotFoundException, IOException {

        int linea = 0;

        File file = new File(archivo);
        CSVReader reader = new CSVReader(new FileReader(file), '|');
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            if(nextLine.length>2)
                linea++;
        }
        return linea;
    }

}