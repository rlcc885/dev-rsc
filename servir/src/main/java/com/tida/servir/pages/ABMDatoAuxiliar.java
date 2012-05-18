package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.DatoAuxiliar.TipoDeAcceso;

import helpers.Errores;
import com.tida.servir.entities.Usuario;
import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.tapestry5.ajax.MultiZoneUpdate;

import org.hibernate.Session;

import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.services.Request;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

/**
 *
 *	Clase que maneja las tablas auxiliares
 * Si una tabla tiene hijos, montamos el componente de subtabla.
 * Hay que dar la posibilidad de generar hijos cuando no los tiene.
 *  
 */
public class ABMDatoAuxiliar extends GeneralPage {

    @Inject
    private Session session;
    @Property
    @SessionState
    private Usuario loggedUser;
    @Property
    @Persist
    private String tabla;
    /**
     * Utilizado por la Grilla para discernir cada elemento
     */
    @Property
    private DatoAuxiliar t;

    /*
     * Utilizado como dato para agregar
     */
    @Property
    @Persist
    private DatoAuxiliar da;

    /*
     * Indica si hay una tabla seleccionada
     */
    @Property
    @Persist
    private boolean hayTabla;
    @Property
    @Persist
    private boolean agregarConcepto;
    @Property
    @Persist
    private List<String> errores;
    @Property
    private String e;
    @Component(id = "formularioselecttabla")
    private Form formularioSelectTabla;
    @InjectComponent
    private Zone listaConceptosZone;
    @InjectComponent
    private Zone agregaConceptosZone;
    // Guardamos los parámetros de la búsqueda para cuando borramos, retornarlo
    @Persist
    private String tablaRelacion;
    @Property
    @Persist
    private boolean hayError;
    @Persist
    private long relacionCodigo;
    @Property
    @Persist
    private TipoDeAcceso tipoDeAcceso;
    @InjectComponent
    private Envelope envelope;
    @Inject
    private Request request;

    public ABMDatoAuxiliar() {
    }

    public List<String> getTablas() {
        hayError = false;
        Query c;
        c = session.createQuery("select distinct datoauxiliar.nombreTabla from DatoAuxiliar datoauxiliar where datoauxiliar.nombreTabla is not null");
        tablaRelacion = null;

        List<String> tablas = c.list();
        Collections.sort(tablas, String.CASE_INSENSITIVE_ORDER);

        return tablas;
    }

    public List<DatoAuxiliar> getTablaAux() {
        hayError = false;
        return obtenerTabla(null, 0);
    }

    public Object onValueChanged(Object o) {
        // El select de la tabla
        // Ya tenemos en tabla el valor.
        tabla = (String) o;

        hayTabla = true;
        hayError = false;
        agregarConcepto = false;

        calcularTipoDeAcceso();

        return dosZones();

    }

    private void calcularTipoDeAcceso() {
        tipoDeAcceso = TipoDeAcceso.SoloLectura;
        Query q = session.createQuery("select min(datoauxiliar.tipoDeAcceso) from DatoAuxiliar datoauxiliar where datoauxiliar.nombreTabla = ?");
        q.setString(0, tabla);
        TipoDeAcceso resultado = (TipoDeAcceso) q.uniqueResult();
        if (resultado != null) {
            tipoDeAcceso = resultado;
            System.out.println("Cambiado: " + tipoDeAcceso);
        } else {
            System.out.println("Quedó igual: " + tipoDeAcceso);
        }
    }

    // Accion de borrar dato auxiliar
    @CommitAfter
    @Log
    Object onBorrarDato(DatoAuxiliar dato) {
        if (tipoDeAcceso != tipoDeAcceso.Editable) {
            errores = new ArrayList<String>();
            hayError = true;
            System.out.println("Error: No se puede borrar un dato con tipo de acceso " + tipoDeAcceso.toString());
            errores.add("Error: No se puede borrar un dato con tipo de acceso " + tipoDeAcceso.toString());
        } else {
            // No borro si es el último dato de la tabla o si tiene hijos
            if ((obtenerTabla(null, 0).size() > 1) && (!(obtenerTabla(dato.getNombreTabla(), dato.getCodigo()).size() > 0))) {
                // Debería entrar siempre acá porque se activó la opción de borrar
                // Lo borro de la base
                try {
                    session.delete(dato);
                    session.flush();
                }  catch (HibernateException e) {
                    errores = new ArrayList<String>();
                    hayError = true;
                    errores.add("No se puede borrar el dato por tener registros asociados");
                }

            }
        }
        if (!request.isXHR()) {
            return this;
        }

        return dosZones();// La/a zona a actualizar
    }

