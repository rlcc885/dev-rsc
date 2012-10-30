/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Batch.Helpers;

import Batch.Tratamiento;
import com.tida.servir.entities.Entidad;
import com.tida.servir.entities.Usuario;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import java.io.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import org.apache.tapestry5.upload.services.UploadedFile;
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
    
    public ValidacionXLS(String path, String origenArchivo, Session session, List<String> errores, Usuario usuario,Entidad eu,int tipo) throws IOException, ParseException {
        this.path = path;
        this.origenArchivo = origenArchivo;
        this.session = session;
        this.errores = errores;
        this.usuario = usuario;
        if(tipo==2){
            archivos.add(eu.getCue_entidad()+"ENTIDAD");
            archivos.add(eu.getCue_entidad()+"CONCEPTO");
            archivos.add(eu.getCue_entidad()+"CARGO");
            archivos.add(eu.getCue_entidad()+"UNIDADORGANICA");
            archivos.add(eu.getCue_entidad()+"CARGOASIGNADO");
            archivos.add(eu.getCue_entidad()+"REMUNERACION");
            archivos.add(eu.getCue_entidad()+"EVALUACION");
            archivos.add(eu.getCue_entidad()+"CONSTANCIA");
            archivos.add(eu.getCue_entidad()+"TRABAJADOR");
            archivos.add(eu.getCue_entidad()+"ESTUDIO");
            archivos.add(eu.getCue_entidad()+"CURSO");
            archivos.add(eu.getCue_entidad()+"ANTECEDENTE");
            archivos.add(eu.getCue_entidad()+"PRODUCCION");
            archivos.add(eu.getCue_entidad()+"FAMILIA");
            archivos.add(eu.getCue_entidad()+"DEMERITO");
        }else{
            archivos.add("ENTIDAD");
            archivos.add("CONCEPTO");
            archivos.add("CARGO");
            archivos.add("UNIDADORGANICA");
            archivos.add("CARGOASIGNADO");
            archivos.add("REMUNERACION");
            archivos.add("EVALUACION");
            archivos.add("CONSTANCIA");
            archivos.add("TRABAJADOR");
            archivos.add("ESTUDIO");
            archivos.add("CURSO");
            archivos.add("ANTECEDENTE");
            archivos.add("PRODUCCION");
            archivos.add("FAMILIA");
            archivos.add("DEMERITO");
        }
        
        
        for (int k = 0; k < 15; k++) {
            misCSVs.add("");
        }
        
        errores = listArchivosDepositorioClassified(path);
//        errores = listColumnas(path, misCSVs,eu);
        
//        if(usuario.getRolid()!=2){
//            if (misCSVs.get(0).equals("")){
//                errores.add("Archivo "+eu.getCue_entidad()+"ENTIDAD no encontrado");
//                return;
//            }
//        }
        
        if(usuario.getRolid()==2){
            if(misCSVs.get(0).equals(eu.getCue_entidad()+"ENTIDAD")){                    
               errores.add("Archivo "+eu.getCue_entidad()+"ENTIDAD no esta permitido de subir");
               return;
            }
            if(misCSVs.get(5).equals(eu.getCue_entidad()+"REMUNERACION")){ 
               errores.add("Archivo "+eu.getCue_entidad()+"REMUNERACION no esta permitido de subir"); 
               return;
            }
            if(misCSVs.get(1).equals(eu.getCue_entidad()+"CONCEPTO")){ 
               errores.add("Archivo "+eu.getCue_entidad()+"CONCEPTO no esta permitido de subir"); 
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
            errores.add("Error en el nombre del archivo:"+depositorio);
            return 0;
        }
        
        return posicion;
    }
    
    public List<String> listArchivosDepositorioCopied(String path) throws IOException {
        File directorio = new File(path);
        File[] list = directorio.listFiles();
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                if (list[i].getName().endsWith(".txt") || list[i].getName().endsWith(".TXT")) {
                    misCSVs.set(compareStringDepositorioToArrayListArchivos(list[i].getName().substring(0, (list[i].getName().length()) - 4), archivos, errores), list[i].getName().substring(0, (list[i].getName().length()) - 4));
                    File origen = new File(path+list[i].getName());
                    File destino = new File("/home/carga/file/"+list[i].getName());
                    InputStream in = new FileInputStream(origen);
                    OutputStream out = new FileOutputStream(destino);   
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();
                }
            }
        } else {
            errores.add(directorio + " : Problema con el directorio");
        }
        return errores;

    }
    
    public List<String> listColumnas(String path,ArrayList<String> misCSVs,Entidad eu) throws IOException {
        try {
            if(!misCSVs.get(0).equals("")){
                FileReader  fr = new FileReader (path+"/"+misCSVs.get(0)+".txt");
                BufferedReader  bf = new BufferedReader (fr);
                String data;
                int contproo=0;
                String[] datos = null;
                while ((data = bf.readLine())!=null) { 
                    datos = data.split("\t");
//                    datos=data.split(System.getProperty("line.separator"));
//                    System.out.println("aquiiiii"+datos[0] + "-" + datos[1] + "-"+datos[2]+"-"+datos[19]);
                    if(datos.length!=20){                      
                        contproo++; 
                    }
                }
                if(contproo>0)
                        errores.add("Numero de Columnas diferente de Formato, Archivo: "+misCSVs.get(0));
            }

        }catch(Exception ioe) {
            Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ioe);
            System.out.println(""+ ioe.getMessage());
        }
        return errores;
    }
    
    public List<LineasArchivos> getCantLineasArchivos(List<String> errores,Entidad eu,Integer tipo) {
        List<LineasArchivos> llo = new LinkedList<LineasArchivos>();
        try {
            llo = ListLineaEntidadUE.getListLineaEntidadUE(path, misCSVs,eu,tipo);
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
