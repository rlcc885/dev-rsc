/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Batch.Helpers;

import Batch.Tratamiento;
import com.tida.servir.entities.Usuario;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import jxl.*;
import java.io.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author arson
 */
public class ValidacionXLS {
    private ArrayList<String> misCSVs = new ArrayList<String>();
    public ArrayList<String> archivos = new ArrayList<String>();
    
    String path;
    String origenArchivo;
    Session session;
    List<String> errores;
    Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ArrayList<String> getMisCSVs() {
        return misCSVs;
    }

    public String getOrigenArchivo() {
        return origenArchivo;
    }

    public String getPath() {
        return path;
    }

    public Session getSession() {
        return session;
    }
    
    public ValidacionXLS(String path, String origenArchivo, Session session, List<String> errores, Usuario usuario) throws IOException, ParseException {
        this.path = path;
        this.origenArchivo = origenArchivo;
        this.session = session;
        this.errores = errores;
        this.usuario = usuario;
        
        archivos.add("xxxENTIDAD");
        archivos.add("xxxCONCEPTO");
        archivos.add("xxxCARGO");
        archivos.add("xxxUNIDADORGANICA");
        archivos.add("xxxCARGOASIGNADO");
        archivos.add("xxxREMUNERACION");
        archivos.add("xxxEVALUACION");
        archivos.add("xxxCONSTANCIA");
        archivos.add("xxxTRABAJADOR");
        archivos.add("xxxESTUDIO");
        archivos.add("xxxCURSO");
        archivos.add("xxxANTECEDENTE");
        archivos.add("xxxPRODUCCION");
        archivos.add("xxxFAMILIAR");
        archivos.add("xxxDEMERITO");
        
        for (int k = 0; k < 15; k++) {
            misCSVs.add("");
        }
        
        errores = listArchivosDepositorioClassified(path);
        
        if(usuario.getRolid()!=2){
            if (misCSVs.get(0).equals("")) {
                errores.add("Archivo xxxENTIDAD no encontrado");
                return;
            }
        }
        
        if(usuario.getRolid()==2){
            if(misCSVs.get(0).equals("xxxENTIDAD")){                
               errores.add("Archivo xxxENTIDAD no esta permitido de subir"); 
               return;
            }
            if(misCSVs.get(5).equals("xxxREMUNERACION")){ 
               errores.add("Archivo xxxREMUNERACION no esta permitido de subir"); 
               return;
            }
            if(misCSVs.get(1).equals("xxxCONCEPTO")){ 
               errores.add("Archivo xxxCONCEPTO no esta permitido de subir"); 
               return;
            }
        }
        
        
    }
    
    public List<String> listArchivosDepositorioClassified(String path) throws IOException {
        File directorio = new File(path);
        File[] list = directorio.listFiles();
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                if (list[i].getName().endsWith(".txt") || list[i].getName().endsWith(".TXT")) {
                    misCSVs.set(compareStringDepositorioToArrayListArchivos(list[i].getName().substring(0, (list[i].getName().length()) - 4), archivos, errores), list[i].getName().substring(0, (list[i].getName().length()) - 4));
                }
            }
        } else {
            errores.add(directorio + " : Problema con el directorio");
        }
        return errores;

    }
    
    public static int compareStringDepositorioToArrayListArchivos(String depositorio, ArrayList archivo, List<String> errores) {

        int posicion = 0;

        posicion = archivo.indexOf(depositorio);
        if (posicion == -1) {
            errores.add("Error en el nombre del archivo.");
            return 0;
        }
        
        return posicion;
    }
    public List<LineasArchivos> getCantLineasArchivos(List<String> errores) {
        List<LineasArchivos> llo = new LinkedList<LineasArchivos>();
        try {
            llo = ListLineaEntidadUE.getListLineaEntidadUE(path, misCSVs);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
            errores.add("Error buscando archivo: " + ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
            errores.add("Error procesando archivo: " + ex.getMessage());
        }
        return llo;
    }
    
    
    
    
       
    
}
