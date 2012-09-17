/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.pages;

import Batch.Helpers.Unzip;
import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import helpers.Encriptacion;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.hibernate.Session;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.upload.services.UploadedFile;

/**
 *
 * @author ale
 */
public class ImportarBatch extends GeneralPage{


    @Inject
    private Session session;
    
    @Property
    @SessionState
    private Usuario usuario;
    @Property
    @SessionState
    private Entidad entidadUE;    

    @Inject
    private PropertyAccess _access;

    @Inject
    private Request _request;

    @Inject
    private ComponentResources _resources;
    
    @Persist
    @Property
    private String tipocarga;
    @Persist
    @Property
    private String tipoproceso;
    @Property
    @Persist
    private UploadedFile file;
    @Inject
    private ApplicationGlobals globals;
    @Component(id = "primerform")
    private Form primerform;
    private int elemento=0;
    @Property
    @Persist
    private boolean mostrar;
    @Property
    @Persist
    private boolean respuestaOk;
    private final String STARTPATH = "ArchivosXLS/";
    private List<String> errores = new LinkedList<String>();
    
    @Log
    @SetupRender
    private void inicio() {

    }
    
    @Log
    void onSelectedFromSave() {
        elemento = 1;
    }
    @Log
    void onSelectedFromReset() {
        elemento = 2;
    }
    @Log
    void onSelectedFromCancel() {
        elemento = 3;
    }
    @Log
    void onSelectedFromContinue() {
        elemento = 4;
    }
    @Log
    void onSelectedFromCanceldos() {
        elemento = 5;
    }
    
        
    @CommitAfter
    Object onSuccessFromPrimerform() {
        if(elemento==4){            
            if (file == null) {
                primerform.recordError("Tiene que seleccionar archivo a subir.");
                return this;
            }
//            mostrar=true;
        }else if(elemento==2){
            file=null;            
        }else if(elemento==3){
            file=null;
        }else if(elemento==1){
            respuestaOk=true;
            File copied;
            Date date = new Date();
    //        String path = globals.getServletContext().getRealPath("/") + "images/logotipo/";
            String nombreArchivo = "";
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss");
            nombreArchivo = "ArchivosTXT - "+sdf.format(date);
            String path = STARTPATH + "Importacion/"+nombreArchivo+"/";
            
            
            copied = new File(path);
            if (!copied.exists()) {
                copied.mkdirs();
            }
            File nuevo = new File(path+file.getFileName());
            file.write(nuevo);
            
            errores = Unzip.deZippe(path);
            
            System.out.println("priiiiii"+nuevo.getPath()+"/"+nuevo.getName());
            //descromprimir(nuevo.getPath()+"/"+nuevo.getName());
            
        }else if(elemento==5){
            mostrar=false;
        }       

        return this;
    }
    
   void descromprimir(String path){
       final int BUFFER = 2048;
       try {
         BufferedOutputStream dest = null;
         FileInputStream fis = new FileInputStream(path);
         ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
         ZipEntry entry;
         while((entry = zis.getNextEntry()) != null) {
            System.out.println("Extracting: " +entry);
            int count;
            byte data[] = new byte[BUFFER];
            // write the files to the disk
            FileOutputStream fos = new 	      FileOutputStream(entry.getName());
            dest = new               BufferedOutputStream(fos, BUFFER);
            while ((count = zis.read(data, 0, BUFFER))               != -1) {
               dest.write(data, 0, count);
            }
            dest.flush();
            dest.close();
         }
         zis.close();
      } catch(Exception e) {
         e.printStackTrace();
      }
   }

}
