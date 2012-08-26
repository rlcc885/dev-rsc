/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Batch.Helpers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.*;

/**
 *
 * @author Morgan
 */
public class Unzip {

    static final int BUFFER = 2048;

    public static List<String> deZippe(String path) {
        List<String> errores = new LinkedList<String>();
        List<String> numeroArchivoZip = new LinkedList<String>();
        
        //System.out.println("path dans dezippe "+path);

        File directorio = new File(path);
        File[] list = directorio.listFiles();
        /*for (File file: list) {
            System.out.println(file.getName());
        }*/
        
        if ((list != null) && list.length > 0) {
            for (int i = 0; i < list.length; i++) {
                if (list[i].getName().endsWith(".ZIP") || list[i].getName().endsWith(".zip") || list[i].getName().endsWith(".Zip")) {
                    numeroArchivoZip.add(list[i].getName());
                }
            }
        } else {
            errores.add("La carpeta " + path + " no contiene archivos.");
            return errores;
        }

        //verificacion si hay un zip en el repository
        //no hay archivo ZIP
        if (numeroArchivoZip.isEmpty()) {
            errores.add("No hay archivo ZIP en la carpeta " + path );
            return errores;
        }

        //si hay mas de un archivo
        if (numeroArchivoZip.size() > 1) {
            errores.add("Hay mas de un archivo ZIP en la carpeta " + path );
            return errores;
        }

        //System.out.println("PATH "+path);
        //hay un archivo ZIP -> unzip tratamiento
        try {
            ZipInputStream zin = new ZipInputStream(new BufferedInputStream(new FileInputStream(path + numeroArchivoZip.get(0))));
            //System.out.println("path dans cr�ation archivo dans dezippe "+path + numeroArchivoZip.get(0));
            ZipEntry entry;
            while ((entry = zin.getNextEntry()) != null) {
                //System.out.println("UNZIP AVANT CREATION");
                int j = entry.getName().lastIndexOf('/');
                if (j != -1) {
                    // cr�ation de sous r�pertoires si besoin
                    File file = new File(path + entry.getName().substring(0, j));
                    file.mkdirs();
                }
                //System.out.println("UNZIP APRES CREATION");
                // copie du fichier entry.getName()
                BufferedOutputStream fo = new BufferedOutputStream(new FileOutputStream(path + entry.getName()));
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fo.write(c);
                }
                //System.out.println("UNZIP FIN");
                fo.close();
                //System.out.println("UNZIP APRES FO CLOSE");
            }

            zin.close();
            //System.out.println("UNZIP ZIN CLOSE");

        } catch (FileNotFoundException e) {
            errores.add("Problema buscando el archivo ZIP");
            return errores;
        } catch (IOException e) {
            errores.add("Problema leyendo el archivo ZIP");
            return errores;
        }
        return errores;
    }

    public static List<String> zippe(String path, String nombre) {
        List<String> errores = new LinkedList<String>();

        try {
            //System.out.println("path dans zippe " + path);
            // cr�ation d'un flux d'�criture sur fichier
            //Date date = new Date();
            //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            //System.out.println("--------------- date "+sdf.format(date));
            //FileOutputStream dest = new FileOutputStream(path + "ArchivosCSV - "+sdf.format(date)+".zip");
            FileOutputStream dest = new FileOutputStream(path + nombre);
            //FileOutputStream dest = new FileOutputStream(path + "ArchivosCSV.zip");
            // calcul du checksum : Adler32 (plus rapide) ou CRC32
            CheckedOutputStream checksum = new CheckedOutputStream(dest, new Adler32());
            // cr�ation d'un buffer d'�criture
            BufferedOutputStream buff = new BufferedOutputStream(checksum);
            // cr�ation d'un flux d'�criture Zip
            ZipOutputStream out = new ZipOutputStream(buff);
            // sp�cification de la m�thode de compression
            out.setMethod(ZipOutputStream.DEFLATED);
            // sp�cifier la qualit� de la compression 0..9
            out.setLevel(Deflater.BEST_COMPRESSION);

            // buffer temporaire des donn�es � �criture dans le flux de sortie
            byte data[] = new byte[BUFFER];
            // extraction de la liste des fichiers du r�pertoire courant
            File f = new File(path + ".");
            //System.out.println("path dans zippe " + path + "GeneracionCSV/");
            String files[] = f.list();
            // pour chacun des fichiers de la liste
            for (int i = 0; i < files.length; i++) {
                if (files[i].endsWith(".csv") || files[i].endsWith(".CSV")) {

                    // en afficher le nom
                    //System.out.println("Adding: " + files[i]);

                    // cr�ation d'un flux de lecture
                    FileInputStream fi = new FileInputStream(path + files[i]);
                    //FileInputStream fi = new FileInputStream(files[i]);
                    // cr�ation d'un tampon de lecture sur ce flux
                    BufferedInputStream buffi = new BufferedInputStream(fi, BUFFER);
                    // cr�ation d'en entr�e Zip pour ce fichier
                    //ZipEntry entry = new ZipEntry(path + "GeneracionCSV/" + files[i]);
                    ZipEntry entry = new ZipEntry(files[i]);
                    // ajout de cette entr�e dans le flux d'�criture de l'archive Zip
                    out.putNextEntry(entry);

                    // �criture du fichier par paquet de BUFFER octets
                    // dans le flux d'�criture
                    int count;
                    while ((count = buffi.read(data, 0, BUFFER)) != -1) {
                        out.write(data, 0, count);
                    }

                    out.closeEntry();
                    buffi.close();
                }
            }
            out.close();
            buff.close();
            checksum.close();
            dest.close();

        } catch (Exception e) {
            e.printStackTrace();
            return errores;
        }
        return errores;

    }
    
    public static List<String> zippeXLS(String path, String nombreArchivo) {
        List<String> errores = new LinkedList<String>();

        try {
            //System.out.println("path dans zippe " + path);
            // cr�ation d'un flux d'�criture sur fichier
            FileOutputStream dest = new FileOutputStream(path + nombreArchivo + "XLS.zip");
            //FileOutputStream dest = new FileOutputStream(path + "GeneracionCSV/" + "ArchivosCSV.zip");
            // calcul du checksum : Adler32 (plus rapide) ou CRC32
            CheckedOutputStream checksum = new CheckedOutputStream(dest, new Adler32());
            // cr�ation d'un buffer d'�criture
            BufferedOutputStream buff = new BufferedOutputStream(checksum);
            // cr�ation d'un flux d'�criture Zip
            ZipOutputStream out = new ZipOutputStream(buff);
            // sp�cification de la m�thode de compression
            out.setMethod(ZipOutputStream.DEFLATED);
            // sp�cifier la qualit� de la compression 0..9
            out.setLevel(Deflater.BEST_COMPRESSION);

            // buffer temporaire des donn�es � �criture dans le flux de sortie
            byte data[] = new byte[BUFFER];
            // extraction de la liste des fichiers du r�pertoire courant
            File f = new File(path + ".");
            //File f = new File(path + "GeneracionCSV/" + ".");
            //System.out.println("path dans zippe " + path + "GeneracionCSV/");
            String files[] = f.list();
            // pour chacun des fichiers de la liste
            for (int i = 0; i < files.length; i++) {
                if (files[i].endsWith(".xls") || files[i].endsWith(".XLS")) {
                //if (files[i].endsWith(".csv") || files[i].endsWith(".CSV")) {

                    // en afficher le nom
                    //System.out.println("Adding: " + files[i]);

                    // cr�ation d'un flux de lecture
                    //FileInputStream fi = new FileInputStream(path + "GeneracionCSV/" + files[i]);
                    FileInputStream fi = new FileInputStream(path + files[i]);
                    //FileInputStream fi = new FileInputStream(files[i]);
                    // cr�ation d'un tampon de lecture sur ce flux
                    BufferedInputStream buffi = new BufferedInputStream(fi, BUFFER);
                    // cr�ation d'en entr�e Zip pour ce fichier
                    //ZipEntry entry = new ZipEntry(path + "GeneracionCSV/" + files[i]);
                    ZipEntry entry = new ZipEntry(files[i]);
                    // ajout de cette entr�e dans le flux d'�criture de l'archive Zip
                    out.putNextEntry(entry);

                    // �criture du fichier par paquet de BUFFER octets
                    // dans le flux d'�criture
                    int count;
                    while ((count = buffi.read(data, 0, BUFFER)) != -1) {
                        out.write(data, 0, count);
                    }

                    out.closeEntry();
                    buffi.close();
                }
            }
            out.close();
            buff.close();
            checksum.close();
            dest.close();

        } catch (Exception e) {
            e.printStackTrace();
            return errores;
        }
        return errores;

    }
}
