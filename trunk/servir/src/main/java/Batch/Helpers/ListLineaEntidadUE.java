/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Batch.Helpers;

import Batch.Tratamiento;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Sheet;
import jxl.Workbook;

/**
 *
 * @author Morgan
 */
public class ListLineaEntidadUE {
    
    public static List<LineasArchivos> getListLineaEntidadUE(String path, ArrayList<String> misCSVs) throws FileNotFoundException, IOException{
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
                lo.setArchivo("xxxENTIDAD");
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
                lo.setArchivo("xxxCONCEPTO");
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
                lo.setArchivo("xxxUNIDADORGANICA");
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
                lo.setArchivo("xxxCARGO");
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
                lo.setArchivo("xxxCARGOASIGNADO");
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
                lo.setArchivo("xxxREMUNERACION");
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
                lo.setArchivo("xxxEVALUACION");
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
                lo.setArchivo("xxxCONSTANCIA");
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
                lo.setArchivo("xxxTRABAJADOR");
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
                lo.setArchivo("xxxESTUDIO");
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
                lo.setArchivo("xxxCURSO");
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
                lo.setArchivo("xxxANTECEDENTE");
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
                lo.setArchivo("xxxPRODUCCION");
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
                lo.setArchivo("xxxFAMILIAR");
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
                lo.setArchivo("xxxDEMERITO");
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