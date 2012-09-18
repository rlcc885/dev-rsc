/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Batch.Helpers;

import Batch.Tratamiento;
import au.com.bytecode.opencsv.CSVReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
                Workbook wb = Workbook.getWorkbook(new File(path+"/"+misCSVs.get(0)+".xls"));
                lo = new LineasArchivos();
                Sheet sheet = wb.getSheet(0);
                int rows = sheet.getRows();
                lo = new LineasArchivos();
                lo.setLineas(rows-1);
                lo.setArchivo("xxxENTIDAD");
                llo.add(lo);
            }
            if(!misCSVs.get(1).equals("")){
                Workbook wb = Workbook.getWorkbook(new File(path+"/"+misCSVs.get(1)+".xls"));
                lo = new LineasArchivos();
                Sheet sheet = wb.getSheet(0);
                int rows = sheet.getRows();
                lo = new LineasArchivos();
                lo.setLineas(rows-1);
                lo.setArchivo("xxxCONCEPTO");
                llo.add(lo);
            }
            if(!misCSVs.get(2).equals("")){
                Workbook wb = Workbook.getWorkbook(new File(path+"/"+misCSVs.get(2)+".xls"));
                lo = new LineasArchivos();
                Sheet sheet = wb.getSheet(0);
                int rows = sheet.getRows();
                lo = new LineasArchivos();
                lo.setLineas(rows-1);
                lo.setArchivo("xxxUNIDADORGANICA");
                llo.add(lo);
            }
            if(!misCSVs.get(3).equals("")){
                Workbook wb = Workbook.getWorkbook(new File(path+"/"+misCSVs.get(3)+".xls"));
                lo = new LineasArchivos();
                Sheet sheet = wb.getSheet(0);
                int rows = sheet.getRows();
                lo = new LineasArchivos();
                lo.setLineas(rows-1);
                lo.setArchivo("xxxCARGO");
                llo.add(lo);
            }
            if(!misCSVs.get(4).equals("")){
                Workbook wb = Workbook.getWorkbook(new File(path+"/"+misCSVs.get(4)+".xls"));
                lo = new LineasArchivos();
                Sheet sheet = wb.getSheet(0);
                int rows = sheet.getRows();
                lo = new LineasArchivos();
                lo.setLineas(rows-1);
                lo.setArchivo("xxxCARGOASIGNADO");
                llo.add(lo);
            }
            if(!misCSVs.get(5).equals("")){
                Workbook wb = Workbook.getWorkbook(new File(path+"/"+misCSVs.get(5)+".xls"));
                lo = new LineasArchivos();
                Sheet sheet = wb.getSheet(0);
                int rows = sheet.getRows();
                lo = new LineasArchivos();
                lo.setLineas(rows-1);
                lo.setArchivo("xxxREMUNERACION");
                llo.add(lo);
            }
            if(!misCSVs.get(6).equals("")){
                Workbook wb = Workbook.getWorkbook(new File(path+"/"+misCSVs.get(6)+".xls"));
                lo = new LineasArchivos();
                Sheet sheet = wb.getSheet(0);
                int rows = sheet.getRows();
                lo = new LineasArchivos();
                lo.setLineas(rows-1);
                lo.setArchivo("xxxEVALUACION");
                llo.add(lo);
            }
            if(!misCSVs.get(7).equals("")){
                Workbook wb = Workbook.getWorkbook(new File(path+"/"+misCSVs.get(7)+".xls"));
                lo = new LineasArchivos();
                Sheet sheet = wb.getSheet(0);
                int rows = sheet.getRows();
                lo = new LineasArchivos();
                lo.setLineas(rows-1);
                lo.setArchivo("xxxCONSTANCIA");
                llo.add(lo);
            }
            if(!misCSVs.get(8).equals("")){
                Workbook wb = Workbook.getWorkbook(new File(path+"/"+misCSVs.get(8)+".xls"));
                lo = new LineasArchivos();
                Sheet sheet = wb.getSheet(0);
                int rows = sheet.getRows();
                lo = new LineasArchivos();
                lo.setLineas(rows-1);
                lo.setArchivo("xxxTRABAJADOR");
                llo.add(lo);
            }
            if(!misCSVs.get(9).equals("")){
                Workbook wb = Workbook.getWorkbook(new File(path+"/"+misCSVs.get(9)+".xls"));
                lo = new LineasArchivos();
                Sheet sheet = wb.getSheet(0);
                int rows = sheet.getRows();
                lo = new LineasArchivos();
                lo.setLineas(rows-1);
                lo.setArchivo("xxxESTUDIO");
                llo.add(lo);
            }
            if(!misCSVs.get(10).equals("")){
                Workbook wb = Workbook.getWorkbook(new File(path+"/"+misCSVs.get(10)+".xls"));
                lo = new LineasArchivos();
                Sheet sheet = wb.getSheet(0);
                int rows = sheet.getRows();
                lo = new LineasArchivos();
                lo.setLineas(rows-1);
                lo.setArchivo("xxxCURSO");
                llo.add(lo);
            }
            if(!misCSVs.get(11).equals("")){
                Workbook wb = Workbook.getWorkbook(new File(path+"/"+misCSVs.get(11)+".xls"));
                lo = new LineasArchivos();
                Sheet sheet = wb.getSheet(0);
                int rows = sheet.getRows();
                lo = new LineasArchivos();
                lo.setLineas(rows-1);
                lo.setArchivo("xxxANTECEDENTE");
                llo.add(lo);
            }
            if(!misCSVs.get(12).equals("")){
                Workbook wb = Workbook.getWorkbook(new File(path+"/"+misCSVs.get(12)+".xls"));
                lo = new LineasArchivos();
                Sheet sheet = wb.getSheet(0);
                int rows = sheet.getRows();
                lo = new LineasArchivos();
                lo.setLineas(rows-1);
                lo.setArchivo("xxxPRODUCCION");
                llo.add(lo);
            }
            if(!misCSVs.get(13).equals("")){
                Workbook wb = Workbook.getWorkbook(new File(path+"/"+misCSVs.get(13)+".xls"));
                lo = new LineasArchivos();
                Sheet sheet = wb.getSheet(0);
                int rows = sheet.getRows();
                lo = new LineasArchivos();
                lo.setLineas(rows-1);
                lo.setArchivo("xxxFAMILIAR");
                llo.add(lo);
            }
            if(!misCSVs.get(14).equals("")){
                Workbook wb = Workbook.getWorkbook(new File(path+"/"+misCSVs.get(14)+".xls"));
                lo = new LineasArchivos();
                Sheet sheet = wb.getSheet(0);
                int rows = sheet.getRows();
                lo = new LineasArchivos();
                lo.setLineas(rows-1);
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