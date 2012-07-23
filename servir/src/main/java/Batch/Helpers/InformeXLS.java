/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Batch.Helpers;

import com.tida.servir.entities.Ant_Laborales;
import com.tida.servir.entities.Cargoxunidad;
import com.tida.servir.entities.CargoAsignado;
import com.tida.servir.entities.Certificacion;
import com.tida.servir.entities.ConceptoRemunerativo;
import com.tida.servir.entities.ConstanciaDocumental;
import com.tida.servir.entities.Curso;
import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.Familiar;
import com.tida.servir.entities.Legajo;
import com.tida.servir.entities.MeritoDemerito;
import com.tida.servir.entities.Entidad_BK;
import com.tida.servir.entities.Publicacion;
import com.tida.servir.entities.Titulo;
import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.UnidadOrganica;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.ScriptStyle;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.hibernate.Session;

/**
 *
 * @author Morgan
 */
public class InformeXLS {
    
    public static final String ERROR_GENERANDO_EL_ARCHIVO = "Error generando el archivo: ";
    public static final String TRUE_ARCHIVO = "T";
    public static final String FALSE_ARCHIVO = "F";
    public static final String RECHAZADOS = "Rechazados";
    public static final String SIN_CAMBIOS = "Sin cambios";
    public static final String MODIFICADOS = "Modificados";
    public static final String ALTAS = "Altas";
    
    

    /** Creacion del archivo Concepto.xls
     * 
     * @param is todos los datos para el archivo
     * @throws IOException
     * @throws WriteException 
     * 
     */
    public void creadorXLSConcepto(InformeSalida<LineaInformeCodigo> is, String path)
            throws IOException, WriteException {

        File monFichier = new File(path + "/" + helpers.Constantes.CONCEPTOS_REMUNERATIVOS + ".xls");

        WritableWorkbook source = Workbook.createWorkbook(monFichier);

        WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10);
        WritableCellFormat arial10format = new WritableCellFormat(arial10font);
        arial10format.setBorder(Border.ALL, BorderLineStyle.MEDIUM);


        WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableCellFormat bold = new WritableCellFormat(wf);
        bold.setWrap(true);

        WritableSheet sheet1 = source.createSheet(monFichier.getName().toString(), 0);
        sheet1.mergeCells(1, 1, 3, 3);
        sheet1.mergeCells(0, 5, 4, 5);

        WritableCellFormat cellFormat = new WritableCellFormat();
        cellFormat.setOrientation(jxl.format.Orientation.VERTICAL);

        WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

        WritableFont dataFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat dataFormat = new WritableCellFormat(dataFont);

        titleFormat.setWrap(true);
        titleFormat.setAlignment(Alignment.CENTRE);
        titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

        int linea = 0;
        linea = 6;
        
        for (int l = 0; l < 5; l++) {
            sheet1.setColumnView(l, 15);
        }

        Label label = new Label(1, 1, monFichier.getName().toString(), titleFormat);
        sheet1.addCell(label);

        //Informe de proceso del archivo
        Label sousTitre1 = new Label(0, 5, TituloInformeXLS.INFORME_PROCESO_ARCHIVO + helpers.Constantes.CONCEPTOS_REMUNERATIVOS, titleFormat);
        Label sousTitre2 = new Label(0, 6, TituloInformeXLS.CODIGO_ENTIDADUE, titleFormat);
        Label sousTitre3 = new Label(1, 6, TituloInformeXLS.CODIGO + helpers.Constantes.CONCEPTOS_REMUNERATIVOS, titleFormat);
        Label sousTitre4 = new Label(2, 6, TituloInformeXLS.RESULTADO_OPERACION, titleFormat);
        sheet1.addCell(sousTitre1);
        sheet1.addCell(sousTitre2);
        sheet1.addCell(sousTitre3);
        sheet1.addCell(sousTitre4);
        
        for(LineaInformeCodigo llic:is.getLt()){
            linea++;
            Label codigo_entidad = new Label(0, linea, llic.getCodigoEntidadUE(), dataFormat);
            Label codigo_concepto = new Label(1, linea, llic.getCodigo(), dataFormat);
            Label resultado_operacion = new Label(2, linea, llic.getResultado(), dataFormat);
            sheet1.addCell((WritableCell) codigo_entidad);
            sheet1.addCell((WritableCell) codigo_concepto);
            sheet1.addCell((WritableCell) resultado_operacion);
        }
        
        //resumen del proceso
        linea++;
        linea++;
        Label sousTitre5 = new Label(0, linea, TituloInformeXLS.RESUMEN_PROCESO_ARCHIVO, titleFormat);
        sheet1.addCell(sousTitre5);
        sheet1.mergeCells(0, linea, 4, linea);

        linea++;
        Label rechazados_titulo = new Label(1, linea, InformeXLS.RECHAZADOS, dataFormat);
        Label rechazados = new Label(2, linea, is.getRechazado().toString(), dataFormat);
        sheet1.addCell(rechazados_titulo);
        sheet1.addCell(rechazados);

        linea++;
        Label sin_cambios_titulo = new Label(1, linea, InformeXLS.SIN_CAMBIOS, dataFormat);
        Label sin_cambios = new Label(2, linea, is.getCambio().toString(), dataFormat);
        sheet1.addCell(sin_cambios_titulo);
        sheet1.addCell(sin_cambios);

        linea++;
        Label modificados_titulo = new Label(1, linea, InformeXLS.MODIFICADOS, dataFormat);
        Label modificados = new Label(2, linea, is.getModificado().toString(), dataFormat);
        sheet1.addCell(modificados_titulo);
        sheet1.addCell(modificados);

        linea++;
        Label altas_titulo = new Label(1, linea, InformeXLS.ALTAS, dataFormat);
        Label altas = new Label(2, linea, is.getAlta().toString(), dataFormat);
        sheet1.addCell(altas_titulo);
        sheet1.addCell(altas);