    private List<DatoAuxiliar> obtenerTabla(String tablaRelacion, long relacionCodigo) {
        Criteria c = session.createCriteria(DatoAuxiliar.class);
        if (tablaRelacion != null) {
            c.add(Restrictions.eq("tablaRelacion", tablaRelacion));
            c.add(Restrictions.eq("relacionCodigo", relacionCodigo));
        } else {
            // No hay restricciones, muestro toda la tabla
            c.add(Restrictions.eq("nombreTabla", tabla));
        }
        return c.list();
    }

    public boolean getHayHijos() {
        // tiene t como contexto
        // vemos si el objeto que se está viendo tiene hijos.
        return obtenerTabla(t.getNombreTabla(), t.getCodigo()).size() > 0;
    }

    public boolean getEsBorrable() {
        boolean borrar;
        borrar = true;

        borrar &= (tipoDeAcceso == tipoDeAcceso.Editable);

        // Doble chequeo con Borrar
        // sólo puedo borrar los elementos que no tienen hijos

        // veo que no tenga hijos
        //

        //veo que no sea el último elemento en la tabla, ojo, en la tabla, no en la búsqueda
        borrar &= !(obtenerTabla(t.getNombreTabla(), t.getCodigo()).size() > 0);
        borrar &= obtenerTabla(null, 0).size() > 1;
        return borrar;
    }

    // Accion de  ver la tabla auxiliar de hijos de una tabla
    Object onTablaHijos(DatoAuxiliar dato) {

        // Los hijos tienen todos que tener la misma tabla entre sí.
        tablaRelacion = dato.getNombreTabla();
        relacionCodigo = dato.getCodigo();

        tabla = obtenerTabla(tablaRelacion, relacionCodigo).get(0).getNombreTabla();
        agregarConcepto = false;
        hayError = false;

        if (!request.isXHR()) {
            return this;
        }

        return dosZones();// La/a zona a actualizar
    }

    // Accion de agregar uno nuevo
    Object onAgregarNuevo() {
        // Agregamos uno que tenga las mismas restricciones que los datos que hay en la tabla.
        agregarConcepto = true;
        da = new DatoAuxiliar();
        // El nuevo objeto tiene las mismas relaciones que su padre, si es null tablaRelacion, se ocupa el sistema
        List<DatoAuxiliar> datosTabla = obtenerTabla(tablaRelacion, relacionCodigo);
        da.setNombreTabla(datosTabla.get(0).getNombreTabla());
        da.setTablaRelacion(datosTabla.get(0).getTablaRelacion());
        da.setRelacionCodigo(datosTabla.get(0).getRelacionCodigo());
        da.setTipoDeAcceso(tipoDeAcceso);
        if (!request.isXHR()) {
            return this;
        }
        return dosZones();// La/a zona a actualizar

    }

    private MultiZoneUpdate dosZones() {
        MultiZoneUpdate mu;
        mu = new MultiZoneUpdate("agregaConceptosZone", agregaConceptosZone.getBody()).add("listaConceptosZone", listaConceptosZone.getBody());
        return mu;
    }

    @Log
    @CommitAfter
    Object onSuccessFromFormularioAgregoConcepto() {
        errores = new ArrayList<String>();
        // veo que no hayan códigos repetidos
        Criteria c = session.createCriteria(DatoAuxiliar.class);
        c.add(Restrictions.eq("nombreTabla", da.getNombreTabla()));
        c.add(Restrictions.eq("codigo", da.getCodigo()));
        if (c.list().size() > 0) {
            hayError = true;
            System.out.println("Error:" + Errores.DATOAUXILIAR_CODIGO_REPETIDO);

            errores.add(Errores.DATOAUXILIAR_CODIGO_REPETIDO);
        }

        // veo que si hay una relación, que exista el campo relacionado
        if ((da.getTablaRelacion() != null) && (da.getTablaRelacion().equals(new String("")))) {
            c = session.createCriteria(DatoAuxiliar.class);
            c.add(Restrictions.eq("nombreTabla", da.getTablaRelacion()));
            c.add(Restrictions.eq("codigo", da.getRelacionCodigo()));
            if (c.list().isEmpty()) {
                errores.add(Errores.DATOAUXILIAR_NO_TABLA_RELACION);
                System.out.println("Error:" + Errores.DATOAUXILIAR_NO_TABLA_RELACION);

                hayError = true;
            }
        }

        if (!hayError) {
            session.saveOrUpdate(da);

        }
        agregarConcepto = false; // hago que no se pueda agregar concepto
        envelope.setContents(helpers.Constantes.DATO_AUXILIAR_EXITO);
        return dosZones();// La/a zona a actualizar

    }

    @Log
    void onPrepareFromFormularioAgregoConcepto() {
    }

    /*
    Cargar desde los parámetros
     */
    void onActivate() {
    }

    Usuario onPassivate() {
        return null;
    }
}
