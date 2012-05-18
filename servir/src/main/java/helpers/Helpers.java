/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import com.tida.servir.entities.Cargo;
import com.tida.servir.entities.CargoAsignado;
import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.EntidadUEjecutora;
import com.tida.servir.entities.Legajo;
import com.tida.servir.entities.UnidadOrganica;
import com.tida.servir.entities.Usuario;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ale
 */
public class Helpers {
    public static List<DatoAuxiliar> getValoresTablaAuxiliar(String tabla, Session session){
        Criteria c = session.createCriteria(DatoAuxiliar.class);
        c.add(Restrictions.eq("nombreTabla", tabla));
        return c.list();
    }

    public static List<String> getValorTablaAuxiliar(String tabla, Session session) {
        // TODO: este codigo esta duplicado
        Criteria c = session.createCriteria(DatoAuxiliar.class);
        c.add(Restrictions.eq("nombreTabla", tabla));
        c.setProjection(Projections.property("id"));
        c.setProjection(Projections.property("valor"));
        return c.list();
    }

    public static List<String> getValorTablaAuxiliar(String tabla, Session session,
            String tablaRelacion,
            long relacionCodigo) {
        // TODO: este codigo esta duplicado
        Criteria c = session.createCriteria(DatoAuxiliar.class);
        c.add(Restrictions.eq("nombreTabla", tabla));
        c.add(Restrictions.eq("tablaRelacion", tablaRelacion));
        c.add(Restrictions.eq("relacionCodigo", relacionCodigo));
        c.setProjection(Projections.property("valor"));
        return c.list();
    }

    public static boolean validateDate(Date testDate) {

        try {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setLenient(false);        // must do this
            gc.set(GregorianCalendar.YEAR, testDate.getYear());
            gc.set(GregorianCalendar.MONTH, testDate.getMonth());// invalid month
            gc.set(GregorianCalendar.DATE, testDate.getDate());

            gc.getTime(); // exception thrown here
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Obtiene las tablas en forma de objeto de tablas auxiliares.
     * @param tabla
     * @param tablaRelacion
     * @param relacionCodigo
     * @return
     */
    public static List<DatoAuxiliar> getDatoAuxiliar(String tabla, String tablaRelacion,
            long relacionCodigo, Session session) {
        Criteria c = session.createCriteria(DatoAuxiliar.class);
        c.add(Restrictions.like("nombreTabla", tabla));
        if (tablaRelacion != null) {
            if (!tablaRelacion.equals("")) {
                c.add(Restrictions.like("tablaRelacion", tablaRelacion));
                c.add(Restrictions.eq("relacionCodigo", relacionCodigo));

            }
        }
        return c.list();
    }

    /**
     * Obtiene el dato auxiliar de la tabla y códigos dados
     * @param tabla
     * @param codigo
     * @param session
     * @return
     */
    public static DatoAuxiliar getDatoAuxiliar(String tabla, Long codigo, Session session) {

        Criteria c = session.createCriteria(DatoAuxiliar.class);
        c.add(Restrictions.like("nombreTabla", tabla));
        c.add(Restrictions.like("codigo", codigo));

        if (c.list().isEmpty()) {
            return null;
        }

        return (DatoAuxiliar) c.list().get(0);
    }

    /**
     * Obtiene un dato auxiliar segun tabla y valor
     * @param tabla
     * @param valor
     * @param session
     * @return
     */
    public static DatoAuxiliar getDatoAuxiliar(String tabla, String valor, Session session) {
        List<DatoAuxiliar> ld;
        Criteria c = session.createCriteria(DatoAuxiliar.class);
        c.add(Restrictions.like("nombreTabla", tabla));
        c.add(Restrictions.like("valor", valor));
        ld = c.list();
        if (ld.size() > 0) {
            return (DatoAuxiliar) c.list().get(0);
        } else {
            return null;
        }
    }


    /**
     * Calcula si es válido o no un agrupamiento de datos auxiliares de ubigeo
     * @param depto - El valor del departamento. UBDepartamento
     * @param prov - El valor de la provincia. UBProvincia
     * @param dist - El valor del distrito. UBDistrito
     */

    public static Boolean isUbigeoValido(DatoAuxiliar depto, DatoAuxiliar prov, DatoAuxiliar dist, Session session) {
        if(dist == null) {
            if (prov == null)
                return true;
            else {
                if(depto == null)
                    return false; // prov no nulo y depto nulo.
            return (prov.getRelacionCodigo() == depto.getCodigo());

            }
        } else {
            if (prov == null)
                return false;
            if (dist == null)
                return false;

        }
        return (dist.getRelacionCodigo() == prov.getCodigo()) && (prov.getRelacionCodigo() == depto.getCodigo());
    }

    /**
     * Calcula para un cargo la cantidad de puestos que tiene ocupados
     * @param session
     * @param cargo
     * @return
     */
    public static Integer getCantPuestosOcupadosCargo(Session session, Cargo cargo) {
        Criteria c = session.createCriteria(CargoAsignado.class);
        c.add(Restrictions.eq("cargo", cargo));
        c.add(Restrictions.eq("estado", Constantes.ESTADO_ACTIVO));

        return c.list().size();
    }

    /**
     * Según el usuario pasado devuelve si opera en varios organismos o no
     * @param user
     * @return
     */
    public static boolean esMultiOrganismo(Usuario user) {
        return (user.getTipo_usuario().equals(Usuario.OPERADORABMSERVIR)
                || user.getTipo_usuario().equals(Usuario.OPERADORANALISTA));
    }

    public static Integer maxNivelUO(EntidadUEjecutora eue, Session session) {
        Criteria c;
        Integer nivelMax;
        c = session.createCriteria(UnidadOrganica.class);
        c.add(Restrictions.eq("entidadUE", eue));
        c.add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA));
        ProjectionList projList = Projections.projectionList();
        projList.add(Projections.max("nivel"));
        c.setProjection(projList);

        if (c.list().isEmpty()) {
            nivelMax = 0;
        } else {
            nivelMax = (Integer) c.list().get(0);
            if (nivelMax == null) {
                nivelMax = 0;
            }
        }
        return nivelMax;
    }

