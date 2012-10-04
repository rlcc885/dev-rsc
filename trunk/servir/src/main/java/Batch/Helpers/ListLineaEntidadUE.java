/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Batch.Helpers;

import Batch.Tratamiento;
import com.tida.servir.entities.Entidad;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Morgan
 */
public class ListLineaEntidadUE {
    
    public static List<LineasArchivos> getListLineaEntidadUE(String path, ArrayList<String> misCSVs,Entidad eu,Integer tipo) throws FileNotFoundException, IOException{
        List<LineasArchivos> llo = new LinkedList<LineasArchivos>();
        LineasArchivos lo;
        try {
            if(!misCSVs.get(0).equals("")){
                FileReader  fr = new FileReader (path+"/"+misCSVs.get(0)+".txt");
                BufferedReader  bf = new BufferedReader (fr);
                String data;
                int lNumeroLineas = 0; 
                while ((data = bf.readLine())!=null) {
                    lNumeroLineas++;
                }
                lo = new LineasArchivos();
                lo.setLineas(lNumeroLineas-1);
                if(tipo==1)
                    lo.setArchivo(eu.getCue_entidad()+"ENTIDAD");
                else
                    lo.setArchivo("ENTIDAD");
                llo.add(lo);
            }
            if(!misCSVs.get(1).equals("")){
                FileReader  fr = new FileReader (path+"/"+misCSVs.get(1)+".txt");
                BufferedReader  bf = new BufferedReader (fr);
                String data;
                int lNumeroLineas = 0; 
                while ((data = bf.readLine())!=null) {
                    lNumeroLineas++;
                }
                lo = new LineasArchivos();
                lo.setLineas(lNumeroLineas-1);
                if(tipo==1)
                    lo.setArchivo(eu.getCue_entidad()+"CONCEPTO");
                else
                    lo.setArchivo("CONCEPTO");
                llo.add(lo);
            }
            if(!misCSVs.get(2).equals("")){
                FileReader  fr = new FileReader (path+"/"+misCSVs.get(2)+".txt");
                BufferedReader  bf = new BufferedReader (fr);
                String data;
                int lNumeroLineas = 0; 
                while ((data = bf.readLine())!=null) {
                    lNumeroLineas++;
                }
                lo = new LineasArchivos();
                lo.setLineas(lNumeroLineas-1);
                if(tipo==1)
                    lo.setArchivo(eu.getCue_entidad()+"CARGO");
                else
                    lo.setArchivo("CARGO");
                llo.add(lo);
            }
            if(!misCSVs.get(3).equals("")){
                FileReader  fr = new FileReader (path+"/"+misCSVs.get(3)+".txt");
                BufferedReader  bf = new BufferedReader (fr);
                String data;
                int lNumeroLineas = 0; 
                while ((data = bf.readLine())!=null) {
                    lNumeroLineas++;
                }
                lo = new LineasArchivos();
                lo.setLineas(lNumeroLineas-1);
                if(tipo==1)
                    lo.setArchivo(eu.getCue_entidad()+"UNIDADORGANICA");
                else
                    lo.setArchivo("UNIDADORGANICA");
                llo.add(lo);
            }
            if(!misCSVs.get(4).equals("")){
                FileReader  fr = new FileReader (path+"/"+misCSVs.get(4)+".txt");
                BufferedReader  bf = new BufferedReader (fr);
                String data;
                int lNumeroLineas = 0; 
                while ((data = bf.readLine())!=null) {
                    lNumeroLineas++;
                }
                lo = new LineasArchivos();
                lo.setLineas(lNumeroLineas-1);
                if(tipo==1)
                    lo.setArchivo(eu.getCue_entidad()+"CARGOASIGNADO");
                else
                    lo.setArchivo("CARGOASIGNADO");
                llo.add(lo);
            }
            if(!misCSVs.get(5).equals("")){
                FileReader  fr = new FileReader (path+"/"+misCSVs.get(5)+".txt");
                BufferedReader  bf = new BufferedReader (fr);
                String data;
                int lNumeroLineas = 0; 
                while ((data = bf.readLine())!=null) {
                    lNumeroLineas++;
                }
                lo = new LineasArchivos();
                lo.setLineas(lNumeroLineas-1);
                if(tipo==1)
                    lo.setArchivo(eu.getCue_entidad()+"REMUNERACION");
                else
                    lo.setArchivo("REMUNERACION");
                llo.add(lo);
            }
            if(!misCSVs.get(6).equals("")){
                FileReader  fr = new FileReader (path+"/"+misCSVs.get(6)+".txt");
                BufferedReader  bf = new BufferedReader (fr);
                String data;
                int lNumeroLineas = 0; 
                while ((data = bf.readLine())!=null) {
                    lNumeroLineas++;
                }
                lo = new LineasArchivos();
                lo.setLineas(lNumeroLineas-1);
                if(tipo==1)
                    lo.setArchivo(eu.getCue_entidad()+"EVALUACION");
                else
                    lo.setArchivo("EVALUACION");
                llo.add(lo);
            }
            if(!misCSVs.get(7).equals("")){
                FileReader  fr = new FileReader (path+"/"+misCSVs.get(7)+".txt");
                BufferedReader  bf = new BufferedReader (fr);
                String data;
                int lNumeroLineas = 0; 
                while ((data = bf.readLine())!=null) {
                    lNumeroLineas++;
                }
                lo = new LineasArchivos();
                lo.setLineas(lNumeroLineas-1);
                if(tipo==1)
                    lo.setArchivo(eu.getCue_entidad()+"CONSTANCIA");
                else
                    lo.setArchivo("CONSTANCIA");
                llo.add(lo);
            }
            if(!misCSVs.get(8).equals("")){
                FileReader  fr = new FileReader (path+"/"+misCSVs.get(8)+".txt");
                BufferedReader  bf = new BufferedReader (fr);
                String data;
                int lNumeroLineas = 0; 
                while ((data = bf.readLine())!=null) {
                    lNumeroLineas++;
                }
                lo = new LineasArchivos();
                lo.setLineas(lNumeroLineas-1);
                if(tipo==1)
                    lo.setArchivo(eu.getCue_entidad()+"TRABAJADOR");
                else
                    lo.setArchivo("TRABAJADOR");
                llo.add(lo);
            }
            if(!misCSVs.get(9).equals("")){
                FileReader  fr = new FileReader (path+"/"+misCSVs.get(9)+".txt");
                BufferedReader  bf = new BufferedReader (fr);
                String data;
                int lNumeroLineas = 0; 
                while ((data = bf.readLine())!=null) {
                    lNumeroLineas++;
                }
                lo = new LineasArchivos();
                lo.setLineas(lNumeroLineas-1);
                if(tipo==1)
                    lo.setArchivo(eu.getCue_entidad()+"ESTUDIO");
                else
                    lo.setArchivo("ESTUDIO");
                llo.add(lo);
            }
            if(!misCSVs.get(10).equals("")){
                FileReader  fr = new FileReader (path+"/"+misCSVs.get(10)+".txt");
                BufferedReader  bf = new BufferedReader (fr);
                String data;
                int lNumeroLineas = 0; 
                while ((data = bf.readLine())!=null) {
                    lNumeroLineas++;
                }
                lo = new LineasArchivos();
                lo.setLineas(lNumeroLineas-1);
                if(tipo==1)
                    lo.setArchivo(eu.getCue_entidad()+"CURSO");
                else
                    lo.setArchivo("CURSO");
                llo.add(lo);
            }
            if(!misCSVs.get(11).equals("")){
                FileReader  fr = new FileReader (path+"/"+misCSVs.get(11)+".txt");
                BufferedReader  bf = new BufferedReader (fr);
                String data;
                int lNumeroLineas = 0; 
                while ((data = bf.readLine())!=null) {
                    lNumeroLineas++;
                }
                lo = new LineasArchivos();
                lo.setLineas(lNumeroLineas-1);
                if(tipo==1)
                    lo.setArchivo(eu.getCue_entidad()+"ANTECEDENTE");
                else
                    lo.setArchivo("ANTECEDENTE");
                llo.add(lo);
            }
            if(!misCSVs.get(12).equals("")){
                FileReader  fr = new FileReader (path+"/"+misCSVs.get(12)+".txt");
                BufferedReader  bf = new BufferedReader (fr);
                String data;
                int lNumeroLineas = 0; 
                while ((data = bf.readLine())!=null) {
                    lNumeroLineas++;
                }
                lo = new LineasArchivos();
                lo.setLineas(lNumeroLineas-1);
                if(tipo==1)
                    lo.setArchivo(eu.getCue_entidad()+"PRODUCCION");
                else
                    lo.setArchivo("PRODUCCION");
                llo.add(lo);
            }
            if(!misCSVs.get(13).equals("")){
                FileReader  fr = new FileReader (path+"/"+misCSVs.get(13)+".txt");
                BufferedReader  bf = new BufferedReader (fr);
                String data;
                int lNumeroLineas = 0; 
                while ((data = bf.readLine())!=null) {
                    lNumeroLineas++;
                }
                lo = new LineasArchivos();
                lo.setLineas(lNumeroLineas-1);
                if(tipo==1)
                    lo.setArchivo(eu.getCue_entidad()+"FAMILIA");
                else
                    lo.setArchivo("FAMILIA");
                llo.add(lo);
            }
            if(!misCSVs.get(14).equals("")){
                FileReader  fr = new FileReader (path+"/"+misCSVs.get(14)+".txt");
                BufferedReader  bf = new BufferedReader (fr);
                String data;
                int lNumeroLineas = 0; 
                while ((data = bf.readLine())!=null) {
                    lNumeroLineas++;
                }
                lo = new LineasArchivos();
                lo.setLineas(lNumeroLineas-1);
                if(tipo==1)
                    lo.setArchivo(eu.getCue_entidad()+"DEMERITO");
                else
                    lo.setArchivo("DEMERITO");
                llo.add(lo);
            }            
        }catch(Exception ioe) {
            Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ioe);
            System.out.println(""+ ioe.getMessage());
        }
        
        return llo;
    }
    
//     public static int lineaArchivo(String archivo) throws FileNotFoundException, IOException {
//
//        int linea = 0;
//
//        File file = new File(archivo);
//        CSVReader reader = new CSVReader(new FileReader(file), '|');
//        String[] nextLine;
//        while ((nextLine = reader.readNext()) != null) {
//            if(nextLine.length>2)
//                linea++;
//        }
//        return linea;
//    }

}