        source.write();
        source.close();

    }

    /** Creacion del archivo Unidad Organica.xls
     * 
     * @param is todos los datos para el archivo
     * @throws IOException
     * @throws WriteException 
     * 
     */
    public void creadorXLSUnidadOrganica(InformeSalida<LineaInformeCodigo> is, String path)
            throws IOException, WriteException {

        File monFichier = new File(path + "/"+ helpers.Constantes.UNIDADES_ORGANICAS + ".xls");

        WritableWorkbook source = Workbook.createWorkbook(monFichier);

        WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10);
        WritableCellFormat arial10format = new WritableCellFormat(arial10font);
        arial10format.setBorder(Border.ALL, BorderLineStyle.MEDIUM);


        WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableCellFormat bold = new WritableCellFormat(wf);
        bold.setWrap(true);

        WritableSheet sheet1 = source.createSheet(monFichier.getName().toString(), 0);
        sheet1.mergeCells(1, 1, 3, 3);
        sheet1.mergeCells(0, 5, 4, 5);

        WritableCellFormat cellFormat = new WritableCellFormat();
        cellFormat.setOrientation(jxl.format.Orientation.VERTICAL);

        WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

        WritableFont dataFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat dataFormat = new WritableCellFormat(dataFont);

        titleFormat.setWrap(true);
        titleFormat.setAlignment(Alignment.CENTRE);
        titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

        int linea = 0;
        linea = 6;

        for (int l = 0; l < 5; l++) {
            sheet1.setColumnView(l, 15);
        }

        Label label = new Label(1, 1, monFichier.getName().toString(), titleFormat);
        sheet1.addCell(label);

        //Informe de proceso del archivo
        Label sousTitre1 = new Label(0, 5, TituloInformeXLS.INFORME_PROCESO_ARCHIVO + helpers.Constantes.UNIDADES_ORGANICAS, titleFormat);
        Label sousTitre2 = new Label(0, 6, TituloInformeXLS.CODIGO_ENTIDADUE, titleFormat);
        Label sousTitre3 = new Label(1, 6, TituloInformeXLS.CODIGO + helpers.Constantes.UNIDADES_ORGANICAS, titleFormat);
        Label sousTitre4 = new Label(2, 6, TituloInformeXLS.RESULTADO_OPERACION, titleFormat);        
        sheet1.addCell(sousTitre1);
        sheet1.addCell(sousTitre2);
        sheet1.addCell(sousTitre3);
        sheet1.addCell(sousTitre4);

        for (LineaInformeCodigo lic : is.getLt()) {
            linea++;
            Label codigo_entidad = new Label(0, linea, lic.getCodigoEntidadUE(), dataFormat);
            Label codigo_unidad_organica = new Label(1, linea, lic.getCodigo(), dataFormat);
            Label resultado_operacion = new Label(2, linea, lic.getResultado(), dataFormat);
            sheet1.addCell((WritableCell) codigo_entidad);
            sheet1.addCell((WritableCell) codigo_unidad_organica);
            sheet1.addCell((WritableCell) resultado_operacion);
        }

        //resumen del proceso
        linea++;
        linea++;
        Label sousTitre5 = new Label(0, linea, TituloInformeXLS.RESUMEN_PROCESO_ARCHIVO, titleFormat);
        sheet1.mergeCells(0, linea, 3, linea);
        sheet1.addCell(sousTitre5);

        linea++;
        Label rechazados_titulo = new Label(1, linea, InformeXLS.RECHAZADOS, dataFormat);
        Label rechazadoss = new Label(2, linea, is.getRechazado().toString(), dataFormat);
        sheet1.addCell(rechazados_titulo);
        sheet1.addCell(rechazadoss);

        linea++;
        Label sin_cambios_titulo = new Label(1, linea, InformeXLS.SIN_CAMBIOS, dataFormat);
        Label sin_cambios = new Label(2, linea, is.getCambio().toString(), dataFormat);
        sheet1.addCell(sin_cambios_titulo);
        sheet1.addCell(sin_cambios);

        linea++;
        Label modificados_titulo = new Label(1, linea, InformeXLS.MODIFICADOS, dataFormat);
        Label modificados = new Label(2, linea, is.getModificado().toString(), dataFormat);
        sheet1.addCell(modificados_titulo);
        sheet1.addCell(modificados);

        linea++;
        Label altas_titulo = new Label(1, linea, InformeXLS.ALTAS, dataFormat);
        Label altas = new Label(2, linea, is.getAlta().toString(), dataFormat);
        sheet1.addCell(altas_titulo);
        sheet1.addCell(altas);

        source.write();
        source.close();

    }

    /** Creacion del archivo Cargo.xls
     * 
     * @param is todos los datos para el archivo
     * @throws IOException
     * @throws WriteException 
     * 
     */
    public void creadorXLSCargo(InformeSalida<LineaInformeCodigo> is, String path) throws IOException, WriteException {

        File monFichier = new File(path + "/"+ helpers.Constantes.CARGOS + ".xls");

        WritableWorkbook source = Workbook.createWorkbook(monFichier);

        WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10);
        WritableCellFormat arial10format = new WritableCellFormat(arial10font);
        arial10format.setBorder(Border.ALL, BorderLineStyle.MEDIUM);


        WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableCellFormat bold = new WritableCellFormat(wf);
        bold.setWrap(true);

        WritableSheet sheet1 = source.createSheet(monFichier.getName().toString(), 0);
        sheet1.mergeCells(1, 1, 3, 3);
        sheet1.mergeCells(0, 5, 4, 5);

        WritableCellFormat cellFormat = new WritableCellFormat();
        cellFormat.setOrientation(jxl.format.Orientation.VERTICAL);

        WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

        WritableFont dataFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat dataFormat = new WritableCellFormat(dataFont);

        titleFormat.setWrap(true);
        titleFormat.setAlignment(Alignment.CENTRE);
        titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

        int linea = 0;
        linea = 6;

        for (int l = 0; l < 5; l++) {
            sheet1.setColumnView(l, 15);
        }

        Label label = new Label(1, 1, monFichier.getName().toString(), titleFormat);
        sheet1.addCell(label);

        //Informe de proceso del archivo
        Label sousTitre1 = new Label(0, 5, TituloInformeXLS.INFORME_PROCESO_ARCHIVO + helpers.Constantes.CARGOS, titleFormat);
        Label sousTitre2 = new Label(0, 6, TituloInformeXLS.CODIGO_ENTIDADUE, titleFormat);
        Label sousTitre3 = new Label(1, 6, TituloInformeXLS.CODIGO + helpers.Constantes.CARGOS, titleFormat);
        Label sousTitre4 = new Label(2, 6, TituloInformeXLS.RESULTADO_OPERACION, titleFormat);
        sheet1.addCell(sousTitre1);
        sheet1.addCell(sousTitre2);
        sheet1.addCell(sousTitre3);
        sheet1.addCell(sousTitre4);

        for (LineaInformeCodigo lic : is.getLt()) {
            linea++;
            Label codigo_entidad = new Label(0, linea, lic.getCodigoEntidadUE(), dataFormat);
            Label codigo_cargo = new Label(1, linea, lic.getCodigo(), dataFormat);
            Label resultado_operacion = new Label(2, linea, lic.getResultado(), dataFormat);
            sheet1.addCell((WritableCell) codigo_entidad);
            sheet1.addCell((WritableCell) codigo_cargo);
            sheet1.addCell((WritableCell) resultado_operacion);
        }

        //resumen del proceso
        linea++;
        linea++;
        Label sousTitre5 = new Label(0, linea, TituloInformeXLS.RESUMEN_PROCESO_ARCHIVO, titleFormat);
        sheet1.mergeCells(0, linea, 3, linea);
        sheet1.addCell(sousTitre5);

        linea++;
        Label rechazados_titulo = new Label(1, linea, InformeXLS.RECHAZADOS, dataFormat);
        Label rechazadoss = new Label(2, linea, is.getRechazado().toString(), dataFormat);
        sheet1.addCell(rechazados_titulo);
        sheet1.addCell(rechazadoss);

        linea++;
        Label sin_cambios_titulo = new Label(1, linea, InformeXLS.SIN_CAMBIOS, dataFormat);
        Label sin_cambios = new Label(2, linea, is.getCambio().toString(), dataFormat);
        sheet1.addCell(sin_cambios_titulo);
        sheet1.addCell(sin_cambios);

        linea++;
        Label modificados_titulo = new Label(1, linea, InformeXLS.MODIFICADOS, dataFormat);
        Label modificados = new Label(2, linea, is.getModificado().toString(), dataFormat);
        sheet1.addCell(modificados_titulo);
        sheet1.addCell(modificados);

        linea++;
        Label altas_titulo = new Label(1, linea, InformeXLS.ALTAS, dataFormat);
        Label altas = new Label(2, linea, is.getAlta().toString(), dataFormat);
        sheet1.addCell(altas_titulo);
        sheet1.addCell(altas);

        source.write();
        source.close();

    }

    /** Creacion del archivo Familiar.xls
     * 
    @param file path
     * @param is todos los datos para el archivo
     * @throws IOException
     * @throws WriteException 
     */
    public void creadorXLSFamiliar(InformeSalida<LineaInformeTipoNumeroDocumento> is, String path) throws IOException, WriteException {

        File monFichier = new File(path + "/"+ helpers.Constantes.FAMILIARES + ".xls");

        WritableWorkbook source = Workbook.createWorkbook(monFichier);

        WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10);
        WritableCellFormat arial10format = new WritableCellFormat(arial10font);
        arial10format.setBorder(Border.ALL, BorderLineStyle.MEDIUM);


        WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableCellFormat bold = new WritableCellFormat(wf);
        bold.setWrap(true);

        WritableSheet sheet1 = source.createSheet(monFichier.getName().toString(), 0);
        sheet1.mergeCells(1, 1, 3, 3);
        sheet1.mergeCells(0, 5, 4, 5);

        WritableCellFormat cellFormat = new WritableCellFormat();
        cellFormat.setOrientation(jxl.format.Orientation.VERTICAL);

        WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

        WritableFont dataFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat dataFormat = new WritableCellFormat(dataFont);

        titleFormat.setWrap(true);
        titleFormat.setAlignment(Alignment.CENTRE);
        titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

        int linea = 0;
        linea = 6;

        for (int l = 0; l < 5; l++) {
            sheet1.setColumnView(l, 15);
        }

        Label label = new Label(1, 1, monFichier.getName().toString(), titleFormat);
        sheet1.addCell(label);

        //Informe de proceso del archivo
        Label sousTitre1 = new Label(0, 5, TituloInformeXLS.INFORME_PROCESO_ARCHIVO + helpers.Constantes.FAMILIARES, titleFormat);
        Label sousTitre2 = new Label(0, 6, TituloInformeXLS.CODIGO_ENTIDADUE, titleFormat);
        Label sousTitre3 = new Label(1, 6, TituloInformeXLS.TIPO_DOCUMENTO, titleFormat);
        Label sousTitre4 = new Label(2, 6, TituloInformeXLS.NUMERO_DOCUMENTO, titleFormat);
        Label sousTitre5 = new Label(3, 6, TituloInformeXLS.RESULTADO_OPERACION, titleFormat);
        sheet1.addCell(sousTitre1);
        sheet1.addCell(sousTitre2);
        sheet1.addCell(sousTitre3);
        sheet1.addCell(sousTitre4);
        sheet1.addCell(sousTitre5);

        for (LineaInformeTipoNumeroDocumento litnd : is.getLt()) {
            linea++;
            Label codigo_entidad = new Label(0, linea, litnd.getCodigoEntidadUE(), dataFormat);
            Label tipoDocumento = new Label(1, linea, litnd.getTipoDocumento(), dataFormat);
            Label numeroDocumento = new Label(2, linea, litnd.getNumeroDocumento(), dataFormat);
            Label resultado_operacion = new Label(3, linea, litnd.getResultado(), dataFormat);
            sheet1.addCell((WritableCell) codigo_entidad);
            sheet1.addCell((WritableCell) tipoDocumento);
            sheet1.addCell((WritableCell) numeroDocumento);
            sheet1.addCell((WritableCell) resultado_operacion);
        }

        //resumen del proceso
        linea++;
        linea++;
        Label sousTitre6 = new Label(0, linea, TituloInformeXLS.RESUMEN_PROCESO_ARCHIVO, titleFormat);
        sheet1.mergeCells(0, linea, 3, linea);
        sheet1.addCell(sousTitre6);

        linea++;
        Label rechazados_titulo = new Label(1, linea, InformeXLS.RECHAZADOS, dataFormat);
        Label rechazadoss = new Label(2, linea, is.getRechazado().toString(), dataFormat);
        sheet1.addCell(rechazados_titulo);
        sheet1.addCell(rechazadoss);

        linea++;
        Label sin_cambios_titulo = new Label(1, linea, InformeXLS.SIN_CAMBIOS, dataFormat);
        Label sin_cambios = new Label(2, linea, is.getCambio().toString(), dataFormat);
        sheet1.addCell(sin_cambios_titulo);
        sheet1.addCell(sin_cambios);

        linea++;
        Label modificados_titulo = new Label(1, linea, InformeXLS.MODIFICADOS, dataFormat);
        Label modificados = new Label(2, linea, is.getModificado().toString(), dataFormat);
        sheet1.addCell(modificados_titulo);
        sheet1.addCell(modificados);

        linea++;
        Label altas_titulo = new Label(1, linea, InformeXLS.ALTAS, dataFormat);
        Label altas = new Label(2, linea, is.getAlta().toString(), dataFormat);
        sheet1.addCell(altas_titulo);
        sheet1.addCell(altas);

        source.write();
        source.close();

    }

    /** Creacion del archivo Titulo.xls
     * 
    @param file path
     * @param is todos los datos para el archivo
     * @throws IOException
     * @throws WriteException 
     */
    public void creadorXLSTitulo(InformeSalida<LineaInformeTipoNumeroDocumento> is, String path) throws IOException, WriteException {

        File monFichier = new File(path + "/"+ helpers.Constantes.TITULOS + ".xls");

        WritableWorkbook source = Workbook.createWorkbook(monFichier);

        WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10);
        WritableCellFormat arial10format = new WritableCellFormat(arial10font);
        arial10format.setBorder(Border.ALL, BorderLineStyle.MEDIUM);


        WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableCellFormat bold = new WritableCellFormat(wf);
        bold.setWrap(true);

        WritableSheet sheet1 = source.createSheet(monFichier.getName().toString(), 0);
        sheet1.mergeCells(1, 1, 3, 3);
        sheet1.mergeCells(0, 5, 4, 5);

        WritableCellFormat cellFormat = new WritableCellFormat();
        cellFormat.setOrientation(jxl.format.Orientation.VERTICAL);

        WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

        WritableFont dataFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat dataFormat = new WritableCellFormat(dataFont);

        titleFormat.setWrap(true);
        titleFormat.setAlignment(Alignment.CENTRE);
        titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

        int linea = 0;
        linea = 6;

        for (int l = 0; l < 5; l++) {
            sheet1.setColumnView(l, 15);
        }

        Label label = new Label(1, 1, monFichier.getName().toString(), titleFormat);
        sheet1.addCell(label);

        //Informe de proceso del archivo
        Label sousTitre1 = new Label(0, 5, TituloInformeXLS.INFORME_PROCESO_ARCHIVO + helpers.Constantes.TITULOS, titleFormat);
        Label sousTitre2 = new Label(0, 6, TituloInformeXLS.CODIGO_ENTIDADUE, titleFormat);
        Label sousTitre3 = new Label(1, 6, TituloInformeXLS.TIPO_DOCUMENTO, titleFormat);
        Label sousTitre4 = new Label(2, 6, TituloInformeXLS.NUMERO_DOCUMENTO, titleFormat);
        Label sousTitre5 = new Label(3, 6, TituloInformeXLS.RESULTADO_OPERACION, titleFormat);
        sheet1.addCell(sousTitre1);
        sheet1.addCell(sousTitre2);
        sheet1.addCell(sousTitre3);
        sheet1.addCell(sousTitre4);
        sheet1.addCell(sousTitre5);

        for (LineaInformeTipoNumeroDocumento litnd : is.getLt()) {
            linea++;
            Label codigo_entidad = new Label(0, linea, litnd.getCodigoEntidadUE(), dataFormat);
            Label tipoDocumento = new Label(1, linea, litnd.getTipoDocumento(), dataFormat);
            Label numeroDocumento = new Label(2, linea, litnd.getNumeroDocumento(), dataFormat);
            Label resultado_operacion = new Label(3, linea, litnd.getResultado(), dataFormat);
            sheet1.addCell((WritableCell) codigo_entidad);
            sheet1.addCell((WritableCell) tipoDocumento);
            sheet1.addCell((WritableCell) numeroDocumento);
            sheet1.addCell((WritableCell) resultado_operacion);
        }

        //resumen del proceso
        linea++;
        linea++;
        Label sousTitre6 = new Label(0, linea, TituloInformeXLS.RESUMEN_PROCESO_ARCHIVO, titleFormat);
        sheet1.mergeCells(0, linea, 3, linea);
        sheet1.addCell(sousTitre6);

        linea++;
        Label rechazados_titulo = new Label(1, linea, InformeXLS.RECHAZADOS, dataFormat);
        Label rechazadoss = new Label(2, linea, is.getRechazado().toString(), dataFormat);
        sheet1.addCell(rechazados_titulo);
        sheet1.addCell(rechazadoss);

        linea++;
        Label sin_cambios_titulo = new Label(1, linea, InformeXLS.SIN_CAMBIOS, dataFormat);
        Label sin_cambios = new Label(2, linea, is.getCambio().toString(), dataFormat);
        sheet1.addCell(sin_cambios_titulo);
        sheet1.addCell(sin_cambios);

        linea++;
        Label modificados_titulo = new Label(1, linea, InformeXLS.MODIFICADOS, dataFormat);
        Label modificados = new Label(2, linea, is.getModificado().toString(), dataFormat);
        sheet1.addCell(modificados_titulo);
        sheet1.addCell(modificados);

        linea++;
        Label altas_titulo = new Label(1, linea, InformeXLS.ALTAS, dataFormat);
        Label altas = new Label(2, linea,is.getAlta().toString(), dataFormat);
        sheet1.addCell(altas_titulo);
        sheet1.addCell(altas);

        source.write();
        source.close();

    }

    public void creadorXLSCertificacion(InformeSalida<LineaInformeTipoNumeroDocumento> is, String path) throws IOException, WriteException {

        File monFichier = new File(path + "/"+ helpers.Constantes.CERTIFICACIONES + ".xls");

        WritableWorkbook source = Workbook.createWorkbook(monFichier);

        WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10);
        WritableCellFormat arial10format = new WritableCellFormat(arial10font);
        arial10format.setBorder(Border.ALL, BorderLineStyle.MEDIUM);


        WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableCellFormat bold = new WritableCellFormat(wf);
        bold.setWrap(true);

        WritableSheet sheet1 = source.createSheet(monFichier.getName().toString(), 0);
        sheet1.mergeCells(1, 1, 3, 3);
        sheet1.mergeCells(0, 5, 4, 5);

        WritableCellFormat cellFormat = new WritableCellFormat();
        cellFormat.setOrientation(jxl.format.Orientation.VERTICAL);

        WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

        WritableFont dataFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat dataFormat = new WritableCellFormat(dataFont);

        titleFormat.setWrap(true);
        titleFormat.setAlignment(Alignment.CENTRE);
        titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

        int linea = 0;
        linea = 6;

        for (int l = 0; l < 5; l++) {
            sheet1.setColumnView(l, 15);
        }

        Label label = new Label(1, 1, monFichier.getName().toString(), titleFormat);
        sheet1.addCell(label);

        //Informe de proceso del archivo
        Label sousTitre1 = new Label(0, 5, TituloInformeXLS.INFORME_PROCESO_ARCHIVO + helpers.Constantes.CERTIFICACIONES, titleFormat);
        Label sousTitre2 = new Label(0, 6, TituloInformeXLS.CODIGO_ENTIDADUE, titleFormat);
        Label sousTitre3 = new Label(1, 6, TituloInformeXLS.TIPO_DOCUMENTO, titleFormat);
        Label sousTitre4 = new Label(2, 6, TituloInformeXLS.NUMERO_DOCUMENTO, titleFormat);
        Label sousTitre5 = new Label(3, 6, TituloInformeXLS.RESULTADO_OPERACION, titleFormat);
        sheet1.addCell(sousTitre1);
        sheet1.addCell(sousTitre2);
        sheet1.addCell(sousTitre3);
        sheet1.addCell(sousTitre4);
        sheet1.addCell(sousTitre5);

        for (LineaInformeTipoNumeroDocumento listCurrent : is.getLt()) {
            linea++;
            Label codigo_entidad = new Label(0, linea, listCurrent.getCodigoEntidadUE(), dataFormat);
            Label tipoDocumento = new Label(1, linea, listCurrent.getTipoDocumento(), dataFormat);
            Label numeroDocumento = new Label(2, linea, listCurrent.getNumeroDocumento(), dataFormat);
            Label resultado_operacion = new Label(3, linea, listCurrent.getResultado(), dataFormat);
            sheet1.addCell((WritableCell) codigo_entidad);
            sheet1.addCell((WritableCell) tipoDocumento);
            sheet1.addCell((WritableCell) numeroDocumento);
            sheet1.addCell((WritableCell) resultado_operacion);
        }

        //resumen del proceso
        linea++;
        linea++;
        Label sousTitre6 = new Label(0, linea, TituloInformeXLS.RESUMEN_PROCESO_ARCHIVO, titleFormat);
        sheet1.mergeCells(0, linea, 3, linea);
        sheet1.addCell(sousTitre6);

        linea++;
        Label rechazados_titulo = new Label(1, linea, InformeXLS.RECHAZADOS, dataFormat);
        Label rechazadoss = new Label(2, linea, is.getRechazado().toString(), dataFormat);
        sheet1.addCell(rechazados_titulo);
        sheet1.addCell(rechazadoss);

        linea++;
        Label sin_cambios_titulo = new Label(1, linea, InformeXLS.SIN_CAMBIOS, dataFormat);
        Label sin_cambios = new Label(2, linea, is.getCambio().toString(), dataFormat);
        sheet1.addCell(sin_cambios_titulo);
        sheet1.addCell(sin_cambios);

        linea++;
        Label modificados_titulo = new Label(1, linea, InformeXLS.MODIFICADOS, dataFormat);
        Label modificados = new Label(2, linea, is.getModificado().toString(), dataFormat);
        sheet1.addCell(modificados_titulo);
        sheet1.addCell(modificados);

        linea++;
        Label altas_titulo = new Label(1, linea, InformeXLS.ALTAS, dataFormat);
        Label altas = new Label(2, linea,is.getAlta().toString(), dataFormat);
        sheet1.addCell(altas_titulo);
        sheet1.addCell(altas);

        source.write();
        source.close();

    }

    public void creadorXLSCurso(InformeSalida<LineaInformeTipoNumeroDocumento> is, String path)
            throws IOException, WriteException {

        File monFichier = new File(path + "/"+ helpers.Constantes.CURSOS + ".xls");

        WritableWorkbook source = Workbook.createWorkbook(monFichier);

        WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10);
        WritableCellFormat arial10format = new WritableCellFormat(arial10font);
        arial10format.setBorder(Border.ALL, BorderLineStyle.MEDIUM);


        WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableCellFormat bold = new WritableCellFormat(wf);
        bold.setWrap(true);

        WritableSheet sheet1 = source.createSheet(monFichier.getName().toString(), 0);
        sheet1.mergeCells(1, 1, 3, 3);
        sheet1.mergeCells(0, 5, 4, 5);

        WritableCellFormat cellFormat = new WritableCellFormat();
        cellFormat.setOrientation(jxl.format.Orientation.VERTICAL);

        WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

        WritableFont dataFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat dataFormat = new WritableCellFormat(dataFont);

        titleFormat.setWrap(true);
        titleFormat.setAlignment(Alignment.CENTRE);
        titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

        int linea = 0;
        linea = 6;

        for (int l = 0; l < 5; l++) {
            sheet1.setColumnView(l, 15);
        }

        Label label = new Label(1, 1, monFichier.getName().toString(), titleFormat);
        sheet1.addCell(label);

        //Informe de proceso del archivo
        Label sousTitre1 = new Label(0, 5, TituloInformeXLS.INFORME_PROCESO_ARCHIVO + helpers.Constantes.CURSOS, titleFormat);
        Label sousTitre2 = new Label(0, 6, TituloInformeXLS.CODIGO_ENTIDADUE, titleFormat);
        Label sousTitre3 = new Label(1, 6, TituloInformeXLS.TIPO_DOCUMENTO, titleFormat);
        Label sousTitre4 = new Label(2, 6, TituloInformeXLS.NUMERO_DOCUMENTO, titleFormat);
        Label sousTitre5 = new Label(3, 6, TituloInformeXLS.RESULTADO_OPERACION, titleFormat);
        sheet1.addCell(sousTitre1);
        sheet1.addCell(sousTitre2);
        sheet1.addCell(sousTitre3);
        sheet1.addCell(sousTitre4);
        sheet1.addCell(sousTitre5);

        for (LineaInformeTipoNumeroDocumento litnd : is.getLt()) {
            linea++;
            Label codigo_entidad = new Label(0, linea, litnd.getCodigoEntidadUE(), dataFormat);
            Label tipoDocumento = new Label(1, linea, litnd.getTipoDocumento(), dataFormat);
            Label numeroDocumento = new Label(2, linea, litnd.getNumeroDocumento(), dataFormat);
            Label resultado_operacion = new Label(3, linea, litnd.getResultado(), dataFormat);
            sheet1.addCell((WritableCell)codigo_entidad);
            sheet1.addCell((WritableCell) tipoDocumento);
            sheet1.addCell((WritableCell) numeroDocumento);
            sheet1.addCell((WritableCell) resultado_operacion);
        }

        //resumen del proceso
        linea++;
        linea++;
        Label sousTitre6 = new Label(0, linea, TituloInformeXLS.RESUMEN_PROCESO_ARCHIVO, titleFormat);
        sheet1.mergeCells(0, linea, 3, linea);
        sheet1.addCell(sousTitre6);

        linea++;
        Label rechazados_titulo = new Label(1, linea, InformeXLS.RECHAZADOS, dataFormat);
        Label rechazadoss = new Label(2, linea, is.getRechazado().toString(), dataFormat);
        sheet1.addCell(rechazados_titulo);
        sheet1.addCell(rechazadoss);

        linea++;
        Label sin_cambios_titulo = new Label(1, linea, InformeXLS.SIN_CAMBIOS, dataFormat);
        Label sin_cambios = new Label(2, linea, is.getCambio().toString(), dataFormat);
        sheet1.addCell(sin_cambios_titulo);
        sheet1.addCell(sin_cambios);

        linea++;
        Label modificados_titulo = new Label(1, linea, InformeXLS.MODIFICADOS, dataFormat);
        Label modificados = new Label(2, linea, is.getModificado().toString(), dataFormat);
        sheet1.addCell(modificados_titulo);
        sheet1.addCell(modificados);

        linea++;
        Label altas_titulo = new Label(1, linea, InformeXLS.ALTAS, dataFormat);
        Label altas = new Label(2, linea, is.getAlta().toString(), dataFormat);
        sheet1.addCell(altas_titulo);
        sheet1.addCell(altas);


        source.write();
        source.close();

    }

    public void creadorXLSAntecedentLaboral(InformeSalida<LineaInformeTipoNumeroDocumento> is, String path)
            throws IOException, WriteException {

        File monFichier = new File(path + "/"+ helpers.Constantes.ANTECEDENTES_LABORALES + ".xls");

        WritableWorkbook source = Workbook.createWorkbook(monFichier);

        WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10);
        WritableCellFormat arial10format = new WritableCellFormat(arial10font);
        arial10format.setBorder(Border.ALL, BorderLineStyle.MEDIUM);


        WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableCellFormat bold = new WritableCellFormat(wf);
        bold.setWrap(true);

        WritableSheet sheet1 = source.createSheet(monFichier.getName().toString(), 0);
        sheet1.mergeCells(1, 1, 3, 3);
        sheet1.mergeCells(0, 5, 4, 5);

        WritableCellFormat cellFormat = new WritableCellFormat();
        cellFormat.setOrientation(jxl.format.Orientation.VERTICAL);

        WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

        WritableFont dataFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat dataFormat = new WritableCellFormat(dataFont);

        titleFormat.setWrap(true);
        titleFormat.setAlignment(Alignment.CENTRE);
        titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

        int linea = 0;
        linea = 6;

        for (int l = 0; l < 5; l++) {
            sheet1.setColumnView(l, 15);
        }

        Label label = new Label(1, 1, monFichier.getName().toString(), titleFormat);
        sheet1.addCell(label);

        //Informe de proceso del archivo
        Label sousTitre1 = new Label(0, 5, TituloInformeXLS.INFORME_PROCESO_ARCHIVO + helpers.Constantes.ANTECEDENTES_LABORALES, titleFormat);
        Label sousTitre2 = new Label(0, 6, TituloInformeXLS.CODIGO_ENTIDADUE, titleFormat);
        Label sousTitre3 = new Label(1, 6, TituloInformeXLS.TIPO_DOCUMENTO, titleFormat);
        Label sousTitre4 = new Label(2, 6, TituloInformeXLS.NUMERO_DOCUMENTO, titleFormat);
        Label sousTitre5 = new Label(3, 6, TituloInformeXLS.RESULTADO_OPERACION, titleFormat);        
        sheet1.addCell(sousTitre1);
        sheet1.addCell(sousTitre2);
        sheet1.addCell(sousTitre3);
        sheet1.addCell(sousTitre4);
        sheet1.addCell(sousTitre5);

        for (LineaInformeTipoNumeroDocumento litnd : is.getLt()) {
            linea++;
            Label codigo_entidad = new Label(0, linea, litnd.getCodigoEntidadUE(), dataFormat);
            Label tipoDocumento = new Label(1, linea, litnd.getTipoDocumento(), dataFormat);
            Label numeroDocumento = new Label(2, linea, litnd.getNumeroDocumento(), dataFormat);
            Label resultado_operacion = new Label(3, linea, litnd.getResultado(), dataFormat);
            sheet1.addCell((WritableCell) codigo_entidad);
            sheet1.addCell((WritableCell) tipoDocumento);
            sheet1.addCell((WritableCell) numeroDocumento);
            sheet1.addCell((WritableCell) resultado_operacion);
        }

        //resumen del proceso
        linea++;
        linea++;
        Label sousTitre6 = new Label(0, linea, TituloInformeXLS.RESUMEN_PROCESO_ARCHIVO, titleFormat);
        sheet1.mergeCells(0, linea, 3, linea);
        sheet1.addCell(sousTitre6);

        linea++;
        Label rechazados_titulo = new Label(1, linea, InformeXLS.RECHAZADOS, dataFormat);
        Label rechazadoss = new Label(2, linea, is.getRechazado().toString(), dataFormat);
        sheet1.addCell(rechazados_titulo);
        sheet1.addCell(rechazadoss);

        linea++;
        Label sin_cambios_titulo = new Label(1, linea, InformeXLS.SIN_CAMBIOS, dataFormat);
        Label sin_cambios = new Label(2, linea, is.getCambio().toString(), dataFormat);
        sheet1.addCell(sin_cambios_titulo);
        sheet1.addCell(sin_cambios);

        linea++;
        Label modificados_titulo = new Label(1, linea, InformeXLS.MODIFICADOS, dataFormat);
        Label modificados = new Label(2, linea, is.getModificado().toString(), dataFormat);
        sheet1.addCell(modificados_titulo);
        sheet1.addCell(modificados);

        linea++;
        Label altas_titulo = new Label(1, linea, InformeXLS.ALTAS, dataFormat);
        Label altas = new Label(2, linea,is.getAlta().toString(), dataFormat);
        sheet1.addCell(altas_titulo);
        sheet1.addCell(altas);


        source.write();
        source.close();

    }

    public void creadorXLSMeritoDemerito(InformeSalida<LineaInformeTipoNumeroDocumento> is, String path) throws IOException, WriteException {

        File monFichier = new File(path + "/"+ helpers.Constantes.MERITOS_DEMERITOS + ".xls");

        WritableWorkbook source = Workbook.createWorkbook(monFichier);

        WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10);
        WritableCellFormat arial10format = new WritableCellFormat(arial10font);
        arial10format.setBorder(Border.ALL, BorderLineStyle.MEDIUM);


        WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableCellFormat bold = new WritableCellFormat(wf);
        bold.setWrap(true);

        WritableSheet sheet1 = source.createSheet(monFichier.getName().toString(), 0);
        sheet1.mergeCells(1, 1, 3, 3);
        sheet1.mergeCells(0, 5, 4, 5);

        WritableCellFormat cellFormat = new WritableCellFormat();
        cellFormat.setOrientation(jxl.format.Orientation.VERTICAL);

        WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

        WritableFont dataFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat dataFormat = new WritableCellFormat(dataFont);

        titleFormat.setWrap(true);
        titleFormat.setAlignment(Alignment.CENTRE);
        titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

        int linea = 0;
        linea = 6;

        for (int l = 0; l < 5; l++) {
            sheet1.setColumnView(l, 15);
        }

        Label label = new Label(1, 1, monFichier.getName().toString(), titleFormat);
        sheet1.addCell(label);

        //Informe de proceso del archivo
        Label sousTitre1 = new Label(0, 5, TituloInformeXLS.INFORME_PROCESO_ARCHIVO + helpers.Constantes.MERITOS_DEMERITOS, titleFormat);
        Label sousTitre2 = new Label(0, 6, TituloInformeXLS.CODIGO_ENTIDADUE, titleFormat);
        Label sousTitre3 = new Label(1, 6, TituloInformeXLS.TIPO_DOCUMENTO, titleFormat);
        Label sousTitre4 = new Label(2, 6, TituloInformeXLS.NUMERO_DOCUMENTO, titleFormat);
        Label sousTitre5 = new Label(3, 6, TituloInformeXLS.RESULTADO_OPERACION, titleFormat);
        sheet1.addCell(sousTitre1);
        sheet1.addCell(sousTitre2);
        sheet1.addCell(sousTitre3);
        sheet1.addCell(sousTitre4);
        sheet1.addCell(sousTitre5);

        for (LineaInformeTipoNumeroDocumento litnd : is.getLt()) {
            linea++;
            Label codigo_entidad = new Label(0, linea, litnd.getCodigoEntidadUE(), dataFormat);
            Label tipoDocumento = new Label(1, linea, litnd.getTipoDocumento(), dataFormat);
            Label numeroDocumento = new Label(2, linea, litnd.getNumeroDocumento(), dataFormat);
            Label resultado_operacion = new Label(3, linea, litnd.getResultado(), dataFormat);
            sheet1.addCell((WritableCell) codigo_entidad);
            sheet1.addCell((WritableCell) tipoDocumento);
            sheet1.addCell((WritableCell) numeroDocumento);
            sheet1.addCell((WritableCell) resultado_operacion);
        }

        //resumen del proceso
        linea++;
        linea++;
        Label sousTitre6 = new Label(0, linea, TituloInformeXLS.RESUMEN_PROCESO_ARCHIVO, titleFormat);
        sheet1.mergeCells(0, linea, 3, linea);
        sheet1.addCell(sousTitre6);

        linea++;
        Label rechazados_titulo = new Label(1, linea, InformeXLS.RECHAZADOS, dataFormat);
        Label rechazadoss = new Label(2, linea, is.getRechazado().toString(), dataFormat);
        sheet1.addCell(rechazados_titulo);
        sheet1.addCell(rechazadoss);

        linea++;
        Label sin_cambios_titulo = new Label(1, linea, InformeXLS.SIN_CAMBIOS, dataFormat);
        Label sin_cambios = new Label(2, linea, is.getCambio().toString(), dataFormat);
        sheet1.addCell(sin_cambios_titulo);
        sheet1.addCell(sin_cambios);

        linea++;
        Label modificados_titulo = new Label(1, linea, InformeXLS.MODIFICADOS, dataFormat);
        Label modificados = new Label(2, linea, is.getModificado().toString(), dataFormat);
        sheet1.addCell(modificados_titulo);
        sheet1.addCell(modificados);

        linea++;
        Label altas_titulo = new Label(1, linea, InformeXLS.ALTAS, dataFormat);
        Label altas = new Label(2, linea, is.getAlta().toString(), dataFormat);
        sheet1.addCell(altas_titulo);
        sheet1.addCell(altas);

        System.out.println("---------- alta merito demerito informe xls "+is.getAlta());
        
        source.write();
        source.close();

    }

    public void creadorXLSProduccionIntelectual(InformeSalida<LineaInformeTipoNumeroDocumento> is, String path) throws IOException, WriteException {

        File monFichier = new File(path + "/"+ helpers.Constantes.PRODUCIONES_INTELECTUALES + ".xls");

        WritableWorkbook source = Workbook.createWorkbook(monFichier);

        WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10);
        WritableCellFormat arial10format = new WritableCellFormat(arial10font);
        arial10format.setBorder(Border.ALL, BorderLineStyle.MEDIUM);


        WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableCellFormat bold = new WritableCellFormat(wf);
        bold.setWrap(true);

        WritableSheet sheet1 = source.createSheet(monFichier.getName().toString(), 0);
        sheet1.mergeCells(1, 1, 3, 3);
        sheet1.mergeCells(0, 5, 4, 5);

        WritableCellFormat cellFormat = new WritableCellFormat();
        cellFormat.setOrientation(jxl.format.Orientation.VERTICAL);

        WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

        WritableFont dataFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat dataFormat = new WritableCellFormat(dataFont);

        titleFormat.setWrap(true);
        titleFormat.setAlignment(Alignment.CENTRE);
        titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

        int linea = 0;
        linea = 6;

        for (int l = 0; l < 5; l++) {
            sheet1.setColumnView(l, 15);
        }

        Label label = new Label(1, 1, monFichier.getName().toString(), titleFormat);
        sheet1.addCell(label);

        //Informe de proceso del archivo
        Label sousTitre1 = new Label(0, 5, TituloInformeXLS.INFORME_PROCESO_ARCHIVO + helpers.Constantes.PRODUCIONES_INTELECTUALES, titleFormat);
        Label sousTitre2 = new Label(0, 6, TituloInformeXLS.CODIGO_ENTIDADUE, titleFormat);
        Label sousTitre3 = new Label(1, 6, TituloInformeXLS.TIPO_DOCUMENTO, titleFormat);
        Label sousTitre4 = new Label(2, 6, TituloInformeXLS.NUMERO_DOCUMENTO, titleFormat);
        Label sousTitre5 = new Label(3, 6, TituloInformeXLS.RESULTADO_OPERACION, titleFormat);
        sheet1.addCell(sousTitre1);
        sheet1.addCell(sousTitre2);
        sheet1.addCell(sousTitre3);
        sheet1.addCell(sousTitre4);
        sheet1.addCell(sousTitre5);

        for (LineaInformeTipoNumeroDocumento litnd : is.getLt()) {
            linea++;
            Label codigo_entidad = new Label(0, linea, litnd.getCodigoEntidadUE(), dataFormat);
            Label tipoDocumento = new Label(1, linea, litnd.getTipoDocumento(), dataFormat);
            Label numeroDocumento = new Label(2, linea, litnd.getNumeroDocumento(), dataFormat);
            Label resultado_operacion = new Label(3, linea, litnd.getResultado(), dataFormat);
            sheet1.addCell((WritableCell) codigo_entidad);
            sheet1.addCell((WritableCell) tipoDocumento);
            sheet1.addCell((WritableCell) numeroDocumento);
            sheet1.addCell((WritableCell) resultado_operacion);
        }

        //resumen del proceso
        linea++;
        linea++;
        Label sousTitre6 = new Label(0, linea, TituloInformeXLS.RESUMEN_PROCESO_ARCHIVO, titleFormat);
        sheet1.mergeCells(0, linea, 3, linea);
        sheet1.addCell(sousTitre6);

        linea++;
        Label rechazados_titulo = new Label(1, linea, InformeXLS.RECHAZADOS, dataFormat);
        Label rechazadoss = new Label(2, linea, is.getRechazado().toString(), dataFormat);
        sheet1.addCell(rechazados_titulo);
        sheet1.addCell(rechazadoss);

        linea++;
        Label sin_cambios_titulo = new Label(1, linea, InformeXLS.SIN_CAMBIOS, dataFormat);
        Label sin_cambios = new Label(2, linea, is.getCambio().toString(), dataFormat);
        sheet1.addCell(sin_cambios_titulo);
        sheet1.addCell(sin_cambios);

        linea++;
        Label modificados_titulo = new Label(1, linea, InformeXLS.MODIFICADOS, dataFormat);
        Label modificados = new Label(2, linea, is.getModificado().toString(), dataFormat);
        sheet1.addCell(modificados_titulo);
        sheet1.addCell(modificados);

        linea++;
        Label altas_titulo = new Label(1, linea, InformeXLS.ALTAS, dataFormat);
        Label altas = new Label(2, linea,is.getAlta().toString(), dataFormat);
        sheet1.addCell(altas_titulo);
        sheet1.addCell(altas);


        source.write();
        source.close();

    }

    public void creadorXLSEvaluacion(InformeSalida<LineaInformeTipoNumeroDocumento> is, String path)
            throws IOException, WriteException {

        File monFichier = new File(path + "/"+ helpers.Constantes.EVALUACIONES + ".xls");

        WritableWorkbook source = Workbook.createWorkbook(monFichier);

        WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10);
        WritableCellFormat arial10format = new WritableCellFormat(arial10font);
        arial10format.setBorder(Border.ALL, BorderLineStyle.MEDIUM);


        WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableCellFormat bold = new WritableCellFormat(wf);
        bold.setWrap(true);

        WritableSheet sheet1 = source.createSheet(monFichier.getName().toString(), 0);
        sheet1.mergeCells(1, 1, 3, 3);
        sheet1.mergeCells(0, 5, 4, 5);

        WritableCellFormat cellFormat = new WritableCellFormat();
        cellFormat.setOrientation(jxl.format.Orientation.VERTICAL);

        WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

        WritableFont dataFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat dataFormat = new WritableCellFormat(dataFont);

        titleFormat.setWrap(true);
        titleFormat.setAlignment(Alignment.CENTRE);
        titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

        int linea = 0;
        linea = 6;

        for (int l = 0; l < 5; l++) {
            sheet1.setColumnView(l, 15);
        }

        Label label = new Label(1, 1, monFichier.getName().toString(), titleFormat);
        sheet1.addCell(label);

        //Informe de proceso del archivo
        Label sousTitre1 = new Label(0, 5, TituloInformeXLS.INFORME_PROCESO_ARCHIVO + helpers.Constantes.EVALUACIONES, titleFormat);
        Label sousTitre2 = new Label(0, 6, TituloInformeXLS.CODIGO_ENTIDADUE, titleFormat);
        Label sousTitre3 = new Label(1, 6, TituloInformeXLS.TIPO_DOCUMENTO, titleFormat);
        Label sousTitre4 = new Label(2, 6, TituloInformeXLS.NUMERO_DOCUMENTO, titleFormat);
        Label sousTitre5 = new Label(3, 6, TituloInformeXLS.RESULTADO_OPERACION, titleFormat);
        sheet1.addCell(sousTitre1);
        sheet1.addCell(sousTitre2);
        sheet1.addCell(sousTitre3);
        sheet1.addCell(sousTitre4);
        sheet1.addCell(sousTitre5);

        for (LineaInformeTipoNumeroDocumento litnd : is.getLt()) {
            linea++;
            Label codigo_entidad = new Label(0, linea, litnd.getCodigoEntidadUE(), dataFormat);
            Label tipoDocumento = new Label(1, linea, litnd.getTipoDocumento(), dataFormat);
            Label numeroDocumento = new Label(2, linea, litnd.getNumeroDocumento(), dataFormat);
            Label resultado_operacion = new Label(3, linea, litnd.getResultado(), dataFormat);
            sheet1.addCell((WritableCell) codigo_entidad);
            sheet1.addCell((WritableCell) tipoDocumento);
            sheet1.addCell((WritableCell) numeroDocumento);
            sheet1.addCell((WritableCell) resultado_operacion);
        }

        //resumen del proceso
        linea++;
        linea++;
        Label sousTitre6 = new Label(0, linea, TituloInformeXLS.RESUMEN_PROCESO_ARCHIVO, titleFormat);
        sheet1.mergeCells(0, linea, 3, linea);
        sheet1.addCell(sousTitre6);

        linea++;
        Label rechazados_titulo = new Label(1, linea, InformeXLS.RECHAZADOS, dataFormat);
        Label rechazadoss = new Label(2, linea, is.getRechazado().toString(), dataFormat);
        sheet1.addCell(rechazados_titulo);
        sheet1.addCell(rechazadoss);

        linea++;
        Label sin_cambios_titulo = new Label(1, linea, InformeXLS.SIN_CAMBIOS, dataFormat);
        Label sin_cambios = new Label(2, linea, is.getCambio().toString(), dataFormat);
        sheet1.addCell(sin_cambios_titulo);
        sheet1.addCell(sin_cambios);

        linea++;
        Label modificados_titulo = new Label(1, linea, InformeXLS.MODIFICADOS, dataFormat);
        Label modificados = new Label(2, linea, is.getModificado().toString(), dataFormat);
        sheet1.addCell(modificados_titulo);
        sheet1.addCell(modificados);

        linea++;
        Label altas_titulo = new Label(1, linea, InformeXLS.ALTAS, dataFormat);
        Label altas = new Label(2, linea,is.getAlta().toString(), dataFormat);
        sheet1.addCell(altas_titulo);
        sheet1.addCell(altas);


        source.write();
        source.close();

    }

    public void creadorXLSAusLicPersonal(InformeSalida<LineaInformeTipoNumeroDocumento> is, String path)
            throws IOException, WriteException {

        File monFichier = new File(path + "/"+ helpers.Constantes.AUSENCIAS_LICENCIAS + ".xls");

        WritableWorkbook source = Workbook.createWorkbook(monFichier);

        WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10);
        WritableCellFormat arial10format = new WritableCellFormat(arial10font);
        arial10format.setBorder(Border.ALL, BorderLineStyle.MEDIUM);


        WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableCellFormat bold = new WritableCellFormat(wf);
        bold.setWrap(true);

        WritableSheet sheet1 = source.createSheet(monFichier.getName().toString(), 0);
        sheet1.mergeCells(1, 1, 3, 3);
        sheet1.mergeCells(0, 5, 4, 5);

        WritableCellFormat cellFormat = new WritableCellFormat();
        cellFormat.setOrientation(jxl.format.Orientation.VERTICAL);

        WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

        WritableFont dataFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat dataFormat = new WritableCellFormat(dataFont);

        titleFormat.setWrap(true);
        titleFormat.setAlignment(Alignment.CENTRE);
        titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

        int linea = 0;
        linea = 6;

        for (int l = 0; l < 5; l++) {
            sheet1.setColumnView(l, 15);
        }

        Label label = new Label(1, 1, monFichier.getName().toString(), titleFormat);
        sheet1.addCell(label);

        //Informe de proceso del archivo
        Label sousTitre1 = new Label(0, 5, TituloInformeXLS.INFORME_PROCESO_ARCHIVO + helpers.Constantes.AUSENCIAS_LICENCIAS, titleFormat);
        Label sousTitre2 = new Label(0, 6, TituloInformeXLS.CODIGO_ENTIDADUE, titleFormat);
        Label sousTitre3 = new Label(1, 6, TituloInformeXLS.TIPO_DOCUMENTO, titleFormat);
        Label sousTitre4 = new Label(2, 6, TituloInformeXLS.NUMERO_DOCUMENTO, titleFormat);
        Label sousTitre5 = new Label(3, 6, TituloInformeXLS.RESULTADO_OPERACION, titleFormat);
        sheet1.addCell(sousTitre1);
        sheet1.addCell(sousTitre2);
        sheet1.addCell(sousTitre3);
        sheet1.addCell(sousTitre4);
        sheet1.addCell(sousTitre5);

        for (LineaInformeTipoNumeroDocumento litnd : is.getLt()) {
            linea++;
            Label codigo_entidad = new Label(0, linea, litnd.getCodigoEntidadUE(), dataFormat);
            Label tipoDocumento = new Label(1, linea, litnd.getTipoDocumento(), dataFormat);
            Label numeroDocumento = new Label(2, linea, litnd.getNumeroDocumento(), dataFormat);
            Label resultado_operacion = new Label(3, linea, litnd.getResultado(), dataFormat);
            sheet1.addCell((WritableCell) codigo_entidad);
            sheet1.addCell((WritableCell) tipoDocumento);
            sheet1.addCell((WritableCell) numeroDocumento);
            sheet1.addCell((WritableCell) resultado_operacion);

        }

        //resumen del proceso
        linea++;
        linea++;
        Label sousTitre6 = new Label(0, linea, TituloInformeXLS.RESUMEN_PROCESO_ARCHIVO, titleFormat);
        sheet1.mergeCells(0, linea, 3, linea);
        sheet1.addCell(sousTitre6);

        linea++;
        Label rechazados_titulo = new Label(1, linea, InformeXLS.RECHAZADOS, dataFormat);
        Label rechazadoss = new Label(2, linea, is.getRechazado().toString(), dataFormat);
        sheet1.addCell(rechazados_titulo);
        sheet1.addCell(rechazadoss);

        linea++;
        Label sin_cambios_titulo = new Label(1, linea, InformeXLS.SIN_CAMBIOS, dataFormat);
        Label sin_cambios = new Label(2, linea, is.getCambio().toString(), dataFormat);
        sheet1.addCell(sin_cambios_titulo);
        sheet1.addCell(sin_cambios);

        linea++;
        Label modificados_titulo = new Label(1, linea, InformeXLS.MODIFICADOS, dataFormat);
        Label modificados = new Label(2, linea, is.getModificado().toString(), dataFormat);
        sheet1.addCell(modificados_titulo);
        sheet1.addCell(modificados);

        linea++;
        Label altas_titulo = new Label(1, linea, InformeXLS.ALTAS, dataFormat);
        Label altas = new Label(2, linea,is.getAlta().toString(), dataFormat);
        sheet1.addCell(altas_titulo);
        sheet1.addCell(altas);


        source.write();
        source.close();

    }

    public void creadorXLSConstanciaDocumental(InformeSalida<LineaInformeTipoNumeroDocumento> is, String path)
            throws IOException, WriteException {

        File monFichier = new File(path + "/"+ helpers.Constantes.CONSTANCIAS_DOCUMENTALES + ".xls");

        WritableWorkbook source = Workbook.createWorkbook(monFichier);

        WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10);
        WritableCellFormat arial10format = new WritableCellFormat(arial10font);
        arial10format.setBorder(Border.ALL, BorderLineStyle.MEDIUM);


        WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableCellFormat bold = new WritableCellFormat(wf);
        bold.setWrap(true);

        WritableSheet sheet1 = source.createSheet(monFichier.getName().toString(), 0);
        sheet1.mergeCells(1, 1, 3, 3);
        sheet1.mergeCells(0, 5, 4, 5);

        WritableCellFormat cellFormat = new WritableCellFormat();
        cellFormat.setOrientation(jxl.format.Orientation.VERTICAL);

        WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

        WritableFont dataFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat dataFormat = new WritableCellFormat(dataFont);

        titleFormat.setWrap(true);
        titleFormat.setAlignment(Alignment.CENTRE);
        titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

        int linea = 0;
        linea = 6;

        for (int l = 0; l < 5; l++) {
            sheet1.setColumnView(l, 15);
        }

        Label label = new Label(1, 1, monFichier.getName().toString(), titleFormat);
        sheet1.addCell(label);

        //Informe de proceso del archivo
        Label sousTitre1 = new Label(0, 5, TituloInformeXLS.INFORME_PROCESO_ARCHIVO + helpers.Constantes.CONSTANCIAS_DOCUMENTALES, titleFormat);
        Label sousTitre2 = new Label(0, 6, TituloInformeXLS.CODIGO_ENTIDADUE, titleFormat);
        Label sousTitre3 = new Label(1, 6, TituloInformeXLS.TIPO_DOCUMENTO, titleFormat);
        Label sousTitre4 = new Label(2, 6, TituloInformeXLS.NUMERO_DOCUMENTO, titleFormat);
        Label sousTitre5 = new Label(3, 6, TituloInformeXLS.RESULTADO_OPERACION, titleFormat);
        sheet1.addCell(sousTitre1);
        sheet1.addCell(sousTitre2);
        sheet1.addCell(sousTitre3);
        sheet1.addCell(sousTitre4);
        sheet1.addCell(sousTitre5);

        for (LineaInformeTipoNumeroDocumento litnd : is.getLt()) {
            linea++;
            Label codigo_entidad = new Label(0, linea, litnd.getCodigoEntidadUE(), dataFormat);
            Label tipoDocumento = new Label(1, linea, litnd.getTipoDocumento(), dataFormat);
            Label numeroDocumento = new Label(2, linea, litnd.getNumeroDocumento(), dataFormat);
            Label resultado_operacion = new Label(3, linea, litnd.getResultado(), dataFormat);
            sheet1.addCell((WritableCell) codigo_entidad);
            sheet1.addCell((WritableCell) tipoDocumento);
            sheet1.addCell((WritableCell) numeroDocumento);
            sheet1.addCell((WritableCell) resultado_operacion);
        }

        //resumen del proceso
        linea++;
        linea++;
        Label sousTitre6 = new Label(0, linea, TituloInformeXLS.RESUMEN_PROCESO_ARCHIVO, titleFormat);
        sheet1.mergeCells(0, linea, 3, linea);
        sheet1.addCell(sousTitre6);

        linea++;
        Label rechazados_titulo = new Label(1, linea, InformeXLS.RECHAZADOS, dataFormat);
        Label rechazadoss = new Label(2, linea, is.getRechazado().toString(), dataFormat);
        sheet1.addCell(rechazados_titulo);
        sheet1.addCell(rechazadoss);

        linea++;
        Label sin_cambios_titulo = new Label(1, linea, InformeXLS.SIN_CAMBIOS, dataFormat);
        Label sin_cambios = new Label(2, linea, is.getCambio().toString(), dataFormat);
        sheet1.addCell(sin_cambios_titulo);
        sheet1.addCell(sin_cambios);

        linea++;
        Label modificados_titulo = new Label(1, linea, InformeXLS.MODIFICADOS, dataFormat);
        Label modificados = new Label(2, linea, is.getModificado().toString(), dataFormat);
        sheet1.addCell(modificados_titulo);
        sheet1.addCell(modificados);

        linea++;
        Label altas_titulo = new Label(1, linea, InformeXLS.ALTAS, dataFormat);
        Label altas = new Label(2, linea,is.getAlta().toString(), dataFormat);
        sheet1.addCell(altas_titulo);
        sheet1.addCell(altas);


        source.write();
        source.close();

    }

    public void creadorXLSTrabajador(InformeSalida<LineaInformeTipoNumeroDocumento> is, String path) throws IOException, WriteException {

        File monFichier = new File(path + "/"+ helpers.Constantes.TRABAJADORES + ".xls");

        WritableWorkbook source = Workbook.createWorkbook(monFichier);

        WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10);
        WritableCellFormat arial10format = new WritableCellFormat(arial10font);
        arial10format.setBorder(Border.ALL, BorderLineStyle.MEDIUM);


        WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableCellFormat bold = new WritableCellFormat(wf);
        bold.setWrap(true);

        WritableSheet sheet1 = source.createSheet(monFichier.getName().toString(), 0);
        sheet1.mergeCells(1, 1, 3, 3);
        sheet1.mergeCells(0, 5, 4, 5);

        WritableCellFormat cellFormat = new WritableCellFormat();
        cellFormat.setOrientation(jxl.format.Orientation.VERTICAL);

        WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

        WritableFont dataFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat dataFormat = new WritableCellFormat(dataFont);

        titleFormat.setWrap(true);
        titleFormat.setAlignment(Alignment.CENTRE);
        titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

        int linea = 0;
        linea = 6;

        for (int l = 0; l < 5; l++) {
            sheet1.setColumnView(l, 15);
        }

        Label label = new Label(1, 1, monFichier.getName().toString(), titleFormat);
        sheet1.addCell(label);

        //Informe de proceso del archivo
        Label sousTitre1 = new Label(0, 5, TituloInformeXLS.INFORME_PROCESO_ARCHIVO + helpers.Constantes.TRABAJADORES, titleFormat);
        Label sousTitre2 = new Label(0, 6, TituloInformeXLS.CODIGO_ENTIDADUE, titleFormat);
        Label sousTitre3 = new Label(1, 6, TituloInformeXLS.TIPO_DOCUMENTO, titleFormat);
        Label sousTitre4 = new Label(2, 6, TituloInformeXLS.NUMERO_DOCUMENTO, titleFormat);
        Label sousTitre5 = new Label(3, 6, TituloInformeXLS.RESULTADO_OPERACION, titleFormat);
        sheet1.addCell(sousTitre1);
        sheet1.addCell(sousTitre2);
        sheet1.addCell(sousTitre3);
        sheet1.addCell(sousTitre4);
        sheet1.addCell(sousTitre5);

        for (LineaInformeTipoNumeroDocumento litnd : is.getLt()) {
            linea++;
            Label codigo_entidad = new Label(0, linea, litnd.getCodigoEntidadUE(), dataFormat);
            Label tipoDocumento = new Label(1, linea, litnd.getTipoDocumento(), dataFormat);
            Label numeroDocumento = new Label(2, linea, litnd.getNumeroDocumento(), dataFormat);
            Label resultado_operacion = new Label(3, linea, litnd.getResultado(), dataFormat);
            sheet1.addCell((WritableCell) codigo_entidad);
            sheet1.addCell((WritableCell) tipoDocumento);
            sheet1.addCell((WritableCell) numeroDocumento);
            sheet1.addCell((WritableCell) resultado_operacion);
        }

        //resumen del proceso
        linea++;
        linea++;
        Label sousTitre6 = new Label(0, linea, TituloInformeXLS.RESUMEN_PROCESO_ARCHIVO, titleFormat);
        sheet1.mergeCells(0, linea, 3, linea);
        sheet1.addCell(sousTitre6);

        linea++;
        Label rechazados_titulo = new Label(1, linea, InformeXLS.RECHAZADOS, dataFormat);
        Label rechazadoss = new Label(2, linea, is.getRechazado().toString(), dataFormat);
        sheet1.addCell(rechazados_titulo);
        sheet1.addCell(rechazadoss);

        linea++;
        Label sin_cambios_titulo = new Label(1, linea, InformeXLS.SIN_CAMBIOS, dataFormat);
        Label sin_cambios = new Label(2, linea, is.getCambio().toString(), dataFormat);
        sheet1.addCell(sin_cambios_titulo);
        sheet1.addCell(sin_cambios);

        linea++;
        Label modificados_titulo = new Label(1, linea, InformeXLS.MODIFICADOS, dataFormat);
        Label modificados = new Label(2, linea, is.getModificado().toString(), dataFormat);
        sheet1.addCell(modificados_titulo);
        sheet1.addCell(modificados);

        linea++;
        Label altas_titulo = new Label(1, linea, InformeXLS.ALTAS, dataFormat);
        Label altas = new Label(2, linea,is.getAlta().toString(), dataFormat);
        sheet1.addCell(altas_titulo);
        sheet1.addCell(altas);


        source.write();
        source.close();

    }

    public void creadorXLSLegajo(InformeSalida<LineaInformeTipoNumeroDocumento> is, String path)
            throws IOException, WriteException {

        File monFichier = new File(path + "/"+ helpers.Constantes.LEGAJOS + ".xls");

        WritableWorkbook source = Workbook.createWorkbook(monFichier);

        WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10);
        WritableCellFormat arial10format = new WritableCellFormat(arial10font);
        arial10format.setBorder(Border.ALL, BorderLineStyle.MEDIUM);


        WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableCellFormat bold = new WritableCellFormat(wf);
        bold.setWrap(true);

        WritableSheet sheet1 = source.createSheet(monFichier.getName().toString(), 0);
        sheet1.mergeCells(1, 1, 3, 3);
        sheet1.mergeCells(0, 5, 4, 5);

        WritableCellFormat cellFormat = new WritableCellFormat();
        cellFormat.setOrientation(jxl.format.Orientation.VERTICAL);

        WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

        WritableFont dataFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat dataFormat = new WritableCellFormat(dataFont);

        titleFormat.setWrap(true);
        titleFormat.setAlignment(Alignment.CENTRE);
        titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

        int linea = 0;
        linea = 6;

        for (int l = 0; l < 5; l++) {
            sheet1.setColumnView(l, 15);
        }

        Label label = new Label(1, 1, monFichier.getName().toString(), titleFormat);
        sheet1.addCell(label);

        //Informe de proceso del archivo
        Label sousTitre1 = new Label(0, 5, TituloInformeXLS.INFORME_PROCESO_ARCHIVO + helpers.Constantes.LEGAJOS, titleFormat);
        Label sousTitre2 = new Label(0, 6, TituloInformeXLS.CODIGO_ENTIDADUE, titleFormat);
        Label sousTitre3 = new Label(1, 6, TituloInformeXLS.TIPO_DOCUMENTO, titleFormat);
        Label sousTitre4 = new Label(2, 6, TituloInformeXLS.NUMERO_DOCUMENTO, titleFormat);
        Label sousTitre5 = new Label(3, 6, TituloInformeXLS.RESULTADO_OPERACION, titleFormat);
        sheet1.addCell(sousTitre1);
        sheet1.addCell(sousTitre2);
        sheet1.addCell(sousTitre3);
        sheet1.addCell(sousTitre4);
        sheet1.addCell(sousTitre5);

        for (LineaInformeTipoNumeroDocumento litnd : is.getLt()) {
            linea++;
            Label codigo_entidad = new Label(0, linea, litnd.getCodigoEntidadUE(), dataFormat);
            Label tipoDocumento = new Label(1, linea, litnd.getTipoDocumento(), dataFormat);
            Label numeroDocumento = new Label(2, linea, litnd.getNumeroDocumento(), dataFormat);
            Label resultado_operacion = new Label(3, linea, litnd.getResultado(), dataFormat);
            sheet1.addCell((WritableCell) codigo_entidad);
            sheet1.addCell((WritableCell) tipoDocumento);
            sheet1.addCell((WritableCell) numeroDocumento);
            sheet1.addCell((WritableCell) resultado_operacion);
        }

        //resumen del proceso
        linea++;
        linea++;
        Label sousTitre6 = new Label(0, linea, TituloInformeXLS.RESUMEN_PROCESO_ARCHIVO, titleFormat);
        sheet1.mergeCells(0, linea, 3, linea);
        sheet1.addCell(sousTitre6);

        linea++;
        Label rechazados_titulo = new Label(1, linea, InformeXLS.RECHAZADOS, dataFormat);
        Label rechazadoss = new Label(2, linea, is.getRechazado().toString(), dataFormat);
        sheet1.addCell(rechazados_titulo);
        sheet1.addCell(rechazadoss);

        linea++;
        Label sin_cambios_titulo = new Label(1, linea, InformeXLS.SIN_CAMBIOS, dataFormat);
        Label sin_cambios = new Label(2, linea, is.getCambio().toString(), dataFormat);
        sheet1.addCell(sin_cambios_titulo);
        sheet1.addCell(sin_cambios);

        linea++;
        Label modificados_titulo = new Label(1, linea, InformeXLS.MODIFICADOS, dataFormat);
        Label modificados = new Label(2, linea, is.getModificado().toString(), dataFormat);
        sheet1.addCell(modificados_titulo);
        sheet1.addCell(modificados);

        linea++;
        Label altas_titulo = new Label(1, linea, InformeXLS.ALTAS, dataFormat);
        Label altas = new Label(2, linea,is.getAlta().toString(), dataFormat);
        sheet1.addCell(altas_titulo);
        sheet1.addCell(altas);


        source.write();
        source.close();

    }

    public void creadorXLSCargoAsignado(InformeSalida<LineaInformeTipoNumeroDocumento> is, String path)
            throws IOException, WriteException {

                System.out.println("----------- informe salida "+is.getLt().size());
        File monFichier = new File(path + "/"+ helpers.Constantes.CARGOS_ASIGNADOS + ".xls");

        WritableWorkbook source = Workbook.createWorkbook(monFichier);

        WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10);
        WritableCellFormat arial10format = new WritableCellFormat(arial10font);
        arial10format.setBorder(Border.ALL, BorderLineStyle.MEDIUM);


        WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableCellFormat bold = new WritableCellFormat(wf);
        bold.setWrap(true);

        WritableSheet sheet1 = source.createSheet(monFichier.getName().toString(), 0);
        sheet1.mergeCells(1, 1, 3, 3);
        sheet1.mergeCells(0, 5, 4, 5);

        WritableCellFormat cellFormat = new WritableCellFormat();
        cellFormat.setOrientation(jxl.format.Orientation.VERTICAL);

        WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

        WritableFont dataFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat dataFormat = new WritableCellFormat(dataFont);

        titleFormat.setWrap(true);
        titleFormat.setAlignment(Alignment.CENTRE);
        titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

        int linea = 0;
        linea = 6;

        for (int l = 0; l < 5; l++) {
            sheet1.setColumnView(l, 15);
        }

        Label label = new Label(1, 1, monFichier.getName().toString(), titleFormat);
        sheet1.addCell(label);

        //Informe de proceso del archivo
        Label sousTitre1 = new Label(0, 5, TituloInformeXLS.INFORME_PROCESO_ARCHIVO + helpers.Constantes.CARGOS_ASIGNADOS, titleFormat);
        Label sousTitre2 = new Label(0, 6, TituloInformeXLS.CODIGO_ENTIDADUE, titleFormat);
        Label sousTitre3 = new Label(1, 6, TituloInformeXLS.TIPO_DOCUMENTO, titleFormat);
        Label sousTitre4 = new Label(2, 6, TituloInformeXLS.NUMERO_DOCUMENTO, titleFormat);
        Label sousTitre5 = new Label(3, 6, TituloInformeXLS.RESULTADO_OPERACION, titleFormat);
        sheet1.addCell(sousTitre1);
        sheet1.addCell(sousTitre2);
        sheet1.addCell(sousTitre3);
        sheet1.addCell(sousTitre4);
        sheet1.addCell(sousTitre5);

        for (LineaInformeTipoNumeroDocumento litnd : is.getLt()) {
            linea++;
            Label codigo_entidad = new Label(0, linea, litnd.getCodigoEntidadUE(), dataFormat);
            Label tipoDocumento = new Label(1, linea, litnd.getTipoDocumento(), dataFormat);
            Label numeroDocumento = new Label(2, linea, litnd.getNumeroDocumento(), dataFormat);
            Label resultado_operacion = new Label(3, linea, litnd.getResultado(), dataFormat);
            sheet1.addCell((WritableCell) codigo_entidad);
            sheet1.addCell((WritableCell) tipoDocumento);
            sheet1.addCell((WritableCell) numeroDocumento);
            sheet1.addCell((WritableCell) resultado_operacion);

        }

        //resumen del proceso
        linea++;
        linea++;
        Label sousTitre6 = new Label(0, linea, TituloInformeXLS.RESUMEN_PROCESO_ARCHIVO, titleFormat);
        sheet1.mergeCells(0, linea, 3, linea);
        sheet1.addCell(sousTitre6);

        linea++;
        Label rechazados_titulo = new Label(1, linea, InformeXLS.RECHAZADOS, dataFormat);
        Label rechazadoss = new Label(2, linea, is.getRechazado().toString(), dataFormat);
        sheet1.addCell(rechazados_titulo);
        sheet1.addCell(rechazadoss);

        linea++;
        Label sin_cambios_titulo = new Label(1, linea, InformeXLS.SIN_CAMBIOS, dataFormat);
        Label sin_cambios = new Label(2, linea, is.getCambio().toString(), dataFormat);
        sheet1.addCell(sin_cambios_titulo);
        sheet1.addCell(sin_cambios);

        linea++;
        Label modificados_titulo = new Label(1, linea, InformeXLS.MODIFICADOS, dataFormat);
        Label modificados = new Label(2, linea, is.getModificado().toString(), dataFormat);
        sheet1.addCell(modificados_titulo);
        sheet1.addCell(modificados);

        linea++;
        Label altas_titulo = new Label(1, linea, InformeXLS.ALTAS, dataFormat);
        Label altas = new Label(2, linea,is.getAlta().toString(), dataFormat);
        sheet1.addCell(altas_titulo);
        sheet1.addCell(altas);

        source.write();
        source.close();

    }

    public void creadorXLSRemuneracionPersonal(InformeSalida<LineaInformeTipoNumeroDocumento> is, String path)
            throws IOException, WriteException {

        File monFichier = new File(path + "/"+ helpers.Constantes.REMUNERACIONES_PERSONALES + ".xls");

        WritableWorkbook source = Workbook.createWorkbook(monFichier);

        WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10);
        WritableCellFormat arial10format = new WritableCellFormat(arial10font);
        arial10format.setBorder(Border.ALL, BorderLineStyle.MEDIUM);


        WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableCellFormat bold = new WritableCellFormat(wf);
        bold.setWrap(true);

        WritableSheet sheet1 = source.createSheet(monFichier.getName().toString(), 0);
        sheet1.mergeCells(1, 1, 3, 3);
        sheet1.mergeCells(0, 5, 4, 5);

        WritableCellFormat cellFormat = new WritableCellFormat();
        cellFormat.setOrientation(jxl.format.Orientation.VERTICAL);

        WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

        WritableFont dataFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        WritableCellFormat dataFormat = new WritableCellFormat(dataFont);

        titleFormat.setWrap(true);
        titleFormat.setAlignment(Alignment.CENTRE);
        titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

        int linea = 0;
        linea = 6;

        for (int l = 0; l < 5; l++) {
            sheet1.setColumnView(l, 15);
        }

        Label label = new Label(1, 1, monFichier.getName().toString(), titleFormat);
        sheet1.addCell(label);

        //Informe de proceso del archivo
        Label sousTitre1 = new Label(0, 5, TituloInformeXLS.INFORME_PROCESO_ARCHIVO + helpers.Constantes.REMUNERACIONES_PERSONALES, titleFormat);
        Label sousTitre2 = new Label(0, 6, TituloInformeXLS.CODIGO_ENTIDADUE, titleFormat);
        Label sousTitre3 = new Label(1, 6, TituloInformeXLS.TIPO_DOCUMENTO, titleFormat);
        Label sousTitre4 = new Label(2, 6, TituloInformeXLS.NUMERO_DOCUMENTO, titleFormat);
        Label sousTitre5 = new Label(3, 6, TituloInformeXLS.RESULTADO_OPERACION, titleFormat);
        sheet1.addCell(sousTitre1);
        sheet1.addCell(sousTitre2);
        sheet1.addCell(sousTitre3);
        sheet1.addCell(sousTitre4);
        sheet1.addCell(sousTitre5);

        for (LineaInformeTipoNumeroDocumento litnd : is.getLt()) {
            linea++;
            Label codigo_entidad = new Label(0, linea, litnd.getCodigoEntidadUE(), dataFormat);
            Label tipoDocumento = new Label(1, linea, litnd.getTipoDocumento(), dataFormat);
            Label numeroDocumento = new Label(2, linea, litnd.getNumeroDocumento(), dataFormat);
            Label resultado_operacion = new Label(3, linea, litnd.getResultado(), dataFormat);
            sheet1.addCell((WritableCell) codigo_entidad);
            sheet1.addCell((WritableCell) tipoDocumento);
            sheet1.addCell((WritableCell) numeroDocumento);
            sheet1.addCell((WritableCell) resultado_operacion);
        }

        //resumen del proceso
        linea++;
        linea++;
        Label sousTitre6 = new Label(0, linea, TituloInformeXLS.RESUMEN_PROCESO_ARCHIVO, titleFormat);
        sheet1.mergeCells(0, linea, 3, linea);
        sheet1.addCell(sousTitre6);

        linea++;
        Label rechazados_titulo = new Label(1, linea, InformeXLS.RECHAZADOS, dataFormat);
        Label rechazadoss = new Label(2, linea, is.getRechazado().toString(), dataFormat);
        sheet1.addCell(rechazados_titulo);
        sheet1.addCell(rechazadoss);

        linea++;
        Label sin_cambios_titulo = new Label(1, linea, InformeXLS.SIN_CAMBIOS, dataFormat);
        Label sin_cambios = new Label(2, linea, is.getCambio().toString(), dataFormat);
        sheet1.addCell(sin_cambios_titulo);
        sheet1.addCell(sin_cambios);

        linea++;
        Label modificados_titulo = new Label(1, linea, InformeXLS.MODIFICADOS, dataFormat);
        Label modificados = new Label(2, linea, is.getModificado().toString(), dataFormat);
        sheet1.addCell(modificados_titulo);
        sheet1.addCell(modificados);

        linea++;
        Label altas_titulo = new Label(1, linea, InformeXLS.ALTAS, dataFormat);
        Label altas = new Label(2, linea,is.getAlta().toString(), dataFormat);
        sheet1.addCell(altas_titulo);
        sheet1.addCell(altas);


        source.write();
        source.close();

    }

    public List<String> creadorCSVEntidadUE(Entidad_BK oi, String file, Session session) {

        List<String> errores = new LinkedList<String>();

        try {
            PrintWriter escribir = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            escribir.print(InformeXLS.leoCampo(oi.getCodigoEntidadUE()));
            escribir.print("|");
            escribir.print(InformeXLS.leoCampo(oi.getDenominacion()));
            escribir.print("|");
            escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("ClasFuncOrg", InformeXLS.leoCampo(oi.getClas_funcional()), errores, session));
            escribir.print("|");
            escribir.print(InformeXLS.leoCampo(oi.getCue_entidad()));
            escribir.print("|");
            escribir.print(InformeXLS.leoCampo(oi.getCue_elemento()));
            escribir.print("|");
            escribir.print(InformeXLS.numberToString(oi.getCod_mef_ue()));
            escribir.print("|");
            escribir.print(InformeXLS.leoCampo(oi.getRuc()));
            escribir.print("|");
            escribir.print(InformeXLS.leoCampo(oi.getDirecion()));
            escribir.print("|");
            escribir.print(InformeXLS.leoCampo(oi.getLocalidad()));
            escribir.print("|");
            escribir.print(InformeXLS.datoAuxiliarToString(oi.getCod_ubi_dept()));
            escribir.print("|");
            escribir.print(InformeXLS.datoAuxiliarToString(oi.getCod_ubi_dist()));
            escribir.print("|");
            escribir.print(InformeXLS.datoAuxiliarToString(oi.getCod_ubi_prov()));
            escribir.print("|");
            escribir.print(InformeXLS.leoCampo(oi.getTitularEntidad()));
            escribir.print("|");
            escribir.print(InformeXLS.leoCampo(oi.getCorreoElectronicoTitular()));
            escribir.print("|");
            escribir.print(InformeXLS.leoCampo(oi.getJefeRRHHEntidad()));
            escribir.print("|");
            escribir.print(InformeXLS.leoCampo(oi.getCorreoElectronicoJefeRRHH()));
            escribir.print("|");
            escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("NivelGobierno", InformeXLS.leoCampo(oi.getNivel_gobierno()), errores, session));
            escribir.print("|");
            escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("SectorGobierno", InformeXLS.leoCampo(oi.getSector_gobierno()), errores, session));
            escribir.print("|");
            escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("Pliego", InformeXLS.leoCampo(oi.getPliego()), errores, session));
            escribir.print("|");
            escribir.print(InformeXLS.leoCampo(oi.getCorreoElectronicoInstitucional()));
            escribir.print("|");
            escribir.print(InformeXLS.leoCampo(oi.getTEJefeRRHH()));
            escribir.print("|");
            escribir.print(InformeXLS.leoCampo(oi.getTEMovilJefeRRHH()));
            escribir.close();

        } catch (Exception ex) {
            System.out.println("------------- erreur generando archivo organ1 "+ex);
            errores.add(ERROR_GENERANDO_EL_ARCHIVO + " ORGAN1.csv");
        }

        return errores;
    }

    public List<String> creadorCSVConcepto(List<ConceptoRemunerativo> lcr, String file, Session session) {
        List<String> errores = new LinkedList<String>();
        Integer periodicidad = 0;

        try {
            PrintWriter escribir = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            for (ConceptoRemunerativo cr : lcr) {
                if(cr.getEntidad() != null)
                    escribir.print(InformeXLS.leoCampo(cr.getEntidad().getCue_entidad()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(cr.getCodigo()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(cr.getDescripcion()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(cr.getSustento_legal()));
                escribir.print("|");
                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("TipoRemuneracion", InformeXLS.leoCampo(cr.getClasificacion()), errores, session));
                escribir.print("|");
                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("TipoRemuneracionStd", InformeXLS.leoCampo(cr.getConceptoStd()), errores, session));
                escribir.print("|");
                if(cr.getPeriodicidad() != null ){
                    if(!cr.getPeriodicidad().trim().equals("")){
                        if(cr.getPeriodicidad().equals(ConceptoRemunerativo.PERIODICIDADES.get(0))){
                            periodicidad = 0;
                        }
                        if(cr.getPeriodicidad().equals(ConceptoRemunerativo.PERIODICIDADES.get(1))){
                            periodicidad = 1;
                        }
                        if(cr.getPeriodicidad().equals(ConceptoRemunerativo.PERIODICIDADES.get(2))){
                            periodicidad = 2;
                        }
                        if(cr.getPeriodicidad().equals(ConceptoRemunerativo.PERIODICIDADES.get(3))){
                            periodicidad = 3;
                        }
                    }
                }
                escribir.print(periodicidad);
                escribir.println();
            }
            escribir.close();

        } catch (Exception ex) {

            errores.add(ERROR_GENERANDO_EL_ARCHIVO + " BASICO6.csv");
        }

        return errores;
    }

    public static List<String> creadorCSVUnidadOrganica(List<UnidadOrganica> luo, String file, Session session) {
        List<String> errores = new LinkedList<String>();

        try {
            PrintWriter escribir = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            for (UnidadOrganica uo : luo) {
                if(uo.getEntidad() != null)
                    escribir.print(InformeXLS.leoCampo(uo.getEntidad().getCue_entidad()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(uo.getCod_und_organica()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(uo.getDen_und_organica()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(uo.getLocalidad()));
                escribir.print("|");
                escribir.print(InformeXLS.datoAuxiliarToString(uo.getCod_ubi_dist()));
                escribir.print("|");
                escribir.print(InformeXLS.datoAuxiliarToString(uo.getCod_ubi_prov()));
                escribir.print("|");
                escribir.print(InformeXLS.datoAuxiliarToString(uo.getCod_ubi_dept()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(uo.getCue()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(uo.getSigla()));
                escribir.print("|");
                escribir.print(InformeXLS.numberToString(uo.getNivel()));
                escribir.print("|");
                //escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("TipoActividad", InformeXLS.leoCampo(uo.getTipoActividad()), errores, session));
                //escribir.print("|");
                if(uo.getUnidadorganica() != null)
                    escribir.print(InformeXLS.leoCampo(uo.getUnidadorganica().getCod_und_organica()));
                /*
                else
                    escribir.print("X");
                 * 
                 */
                escribir.println();
            }
            
            escribir.close();

        } catch (Exception ex) {
            System.out.println("error ex en basico 02 "+ex);
            errores.add(ERROR_GENERANDO_EL_ARCHIVO + " Unidad BASICO2.csv");
        }
        return errores;
    }

    public static List<String> creadorCSVCargo(List<Cargoxunidad> lc, String file, Session session) {
        List<String> errores = new LinkedList<String>();

        try {
            PrintWriter escribir = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            for (Cargoxunidad c : lc) {
                if(c.getUnidadorganica() != null){
                    if(c.getUnidadorganica().getEntidad() != null)
                        escribir.print(InformeXLS.leoCampo(c.getUnidadorganica().getEntidad().getCue_entidad()));
                }
                escribir.print("|");
                if(c.getUnidadorganica() != null)
                    escribir.print(InformeXLS.leoCampo(c.getUnidadorganica().getCod_und_organica()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(c.getCod_cargo()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(c.getDen_cargo()));
                escribir.print("|");
                // TODO JZM escribir.print(InformeXLS.getBoolStrFromEstado(InformeXLS.leoCampo(c.getEstado())));
                escribir.print(InformeXLS.getBoolStrFromEstado(InformeXLS.leoCampo("Alta")));
                escribir.print("|");
                escribir.print(InformeXLS.datoAuxiliarToString(c.getRegimenlaboral()));
                // TODO JZM revisar 
//                escribir.print("|");
//                escribir.print(InformeXLS.numberToString(c.getHoras_x_sem()));
//                escribir.print("|");
//                escribir.print(InformeXLS.datoAuxiliarToString(c.getClasificacion_funcional()));
//                escribir.print("|");
//                escribir.print(InformeXLS.booleanToString(c.getReq_hab_profesional()));
                escribir.print("|");
                escribir.print(InformeXLS.booleanToString(c.getDec_jurada_byr()));
                escribir.print("|");
                escribir.print(InformeXLS.booleanToString(c.getPresupuestado_PAP()));
                escribir.print("|");
                escribir.print(InformeXLS.datoAuxiliarToString(c.getGrupoOcupacional()));
                escribir.print("|");
                escribir.print(InformeXLS.datoAuxiliarToString(c.getNivelRemunerativo()));
                // TODO revisar JZM
//                escribir.print("|");
//                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("SituacionCAP", InformeXLS.leoCampo(c.getSituacion_CAP()), errores, session));
//                escribir.print("|");
//                escribir.print(InformeXLS.booleanToString(c.getPersonasCargo()));
                escribir.println();
            }
            escribir.close();

        } catch (Exception ex) {
            errores.add(ERROR_GENERANDO_EL_ARCHIVO + " BASICO3.csv");
        }
        
        return errores;
    }

    public static List<String> creadorCSVTrabajador(List<Trabajador> lt, String file, Entidad_BK oi, Session session) {
        List<String> errores = new LinkedList<String>();

        try {
            PrintWriter escribir = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            for (Trabajador t : lt) {
                escribir.print(InformeXLS.leoCampo(oi.getCodigoEntidadUE()));
                escribir.print("|");
//                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("TipoDocumento", InformeXLS.leoCampo(t.getTipoDocumento()), errores, session));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(t.getNroDocumento()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(t.getApellidoPaterno()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(t.getApellidoMaterno()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(t.getNombres()));
                escribir.print("|");
                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("Sexo", InformeXLS.leoCampo(t.getSexo()), errores, session));
                escribir.print("|");
                escribir.print(InformeXLS.datetoString(t.getFechaNacimiento()));
                escribir.print("|");
//                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("Paises", InformeXLS.leoCampo(t.getPais()), errores, session));
                escribir.print("|");
//                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("UBDistrito", InformeXLS.datoAuxiliarToString(t.getCod_ubi_dist()), errores, session));
                escribir.print("|");
//                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("UBProvincia", InformeXLS.datoAuxiliarToString(t.getCod_ubi_prov()), errores, session));
                escribir.print("|");
//                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("UBDepartamento", InformeXLS.datoAuxiliarToString(t.getCod_ubi_dept()), errores, session));
                escribir.print("|");
//                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("Nacionalidades", InformeXLS.leoCampo(t.getNacionalidad()), errores, session));
                escribir.print("|");
               // escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("EstadoCivil", InformeXLS.leoCampo(t.getEstadocivil()), errores, session));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(t.getDomicilioDireccion()));
                escribir.print("|");
                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("UBDistrito", InformeXLS.datoAuxiliarToString(t.getCod_dom_dist()), errores, session));
                escribir.print("|");
                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("UBProvincia", InformeXLS.datoAuxiliarToString(t.getCod_dom_prov()), errores, session));
                escribir.print("|");
//                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("UBDepartamento", InformeXLS.datoAuxiliarToString(t.getCod_ubi_dept()), errores, session));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(t.getDomicilioCodigoPostal()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(t.getEsSalud()));
                escribir.print("|");
//                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("GrupoSanguineo", InformeXLS.leoCampo(t.getGrupoSanguineo()), errores, session));
                escribir.print("|");
//                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("TipoDiscapacidad", InformeXLS.leoCampo(t.getTipoDiscapacidad()), errores, session));
                escribir.print("|");
                escribir.print(InformeXLS.numberToString(t.getNroCertificadoCONADIS()));
                escribir.print("|");
     //           escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("NivelInstrucción", InformeXLS.leoCampo(t.getNivelInstruccion()), errores, session));
                escribir.print("|");
                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("FormacionProfesional", InformeXLS.datoAuxiliarToString(t.getFormacionprofesional()), errores, session));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(t.getEmergenciaNombre()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(t.getEmergenciaDomicilio()));
                escribir.print("|");
//                escribir.print(InformeXLS.leoCampo(t.getEmergenciaTelefonos()));
                escribir.print("|");
//                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("RegPensionarios", InformeXLS.leoCampo(t.getRegimenPensionario()), errores, session));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(t.getEmailPersonal()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(t.getEmailLaboral()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(t.getNroRUC()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(t.getCodigoOSCE()));
                escribir.println();
            }
            escribir.close();

        } catch (Exception ex) {
            errores.add(ERROR_GENERANDO_EL_ARCHIVO + " PPAL1.csv");
        }
  
        return errores;
    }

    public static List<String> creadorCSVLegajo(List<Legajo> ll, String file, Session session) {
        List<String> errores = new LinkedList<String>();

        try {
            PrintWriter escribir = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            for (Legajo l : ll) {
                if(l.getEntidad() != null)
                    escribir.print(InformeXLS.leoCampo(l.getEntidad().getCue_entidad()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(l.getCod_legajo()));
                escribir.print("|");
                if(l.getTrabajador() != null)
//                    escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("TipoDocumento", InformeXLS.leoCampo(l.getTrabajador().getTipoDocumento()), errores, session));
                escribir.print("|");
                if(l.getTrabajador() != null)
                    escribir.print(InformeXLS.leoCampo(l.getTrabajador().getNroDocumento()));
                escribir.println();
            }
            escribir.close();

        } catch (Exception ex) {
            errores.add(ERROR_GENERANDO_EL_ARCHIVO + " PPAL2.csv");
        }
        
        return errores;
    }

    public static List<String> creadorCSVCargoAsignado(List<CargoAsignado> lca, String file, Session session) {
        List<String> errores = new LinkedList<String>();

        try {
            PrintWriter escribir = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            for (CargoAsignado ca : lca) {
                if(ca.getLegajo() != null){
                    if(ca.getLegajo().getEntidad() != null)
                        escribir.print(InformeXLS.leoCampo(ca.getLegajo().getEntidad().getCue_entidad()));
                }
                escribir.print("|");
                if(ca.getCargoxunidad() != null)
                    escribir.print(InformeXLS.leoCampo(ca.getCargoxunidad().getCod_cargo()));
                escribir.print("|");
                if(ca.getTrabajador() != null)
//                    escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("TipoDocumento", InformeXLS.leoCampo(ca.getTrabajador().getTipoDocumento()), errores, session));
                escribir.print("|");
                if(ca.getTrabajador() != null)
                    escribir.print(InformeXLS.leoCampo(ca.getTrabajador().getNroDocumento()));
                escribir.print("|");
                // TODO JZM escribir.print(InformeXLS.getBoolStrFromEstado(InformeXLS.leoCampo(ca.getEstado())));
                escribir.print(InformeXLS.getBoolStrFromEstado(InformeXLS.leoCampo("Alta")));
                escribir.print("|");
                escribir.print(InformeXLS.datetoString(ca.getFec_inicio()));
                escribir.print("|");
                escribir.print(InformeXLS.datetoString(ca.getFec_fin()));
                escribir.print("|");
//                escribir.print(InformeXLS.numberToString(ca.getCtd_per_superv()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(ca.getMotivo_cese()));
                escribir.print("|");
//                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("TipoVínculo", InformeXLS.leoCampo(ca.getTipoVinculo()), errores, session));
                escribir.println();
            }
            escribir.close();

        } catch (Exception ex) {
            errores.add(ERROR_GENERANDO_EL_ARCHIVO + " PPAL3.csv");
        }
        
        return errores;
    }

    public static List<String> creadorCSVFamiliar(List<Familiar> lf, String file, Entidad_BK oi, Session session) {
        List<String> errores = new LinkedList<String>();

        try {
            PrintWriter escribir = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            for (Familiar f : lf) {
                escribir.print(InformeXLS.leoCampo(oi.getCodigoEntidadUE()));
                escribir.print("|");
                if(f.getTrabajador() != null){
//                    escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("TipoDocumento", InformeXLS.leoCampo(f.getTrabajador().getTipoDocumento()), errores, session));
                }
                escribir.print("|");
                if(f.getTrabajador() != null)
                    escribir.print(InformeXLS.leoCampo(f.getTrabajador().getNroDocumento()));
                escribir.print("|");
//                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("GradoParentesco", InformeXLS.leoCampo(f.getParentesco()), errores, session));
                escribir.print("|");
//                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("TipoDocumento", InformeXLS.leoCampo(f.getTipoDocumento()), errores, session));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(f.getNroDocumento()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(f.getApellidoPaterno()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(f.getApellidoMaterno()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(f.getNombres()));
//                escribir.print("|");
//                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("Sexo", InformeXLS.leoCampo(f.getSexo()), errores, session));
                escribir.print("|");
                escribir.print(InformeXLS.datetoString(f.getFechaNacimiento()));
//                escribir.print("|");
//                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("Paises", InformeXLS.leoCampo(f.getPais()), errores, session));
//                escribir.print("|");
//                escribir.print(InformeXLS.datoAuxiliarToString(f.getCod_ubi_dist()));
//                escribir.print("|");
//                escribir.print(InformeXLS.datoAuxiliarToString(f.getCod_ubi_prov()));
//                escribir.print("|");
//                escribir.print(InformeXLS.datoAuxiliarToString(f.getCod_ubi_dept()));
//                escribir.print("|");
//                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("Nacionalidades", InformeXLS.leoCampo(f.getNacionalidad()), errores, session));
//                escribir.print("|");
//                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("EstadoCivil", InformeXLS.leoCampo(f.getEstadoCivil()), errores, session));
//                escribir.print("|");
//                escribir.print(InformeXLS.leoCampo(f.getDomicilioDireccion()));
//                escribir.print("|");
//                escribir.print(InformeXLS.datoAuxiliarToString(f.getCod_dom_dist()));
//                escribir.print("|");
//                escribir.print(InformeXLS.datoAuxiliarToString(f.getCod_dom_prov()));
//                escribir.print("|");
//                escribir.print(InformeXLS.datoAuxiliarToString(f.getCod_dom_dept()));
//                escribir.print("|");
//                escribir.print(InformeXLS.leoCampo(f.getDomicilioCodigoPostal()));
//                escribir.print("|");
//                escribir.print(InformeXLS.leoCampo(f.getEsSalud()));
//                escribir.print("|");
//                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("GrupoSanguineo", InformeXLS.leoCampo(f.getGrupoSanguineo()), errores, session));
//                escribir.print("|");
//                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("NivelInstrucción", InformeXLS.leoCampo(f.getNivelInstruccion()), errores, session));
                escribir.println();
            }
            escribir.close();

        } catch (Exception ex) {
            errores.add(ERROR_GENERANDO_EL_ARCHIVO + " COMPTRA1.csv");
        }

        return errores;
    }

    public static List<String> creadorCSVTitulo(List<Titulo> lt, String file, Entidad_BK oi, Session session) {
        List<String> errores = new LinkedList<String>();

        try {
            PrintWriter escribir = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            for (Titulo t : lt) {
                escribir.print(InformeXLS.leoCampo(oi.getCodigoEntidadUE()));
                escribir.print("|");
                if(t.getTrabajador() != null)
//                    escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("TipoDocumento", InformeXLS.leoCampo(t.getTrabajador().getTipoDocumento()), errores, session));
                escribir.print("|");
                if(t.getTrabajador() != null)
                    escribir.print(InformeXLS.leoCampo(t.getTrabajador().getNroDocumento()));
                escribir.print("|");
                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("NivelTitulo", InformeXLS.leoCampo(t.getNivel()), errores, session));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(t.getDenominacion()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(t.getEspecialidad()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(t.getCentro_estudios()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(t.getLugar_emision()));
                escribir.print("|");
                escribir.print(InformeXLS.datetoString(t.getFec_emision()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(t.getColegio_profesional()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(t.getNum_colegiatura()));
                escribir.println();
            }
            escribir.close();

        } catch (Exception ex) {
            errores.add(ERROR_GENERANDO_EL_ARCHIVO + " COMPTRA2.csv");
        }

        return errores;
    }

    public static List<String> creadorCSVCertificacion(List<Certificacion> lc, String file, Entidad_BK oi, Session session) {
        List<String> errores = new LinkedList<String>();

        try {
            PrintWriter escribir = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            for (Certificacion c : lc) {
                escribir.print(InformeXLS.leoCampo(oi.getCodigoEntidadUE()));
                escribir.print("|");
                if(c.getTrabajador() != null)
//                    escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("TipoDocumento", InformeXLS.leoCampo(c.getTrabajador().getTipoDocumento()), errores, session));
                escribir.print("|");
                if(c.getTrabajador() != null)
                    escribir.print(InformeXLS.leoCampo(c.getTrabajador().getNroDocumento()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(c.getDenominacion()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(c.getEspecialidad()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(c.getEntidad_certificante()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(c.getLugar_emision()));
                escribir.print("|");
                escribir.print(InformeXLS.datetoString(c.getFec_emision()));
                escribir.println();
            }
            escribir.close();

        } catch (Exception ex) {
            errores.add(ERROR_GENERANDO_EL_ARCHIVO + " COMPTRA3.csv");
        }

        return errores;
    }

    public static List<String> creadorCSVCurso(List<Curso> lc, String file, Entidad_BK oi, Session session) {
        List<String> errores = new LinkedList<String>();

        try {
            PrintWriter escribir = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            for (Curso c : lc) {
                escribir.print(InformeXLS.leoCampo(oi.getCodigoEntidadUE()));
                escribir.print("|");
                if(c.getTrabajador() != null)
//                    escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("TipoDocumento", InformeXLS.leoCampo(c.getTrabajador().getTipoDocumento()), errores, session));
                escribir.print("|");
                if(c.getTrabajador() != null)
                    escribir.print(InformeXLS.leoCampo(c.getTrabajador().getNroDocumento()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(c.getDenominacion()));
                escribir.print("|");
//                escribir.print(InformeXLS.leoCampo(c.getCentro_estudios()));
//                escribir.print("|");
//                escribir.print(InformeXLS.numberToString(c.getHoras()));
//                escribir.print("|");
//                escribir.print(InformeXLS.booleanToString(c.getFinanciadoEntidad()));
//                escribir.print("|");
//                escribir.print(InformeXLS.leoCampo(c.getLugar_dictado()));
//                escribir.print("|");
//                escribir.print(InformeXLS.datetoString(c.getFec_emision()));
//                escribir.println();
            }
            escribir.close();

        } catch (Exception ex) {
            errores.add(ERROR_GENERANDO_EL_ARCHIVO + " COMPTRA4.csv");
        }
        
        return errores;
    }

    public static List<String> creadorCSVAntecdentLaboral(List<Ant_Laborales> lal, String file, Entidad_BK oi, Session session) {
        List<String> errores = new LinkedList<String>();

        try {
            PrintWriter escribir = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            for (Ant_Laborales al : lal) {
                escribir.print(InformeXLS.leoCampo(oi.getCodigoEntidadUE()));
                escribir.print("|");
                if(al.getTrabajador() != null)
//                    escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("TipoDocumento", InformeXLS.leoCampo(al.getTrabajador().getTipoDocumento()), errores, session));
                escribir.print("|");
                if(al.getTrabajador() != null)
                    escribir.print(InformeXLS.leoCampo(al.getTrabajador().getNroDocumento()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(al.getCargo()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(al.getEmpresa()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(al.getFuncion()));
                escribir.print("|");
                escribir.print(InformeXLS.datetoString(al.getFec_ingreso()));
                escribir.print("|");
                escribir.print(InformeXLS.datetoString(al.getFec_egreso()));
                escribir.print("|");
//                escribir.print(InformeXLS.booleanToString(al.getEntidadPublica()));
//                escribir.print("|");
//                escribir.print(InformeXLS.leoCampo(al.getArea()));
//                escribir.print("|");
//                escribir.print(InformeXLS.leoCampo(al.getRegLaboral()));
                escribir.println();
            }
            escribir.close();

        } catch (Exception ex) {
            errores.add(ERROR_GENERANDO_EL_ARCHIVO + " COMPTRA5.csv");
        }
        
        return errores;
    }

    public static List<String> creadorCSVMeritoDemerito(List<MeritoDemerito> lmd, String file, Entidad_BK oi, Session session) {
        List<String> errores = new LinkedList<String>();
        String clase = null;
        String tipo = null;

        try {
            PrintWriter escribir = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            for (MeritoDemerito md : lmd) {
                escribir.print(InformeXLS.leoCampo(oi.getCodigoEntidadUE()));
                escribir.print("|");
                if(md.getTrabajador() != null)
//                    escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("TipoDocumento", InformeXLS.leoCampo(md.getTrabajador().getTipoDocumento()), errores, session));
                escribir.print("|");
                if(md.getTrabajador() != null)
                    escribir.print(InformeXLS.leoCampo(md.getTrabajador().getNroDocumento()));
                escribir.print("|");
                if(md.getClase().trim().equals(MeritoDemerito.CLASE_MERITO)){
                    clase = "1";
                    tipo = "TiposMerito";
                }
                if(md.getClase().trim().equals(MeritoDemerito.CLASE_DEMERITO)){
                    clase = "2";
                    tipo = "TiposDemerito";
                }
                escribir.print(clase);
                escribir.print("|");
                if(tipo != null)
                    escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar(tipo, InformeXLS.leoCampo(InformeXLS.leoCampo(md.getTipo())), errores, session));
                escribir.print("|");
                escribir.print(InformeXLS.datetoString(md.getFecha()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(md.getMotivo()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(md.getDetalle()));
                escribir.println();
            }
            escribir.close();

        } catch (Exception ex) {
            errores.add(ERROR_GENERANDO_EL_ARCHIVO + " COMPTRA6.csv");
        }
        
        return errores;
    }

    public static List<String> creadorCSVProduccionIntelectual(List<Publicacion> lp, String file, Entidad_BK oi, Session session) {
        List<String> errores = new LinkedList<String>();
        String clase = null;
        String tipo = null;
        
        try {
            PrintWriter escribir = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            for (Publicacion p : lp) {
                escribir.print(InformeXLS.leoCampo(oi.getCodigoEntidadUE()));
                escribir.print("|");
                if(p.getTrabajador() != null)
//                    escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("TipoDocumento", InformeXLS.leoCampo(p.getTrabajador().getTipoDocumento()), errores, session));
                escribir.print("|");
                if(p.getTrabajador() != null)
                    escribir.print(InformeXLS.leoCampo(p.getTrabajador().getNroDocumento()));
                escribir.print("|");
//                if(p.getClase().trim().equals(Publicacion.CLASE_PUBLICACION)){
//                    clase = "1";
//                    tipo = "TiposPublicacion";
//                }
//                if(p.getClase().trim().equals(Publicacion.CLASE_INVESTIGACION)){
//                    clase = "2";
//                    tipo = "TiposTrabajosInvestigacion";
//                }
                escribir.print(clase);
                escribir.print("|");
//                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar(tipo, InformeXLS.leoCampo(InformeXLS.leoCampo(p.getTipo())), errores, session));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(p.getTitulo()));
                escribir.print("|");
                escribir.print(InformeXLS.datetoString(p.getFecha()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(p.getDescripcion()));
                escribir.print("|");
//                escribir.print(InformeXLS.booleanToString(p.getEntidad()));
//                escribir.println();
            }
            escribir.close();

        } catch (Exception ex) {
            errores.add(ERROR_GENERANDO_EL_ARCHIVO + " COMPTRA7.csv");
        }
        
        return errores;
    }

    public static List<String> creadorCSVRemuneracionPersonal(List<RemuneracionPersonalCSV> lrpcsv, String file, Session session) {
        List<String> errores = new LinkedList<String>();

        try {
            PrintWriter escribir = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            for (RemuneracionPersonalCSV rpcsv : lrpcsv) {
                escribir.print(InformeXLS.leoCampo(rpcsv.getCodigo_entidadUE()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(rpcsv.getCodigo_cargo()));
                escribir.print("|");
                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("TipoDocumento", InformeXLS.leoCampo(rpcsv.getTipo_documento()), errores, session));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(rpcsv.getNro_documento()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(rpcsv.getCodigo_concepto_remunerativo()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(rpcsv.getImporte()));
                escribir.println();
            }
            escribir.close();

        } catch (Exception ex) {
            errores.add(ERROR_GENERANDO_EL_ARCHIVO + " COMPCAR1.csv");
        }
        
        return errores;
    }

    public static List<String> creadorCSVEvaluacion(List<EvaluacionPersonalCSV> lepcsv, String file, Session session) {
        List<String> errores = new LinkedList<String>();

        try {
            PrintWriter escribir = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            for (EvaluacionPersonalCSV epcsv : lepcsv) {
                escribir.print(InformeXLS.leoCampo(epcsv.getCodigo_entidadUE()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(epcsv.getCodigo_cargo()));
                escribir.print("|");
                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("TipoDocumento", InformeXLS.leoCampo(epcsv.getTipo_documento()), errores, session));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(epcsv.getNro_documento()));
                escribir.print("|");
                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("TipoEvaluaciones", InformeXLS.leoCampo(epcsv.getTipo()), errores, session));
                escribir.print("|");
                escribir.print(InformeXLS.datetoString(epcsv.getFecha_desde()));
                escribir.print("|");
                escribir.print(InformeXLS.datetoString(epcsv.getFecha_hasta()));
                escribir.print("|");
                escribir.print(InformeXLS.numberToString(epcsv.getCalificacion_procentaje()));
                escribir.println();
            }
            escribir.close();

        } catch (Exception ex) {
            errores.add(ERROR_GENERANDO_EL_ARCHIVO + " COMPCAR2.csv");
        }
        
        return errores;
    }

    public static List<String> creadorCSVAusenciaLicencia(List<AusLicPersonalCSV> lalcsv, String file, Session session) {
        List<String> errores = new LinkedList<String>();

        try {
            PrintWriter escribir = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            for (AusLicPersonalCSV alcsv : lalcsv) {
                escribir.print(InformeXLS.leoCampo(alcsv.getCodigoEntidadUE()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(alcsv.getCodigo_cargo()));
                escribir.print("|");
                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("TipoDocumento", InformeXLS.leoCampo(alcsv.getTipo_documento()), errores, session));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(alcsv.getNro_documento()));
                escribir.print("|");
                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("TipoAusencias", InformeXLS.leoCampo(alcsv.getTipo()), errores, session));
                escribir.print("|");
                escribir.print(InformeXLS.datetoString(alcsv.getFecha_desde()));
                escribir.print("|");
                escribir.print(InformeXLS.datetoString(alcsv.getFecha_hasta()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(alcsv.getMotivo()));
                escribir.println();
            }
            escribir.close();

        } catch (Exception ex) {
            errores.add(ERROR_GENERANDO_EL_ARCHIVO + " COMPCAR3.csv");
        }
        
        return errores;
    }

    public static List<String> creadorCSVConstanciaDocumental(List<ConstanciaDocumental> lcd, String file, Session session) {
        List<String> errores = new LinkedList<String>();

        try {
            PrintWriter escribir = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            for (ConstanciaDocumental cd : lcd) {
                if(cd.getLegajo() != null){
                    if(cd.getLegajo().getEntidad() != null)
                        escribir.print(InformeXLS.leoCampo(cd.getLegajo().getEntidad().getCue_entidad()));
                }
                escribir.print("|");
                if(cd.getLegajo() != null)
                    escribir.print(InformeXLS.leoCampo(cd.getLegajo().getCod_legajo()));
                escribir.print("|");
                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("CategoríaConstancia", InformeXLS.leoCampo(InformeXLS.leoCampo(cd.getCat_constancia())), errores, session));
                escribir.print("|");
                escribir.print(CreadorDesdeDB.getCodigoFromValorDatoAuxiliar("DatoConstancia", InformeXLS.leoCampo(InformeXLS.leoCampo(cd.getTip_constancia())), errores, session));
                escribir.print("|");
                escribir.print(InformeXLS.datetoString(cd.getFecha()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(cd.getNum_resolucion()));
                escribir.print("|");
                escribir.print(InformeXLS.leoCampo(cd.getTxt_descriptivo()));
                escribir.println();
            }
            escribir.close();

        } catch (Exception ex) {
            errores.add(ERROR_GENERANDO_EL_ARCHIVO + " COMPLEG1.csv");
        }
        
        return errores;
    }

    public static String leoCampo(String valor) {
        
        if (valor == null) {
            return "";
        }

        if ("".equals(valor.trim())) {
            return "";
        }

        return valor;
        
        
    }

    public static String datoAuxiliarToString(DatoAuxiliar valor) {

        if (valor == null) {
            return "";
        }

        return String.valueOf(valor.getCodigo());
    }

    public static String datetoString(Date valor) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        if (valor == null) {
            return "";
        }

        return sdf.format(valor);
    }

    public static String numberToString(Number valor) {

        if (valor == null) {
            return "";
        }

        return valor.toString();
    }

    public static String booleanToString(Boolean valor) {

        if (valor == null || valor.toString().equals("") || valor.toString().equals("F") || valor == false) {
            return FALSE_ARCHIVO;
        }

        return TRUE_ARCHIVO;
    }
    
       private static String getBoolStrFromEstado(String estado){
        if ((estado != null) || (!estado.trim().equals(""))) {
            if (estado.trim().equals(Cargoxunidad.ESTADO_ALTA) || estado.trim().equals("Activo")) { 
                return "1";
            } 
            
            if (estado.trim().equals(Cargoxunidad.ESTADO_BAJA)) { 
                return "0";
            } 

        }
        return "0";
    }
}