    /**
     * Fusiona la uo con la entidad destino y la pone los cargos y las hijas debajo de UoPadreDestino
     * Maneja la base.
     * @param uo
     * @param entidadDestino
     * @param uoPadreDestino
     */
    @CommitAfter
    public static void fusionarUOBase(UnidadOrganica uo, EntidadUEjecutora entidadOrigen,
            EntidadUEjecutora entidadDestino, UnidadOrganica uoPadreDestino, Session session) {

        uo.setEstado(UnidadOrganica.ESTADO_BAJA);
        session.saveOrUpdate(uo);

        Criteria c = session.createCriteria(Cargo.class);
        c.add(Restrictions.eq("und_organica", uo));
        List<Cargo> lc = c.list();
        System.out.println("---------- Fusionando cant. cargos:" + lc.size());
        migrarCargos(entidadOrigen, entidadDestino, uoPadreDestino, lc, session);

        for (UnidadOrganica uoHija : uoHijas(uo, session)) {
            migrarUnidad(uoHija, entidadOrigen, entidadDestino, uoPadreDestino, session);
        }

    }

    /**
     * Migra la uo hacia la entidad destino y la pone debajo de uoPadreDestino.
     * Maneja la base.
     * @param uo
     * @param entidadDestino
     * @param uoPadreDestino
     */
    @CommitAfter
    public static void migrarUOBase(UnidadOrganica uo, EntidadUEjecutora entidadOrigen,
            EntidadUEjecutora entidadDestino, UnidadOrganica uoPadreDestino, Session session) {
        migrarUnidad(uo, entidadOrigen, entidadDestino, uoPadreDestino, session);
    }

