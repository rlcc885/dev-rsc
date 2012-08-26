/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import com.tida.servir.components.ComponenteMenu; 

/**
 *
 * @author ale
 */
public class Accesos {

    public static enum MENUPADRE {

        Usuarios, Entidades, TablasAuxiliares, Parametros,
        Organigrama, Trabajadores, ConceptosRemunerativos, ProcesoBatch, ProcesoBatchUpload,
        DeteccionIntrusion, CambiarClave, Salir
    };

    public static enum MENUHIJO {

        AdministrarUsuarios, ReportesUsuarios, AdministracionEntidades, ReportesEntidades,
        SeleccionEntidades, UnidadesOrganicas, AdministracionTablas, Cargos, ReportesEstructura,
        AltaTrabajador, ModificacionTrabajador, BajaTrabajador, ReportesTrabajador, TrabajadorPage,
        AdministracionConceptos, CambioUOEntidad, ReportesNivel, DescargaArchivoCSV
    };

    /**
     * A partir del menú padre, obtiene el nombre de dicho menú
     *
     * @param item
     * @return
     */
    public static String getNombrePadre(MENUPADRE item) {
        String nombre = "";

        switch (item) {
            case Usuarios:
                nombre = "Usuarios";
                break;

            case Entidades:
                nombre = "Entidades";
                break;

            case TablasAuxiliares:
                nombre = "Tablas auxiliares";
                break;

            case Parametros:
                nombre = "Parámetros";
                break;

            case Organigrama:
                nombre = "Organigrama";
                break;

            case Trabajadores:
                nombre = "Trabajadores";
                break;

            case ConceptosRemunerativos:
                nombre = "Conceptos";
                break;

            case ProcesoBatch:
                nombre = "Proceso batch";
                break;

            case ProcesoBatchUpload:
                nombre = "Proceso batch";
                break;

            case DeteccionIntrusion:
                nombre = "Det. Intrusión";
                break;

            case CambiarClave:
                nombre = "Cambiar clave";
                break;

            case Salir:
                nombre = "Salir";
                break;


            default:
                break;
        }
        return nombre;
    }

    /*
     * A partir del menú hijo, obtiene el nombre
     */
    public static String getNombreHijo(MENUHIJO item) {
        String nombre = "";
        switch (item) {
            case AdministrarUsuarios:
                nombre = "Administrar usuarios";
                break;
            case ReportesUsuarios:
                nombre = "Reportes de usuarios";
                break;

            case AdministracionEntidades:
                nombre = "Administrar entidades";
                break;
            case ReportesEntidades:
                nombre = "Reportes de entidades";
                break;
            case SeleccionEntidades:
                nombre = "Selección";
                break;
            case DescargaArchivoCSV:
                nombre = "Exportación";
                break;
            case CambioUOEntidad:
                nombre = "Migrar / Fusión UO";
                break;
            case UnidadesOrganicas:
                nombre = "Unidades orgánicas";
                break;
            case AdministracionTablas:
                nombre = "Administración de tablas";
                break;
            case Cargos:
                nombre = "Cargos";
                break;
            case ReportesEstructura:
                nombre = "Reportes de estructura";
                break;
            case ReportesNivel:
                nombre = "Reportes Niv. Sect. Pliego.";
                break;
            case AltaTrabajador:
                nombre = "Alta";
                break;
            case ModificacionTrabajador:
                nombre = "Administrar";
                break;
            case TrabajadorPage:
                nombre = "Administrar";
                break;
            case BajaTrabajador:
                nombre = "Baja";
                break;
            case ReportesTrabajador:
                nombre = "Reportes";
                break;
            case AdministracionConceptos:
                nombre = "Administrar Conceptos";
                break;
        }
        return nombre;
    }

    /**
     * A partir del menú padre, obtiene, de existir el nombre de la página
     *
     * @param item
     * @return
     */
    public static String getPagePadreName(MENUPADRE item) {
        String nombre = "";
        /*
         * String nombreAyuda = ""; ComponenteMenu menu = new ComponenteMenu(); nombreAyuda =
         * menu.getCurrentPadrePageName();
         * System.out.println("------------------------------ page padre hijo de
         * puta "+nombreAyuda);
         */

        switch (item) {
            case Usuarios:
                nombre = null;
                break;

            case Entidades:
                nombre = null;
                break;

            case TablasAuxiliares:
                nombre = null;
                break;

            case Parametros:
                nombre = "ConfigurarAcceso";
                break;

            case Organigrama:
                nombre = null;
                break;

            case Trabajadores:
                nombre = null;
                break;

            case ConceptosRemunerativos:
                nombre = null;
                break;

            case ProcesoBatch:
                nombre = "batch_dev";
                break;

            case ProcesoBatchUpload:
                nombre = "batch_dev";
                break;

            case DeteccionIntrusion:
                nombre = "DeteccionIntrusion";
                break;

            case CambiarClave:
                nombre = "cambiarclave";
                break;

            case Salir:
                nombre = "Index";
                break;

            default:
                break;
        }
        return nombre;
    }

    /*
     * A partir del menú hijo, obtiene el nombre
     */
    public static String getPageHijoName(MENUHIJO item) {
        String nombre = "";
        switch (item) {
            case AdministrarUsuarios:
                nombre = "abmusuario";
                break;
            case ReportesUsuarios:
                nombre = "ReportesUsuarios";
                break;

            case AdministracionEntidades:
                nombre = "amentidaduejecutora";
                break;
            case ReportesEntidades:
                nombre = "ReportesEntidades";
                break;
            case SeleccionEntidades:
                nombre = "cambioentidad";
                break;
            case DescargaArchivoCSV:
                nombre = "DescargaArchivoCSV";
                break;
            case CambioUOEntidad:
                nombre = "CambioUOEntidad";
                break;
            case UnidadesOrganicas:
                nombre = "amunidadorganica";
                break;
            case AdministracionTablas:
                nombre = "abmdatoauxiliar";
                break;
            case Cargos:
                nombre = "abmcargos";
                break;
            case ReportesEstructura:
                nombre = "ReportesEstructura";
                break;
            case ReportesNivel:
                nombre = "ReportesNivel";
                break;
            case AltaTrabajador:
                nombre = "trabajadornuevo";
                break;
            case ModificacionTrabajador:
                nombre = "busqueda";
                break;
            case TrabajadorPage:
                nombre = "TrabajadorEditar";
                break;
            case BajaTrabajador:
                nombre = "BajaTrabajador";
                break;
            case ReportesTrabajador:
                nombre = "ReportesGlobalesTrabajador";
                break;
            case AdministracionConceptos:
                nombre = "abmconceptosremunerativos";
                break;
        }
        return nombre;
    }
}