    public static List<UnidadOrganica> uoHijas(UnidadOrganica uo, Session session) {
        Criteria c = session.createCriteria(UnidadOrganica.class);
        c.add(Restrictions.eq("uoAntecesora", uo));
        return c.list();
    }

    /**
     * Migra la uo hacia la entidad destino y la pone debajo de uoPadreDestino.
     * Maneja las que tienen alguna arriba.
     * @param uo
     * @param entidadOrigen
     * @param entidadDestino
     * @param uoPadreDestino
     */
    @CommitAfter
    public static void migrarUnidad(UnidadOrganica uo, EntidadUEjecutora entidadOrigen,
            EntidadUEjecutora entidadDestino, UnidadOrganica uoPadreDestino, Session session) {
        if (uoPadreDestino == null) {
            uo.setUoAntecesora(null);
            uo.setNivel(1);
        } else {
            uo.setUoAntecesora(uoPadreDestino);
            uo.setNivel(uoPadreDestino.getNivel() + 1);
        }
        uo.setEntidadUE(entidadDestino);
        if (!entidadOrigen.equals(entidadDestino)) {
            uo.setCod_und_organica(entidadDestino.getCodigoEntidadUE() + "-" + uo.getCod_und_organica());
        }
        session.saveOrUpdate(uo);


        Criteria c = session.createCriteria(Cargo.class);
        c.add(Restrictions.eq("und_organica", uo));
        List<Cargo> lc = c.list();

        migrarCargos(entidadOrigen, entidadDestino, uoPadreDestino, lc, session);

        for (UnidadOrganica uoHija : uoHijas(uo, session)) {
            migrarUnidad(uoHija, entidadOrigen, entidadDestino, uo, session);
        }

    }

    /**
     * Migra los cargos de un lado al otro. Para eso sólo hace falta cambiarle a
     * cada uno de ellos el código del cargo
     * Migra los cargos asignados también a esos cargos, creando los legajos necesarios
     * o haciendo nuevos en la nueva entidad
     * @param entidadOrigen
     * @param entidadDestino
     * @param cargos
     */
    @CommitAfter
    public static void migrarCargos(EntidadUEjecutora entidadOrigen,
            EntidadUEjecutora entidadDestino, UnidadOrganica uoDestino, List<Cargo> cargos, Session session) {
        // 
        Criteria c, c1;
        List<CargoAsignado> lca;
        for (Cargo cargo : cargos) {
            cargo.setUnd_organica(uoDestino);
            if (!entidadOrigen.equals(entidadDestino)) {

                cargo.setCod_cargo(entidadDestino.getCodigoEntidadUE() + "-"
                        + cargo.getCod_cargo());
                // Ahora migro los legajos y los cargos asignados.
                c = session.createCriteria(CargoAsignado.class);
                c.add(Restrictions.eq("cargo", cargo));
                lca = c.list();
                for (CargoAsignado ca : lca) {
                    // cada cargo asignado lo tengo que meter en un legajo del nuevo organismo.
                    c1 = session.createCriteria(Legajo.class);
                    c1.add(Restrictions.eq("entidadUE", entidadDestino));
                    c1.add(Restrictions.eq("trabajador", ca.getTrabajador()));
                    List<Legajo> ll = c1.list();
                    if (ll.size() > 0) {

                        // ya tiene legajo en la entidad.
                        ca.setLegajo(ll.get(0));
                        session.saveOrUpdate(ca);
                    } else {
                        // no tiene legajo en la entidad
                        Legajo l = new Legajo();
                        l.setCod_legajo(entidadDestino.getCodigoEntidadUE() + "-" + ca.getTrabajador().getNroDocumento());
                        l.setEntidadUE(entidadDestino);
                        l.setTrabajador(ca.getTrabajador());
                        session.saveOrUpdate(l);
                        ca.setLegajo(l);
                    }
                    session.saveOrUpdate(ca);
                }
            }

            session.saveOrUpdate(cargo);

        }


    }
}
