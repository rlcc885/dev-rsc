package com.tida.servir.entities;

import helpers.Constantes;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ale
 */
public class CrearDatos {

    public static final void crearDatosBasicos(Session session) {


        Entidad_BK _entidadUEjecutora = new Entidad_BK();

        //Metemos en el "usuario" el organismo informante.
        _entidadUEjecutora = new Entidad_BK();
        _entidadUEjecutora.setDenominacion("Organismo Informante 1");
        _entidadUEjecutora.setEstado(Entidad_BK.ESTADO_ALTA);
        _entidadUEjecutora.setCod_mef_ue(2345);
        //_entidadUEjecutora.setCod_ubi_dept();
        
        session.persist(_entidadUEjecutora);

        // También creamos un cargo, si no existe.
        List<Cargoxunidad> lc = session.createCriteria(Cargoxunidad.class).list();
        if (lc.isEmpty()) {
            Cargoxunidad c = new Cargoxunidad();
            c.setDen_cargo("Den Cargo1");
            c.setEstado(Constantes.ESTADO_ACTIVO);
            c.setHoras_x_sem(40);
            c.setReq_hab_profesional(true);
//                    c.setPlantillaremunerativa(4);
            c.setDec_jurada_byr(true);
            c.setPresupuestado_PAP(true);
            //c.setSituacion_CAP(2);
            c.setCod_cargo("312");

            UnidadOrganica uo;
            List<UnidadOrganica> lUo = session.createCriteria(UnidadOrganica.class).list();
            if (lUo.isEmpty()) {
                uo = new UnidadOrganica();
                uo.setCod_und_organica("UO312");
                uo.setDen_und_organica("UnidadOrganica 1");
                uo.setEstado(UnidadOrganica.ESTADO_ALTA);
                uo.setLocalidad("Localidad 1");
                uo.setEntidadUE(_entidadUEjecutora);
                
                // sacado porque ahora son datos auxiliares.
                // uo.setCod_ubi_dept("Depto1");
                // uo.setCod_ubi_dist("Dist1");
                // uo.setCod_ubi_prov("Prov 1");

                /*Organo o;
                List<EntidadUEjecutora> lo = session.createCriteria(Organo.class).list();
                if (lo.isEmpty()) {
                    o = new Organo();
                    o.setCod_organo("Organo 122");
                    o.setDen_organo("Denominacion organo");
                    o.setEntidadUE(_entidadUEjecutora);
                    o.setEstado(Organo.ESTADO_ALTA);
                    session.persist(o);

                } else {
                    o = lo.get(0);
                }

                uo.setOrgano(o);*/
                session.persist(uo);
            } else {
                uo = lUo.get(0);
            }
            c.setUnd_organica(uo);

            session.persist(c);

        }


    }

    /**
     * Tabla Código estructural del cargo
     */
    /*
    public static final void crearCodigosEstructuralesCargo(Session session) {


    List<DatoAuxiliar> categorias;
    DatoAuxiliar d;

    categorias = new ArrayList();
    categorias.add("01-F1- Funcionarios y Directivos - F1");
    categorias.add("01-F2- Funcionarios y Directivos - F2");
    categorias.add("02-01- Magistrados - Vocales y Fiscales Supremos");
    categorias.add("02-02- Magistrados - Vocales y Fiscales Superiores, procurador");
    categorias.add("03-01- Diplomáticos - 01");
    categorias.add("03-02- Diplomáticos - 02");


    for(int i = 1; i <= categorias.size(); i++) {
    d = new DatoAuxiliar();
    d.setNombreTabla("CodigoEstructuralCargo");
    d.setValor(categorias.get(i-1));
    d.setCodigo(i);
    session.persist(d);
    }
    d = new DatoAuxiliar();
    d.setNombreTabla("CodigoEstructuralCargoNivelCategoria");
    d.setValor("Categoría 1, Grupo 1");
    d.setCodigo(1);
    d.setTablaRelacion("CodigoEstructuralCargoNivelGrupo");
    d.setRelacionCodigo(1);
    session.persist(d);


    }
     */
    /**
     * Crea todas las tablas auxiliares provinientes de cargos
     * @param session
     */
    public static final void crearCodigosCargos(Session session) {

        DatoAuxiliar d = new DatoAuxiliar();
        List<String> categorias;

        categorias = new ArrayList();

        categorias.add("Gobierno, políticas públicas");
        categorias.add("Planeamiento");
        categorias.add("Control de gestión");
        categorias.add("Educar");
        categorias.add("Curar, atender la salud");
        categorias.add("Atención directa a ciudadanos, tramitaciones");
        categorias.add("Administración");
        categorias.add("Contabilidad");
        categorias.add("Logística");
        categorias.add("Compras y contrataciones");
        categorias.add("Administración recursos humanos");
        categorias.add("Informática");
        categorias.add("Creación y diseño");
        categorias.add("Transporte");
        categorias.add("Seguridad y vigilancia");
        categorias.add("Limpieza");
        categorias.add("Tareas de oficina");
        categorias.add("Operar maquinas o equipos");
        categorias.add("Trabajos manuales");


        for (int i = 1; i <= categorias.size(); i++) {
            d = new DatoAuxiliar();
            d.setNombreTabla("ClasificadorFuncional");
            d.setValor(categorias.get(i - 1));
            d.setCodigo(i);
            session.persist(d);
        }

    }

    /**
     * Crea algunos datos de los códigos de formación profesional
     * @param session
     */
    public static final void crearFormacionProfesional(Session session) {
        DatoAuxiliar d;

        d = new DatoAuxiliar();
        d.setNombreTabla("FormacionProfesional");
        d.setValor("PROFESIONALES CIENTIFICOS E INTELECTUALES");
        d.setCodigo(100);
        session.persist(d);

        d = new DatoAuxiliar();
        d.setNombreTabla("FormacionProfesional");
        d.setValor("TECNICOS DE NIVEL MEDIO");
        d.setCodigo(200);
        session.persist(d);


        d = new DatoAuxiliar();
        d.setNombreTabla("FormacionProfesional2");
        d.setValor("PROFESIONALES DE LAS CIENCIAS FISICAS, QUIMICAS, MATEMATICAS,ESTADISTICA E INFORMATICA");
        d.setRelacionCodigo(100);
        d.setTablaRelacion("FormacionProfesional");
        d.setCodigo(110);
        session.persist(d);

        d = new DatoAuxiliar();
        d.setNombreTabla("FormacionProfesional3");
        d.setValor("FISICOS Y ASTRONOMOS");
        d.setRelacionCodigo(110);
        d.setTablaRelacion("FormacionProfesional2");
        d.setCodigo(111);
        session.persist(d);

        d = new DatoAuxiliar();
        d.setNombreTabla("FormacionProfesional4");
        d.setValor("ASTROFISICO");
        d.setRelacionCodigo(111);
        d.setTablaRelacion("FormacionProfesional3");
        d.setCodigo(11101);
        session.persist(d);
    }

    /**
     * Crea las tablas de grupo y categoría ocupacional
     * @param session
     */
    public static final void ocupacionalesBasicos(Session session) {

        DatoAuxiliar d = new DatoAuxiliar();
        d.setNombreTabla("GrupoOcupacional");
        d.setValor("Grupo Ocupacional 1");
        d.setCodigo(1);
        session.persist(d);


        d = new DatoAuxiliar();
        d.setNombreTabla("GrupoOcupacional");
        d.setValor("Grupo Ocupacional 2");
        d.setCodigo(2);
        session.persist(d);

        d = new DatoAuxiliar();
        d.setNombreTabla("NivelRemunerativo");
        d.setValor("cat 1 grupo 1");
        d.setTablaRelacion("GrupoOcupacional");
        d.setCodigo(1);
        d.setRelacionCodigo(1);
        session.persist(d);

        d = new DatoAuxiliar();
        d.setNombreTabla("NivelRemunerativo");
        d.setValor("cat 2 grupo 1");
        d.setTablaRelacion("GrupoOcupacional");
        d.setCodigo(2);
        d.setRelacionCodigo(1);
        session.persist(d);


        d = new DatoAuxiliar();
        d.setNombreTabla("NivelRemunerativo");
        d.setValor("cat 1 grupo 2");
        d.setTablaRelacion("GrupoOcupacional");
        d.setCodigo(3);
        d.setRelacionCodigo(2);
        session.persist(d);

    }

    /**
     * Crea los ubigeos básicos necesarios
     * @param session
     */
    public static final void crearUbigeosBasicos(Session session) {

        DatoAuxiliar d = new DatoAuxiliar();
        d.setNombreTabla("UBDepartamento");
        d.setValor("Departamento 1");
        d.setCodigo(1);
        session.persist(d);


        d = new DatoAuxiliar();
        d.setNombreTabla("UBDepartamento");
        d.setValor("Departamento 2");
        d.setCodigo(2);
        session.persist(d);

        d = new DatoAuxiliar();
        d.setNombreTabla("UBProvincia");
        d.setValor("Prov1 depto 1");
        d.setTablaRelacion("UBDepartamento");
        d.setCodigo(1);
        d.setRelacionCodigo(1);
        session.persist(d);

        d = new DatoAuxiliar();
        d.setNombreTabla("UBProvincia");
        d.setValor("Prov2 depto 1");
        d.setTablaRelacion("UBDepartamento");
        d.setCodigo(2);
        d.setRelacionCodigo(1);
        session.persist(d);


        d = new DatoAuxiliar();
        d.setNombreTabla("UBProvincia");
        d.setValor("Prov1 depto 2");
        d.setTablaRelacion("UBDepartamento");
        d.setCodigo(3);
        d.setRelacionCodigo(2);
        session.persist(d);


        d = new DatoAuxiliar();
        d.setNombreTabla("UBDistrito");
        d.setValor("Dist 1, Prov 1, depto 1");
        d.setTablaRelacion("UBProvincia");
        d.setCodigo(1);
        d.setRelacionCodigo(1);
        session.persist(d);

    }

    /**
     * Crea las constancias documentales y sus categorías.
     * @param session
     */
    public static final void crearConstanciasDocumentales(Session session) {

        /**
         * Primero se crean las tablas de las categorías, luego los tipos
         */
        DatoAuxiliar d;

        List<String> categorias = new ArrayList();
        categorias.add("Identificación Personal / familiar");
        categorias.add("Datos personales");
        categorias.add("Formación");
        categorias.add("Carrera laboral");
        categorias.add("Producción intelectual");
        categorias.add("Licencias");
        categorias.add("Evaluaciones");
        categorias.add("Méritos y deméritos");
        categorias.add("Bonificaciones y retenciones");
        categorias.add("Antecedentes laborales");

        for (int i = 1; i <= categorias.size(); i++) {
            d = new DatoAuxiliar();
            d.setNombreTabla("CategoríaConstancia");
            d.setValor(categorias.get(i - 1));
            d.setCodigo(i);
            session.persist(d);
        }

        categorias = new ArrayList();
        categorias.add("Partida de nacimiento");
        categorias.add("DNI");
        categorias.add("Carnet de extranjería");
        categorias.add("Resolución Nacionalización");
        categorias.add("Partida de matrimonio");
        categorias.add("Constancia de convivencia");
        categorias.add("Certificado de nacimiento");
        categorias.add("Acta de nacimiento");
        categorias.add("Partida de nacimiento");
        categorias.add("Constancia de nacimiento");

        /**
         * Ahora agregamos cada uno de los datos de la categoría.
         */
        for (int i = 1; i <= categorias.size(); i++) {
            d = new DatoAuxiliar();
            d.setNombreTabla("DatoConstancia");
            d.setValor(categorias.get(i - 1));
            d.setCodigo(i);
            d.setTablaRelacion("CategoríaConstancia");
            d.setRelacionCodigo(0);
            session.persist(d);
        }

    }

    /**
     * Crea datos básicos iniciales de tablas auxiliares.
     * @param session
     */
    public static final void crearDatosAuxiliares(Session session) {
        //    @ManyToOne(optional = false)
    /*
        DatoAuxiliar nombreTabla;
        private DatoAuxiliar tablaRelacion; // si leue relacionamos con otra tabla
        private DatoAuxiliar relacionValor; // el valor a matchear en la tabla relacio
        private DatoAuxiliar valor; // El valor del campo
         */
        DatoAuxiliar d;
        List<String> categorias;
        // Primero llamo a crear los ubigeos
        crearUbigeosBasicos(session);

        // Creo las tablas básicas

        ConfiguracionAcceso ca = new ConfiguracionAcceso();
        ca.setDuracion_clave(10L);
        ca.setId(1L);
        ca.setIntentos_bloqueo(3L);
        session.persist(ca);


        /**
         * Clasificador funcional de organismos
         */
        d = new DatoAuxiliar();
        d.setNombreTabla("ClasFuncOrg");
        d.setValor("Salud");
        d.setCodigo(1);
        session.persist(d);

        d = new DatoAuxiliar();
        d.setNombreTabla("ClasFuncOrg");
        d.setValor("Educación");
        d.setCodigo(2);
        session.persist(d);

        d = new DatoAuxiliar();
        d.setNombreTabla("ClasFuncOrg");
        d.setValor("Otros");
        d.setCodigo(3);
        session.persist(d);

        /**
         * Tipos de Email
         */
        d = new DatoAuxiliar();
        d.setNombreTabla("TiposEmail");
        d.setValor("Mail Oficial");
        d.setCodigo(1);
        session.persist(d);

        d = new DatoAuxiliar();
        d.setNombreTabla("TiposEmail");
        d.setValor("Mail réplica del Oficial");
        d.setCodigo(2);
        session.persist(d);

        /**
         * Régimen Laboral Contractual
         */
        d = new DatoAuxiliar();
        d.setNombreTabla("RegimenLaboralContractual");
        d.setValor("D.Leg 276");
        d.setCodigo(1);
        session.persist(d);

        d = new DatoAuxiliar();
        d.setNombreTabla("RegimenLaboralContractual");
        d.setValor("D.Leg 728");
        d.setCodigo(2);
        session.persist(d);

        d = new DatoAuxiliar();
        d.setNombreTabla("RegimenLaboralContractual");
        d.setValor("CAS");
        d.setCodigo(3);
        session.persist(d);

        d = new DatoAuxiliar();
        d.setNombreTabla("RegimenLaboralContractual");
        d.setValor("Locación de servicio");
        d.setCodigo(4);
        session.persist(d);

        /**
         * Constancias Documentales
         */
        crearConstanciasDocumentales(session);

        /**
         * Regímenes pensionarios
         */
        d = new DatoAuxiliar();
        d.setNombreTabla("RegPensionarios");
        d.setValor("ONP");
        d.setCodigo(1);
        session.persist(d);

        d = new DatoAuxiliar();
        d.setNombreTabla("RegPensionarios");
        d.setValor("AFP Horizonte");
        d.setCodigo(2);
        session.persist(d);

        d = new DatoAuxiliar();
        d.setNombreTabla("RegPensionarios");
        d.setValor("AFP Integra");
        d.setCodigo(3);
        session.persist(d);

        d = new DatoAuxiliar();
        d.setNombreTabla("RegPensionarios");
        d.setValor("AFP Prima");
        d.setCodigo(4);
        session.persist(d);

        d = new DatoAuxiliar();
        d.setNombreTabla("RegPensionarios");
        d.setValor("AFP Profuturo");
        d.setCodigo(5);
        session.persist(d);

        d = new DatoAuxiliar();
        d.setNombreTabla("RegPensionarios");
        d.setValor("AFP Unión Vida");
        d.setCodigo(6);
        session.persist(d);

        /**
         * Tipos de documento
         */
        d = new DatoAuxiliar();
        d.setNombreTabla("TipoDocumento");
        d.setValor("DNI");
        d.setCodigo(1);
        session.persist(d);

        d = new DatoAuxiliar();
        d.setNombreTabla("TipoDocumento");
        d.setValor("Carnet extranjería");
        d.setCodigo(2);
        session.persist(d);

        d = new DatoAuxiliar();
        d.setNombreTabla("TipoDocumento");
        d.setValor("Partida de nacimiento (solo a menores)");
        d.setCodigo(3);
        session.persist(d);

        /**
         * Sexo
         */
        d = new DatoAuxiliar();
        d.setNombreTabla("Sexo");
        d.setValor("Masculino");
        d.setCodigo(1);
        session.persist(d);

        d = new DatoAuxiliar();
        d.setNombreTabla("Sexo");
        d.setValor("Femenino");
        d.setCodigo(2);
        session.persist(d);

        /**
         * Estado Civil
         */
        d = new DatoAuxiliar();
        d.setNombreTabla("EstadoCivil");
        d.setValor("Soltero/a");
        d.setCodigo(1);
        session.persist(d);

        d = new DatoAuxiliar();
        d.setNombreTabla("EstadoCivil");
        d.setValor("Casado/a");
        d.setCodigo(2);
        session.persist(d);

        d = new DatoAuxiliar();
        d.setNombreTabla("EstadoCivil");
        d.setValor("Divorciado/a-separado/a");
        d.setCodigo(3);
        session.persist(d);

        d = new DatoAuxiliar();
        d.setNombreTabla("EstadoCivil");
        d.setValor("Viudo/a");
        d.setCodigo(4);
        session.persist(d);

        /**
         * Grupo sanguíneo
         */
        categorias = new ArrayList();
        categorias.add("0+");
        categorias.add("A+");
        categorias.add("B+");
        categorias.add("AB+");
        categorias.add("0-");
        categorias.add("A-");
        categorias.add("B-");
        categorias.add("AB-");

        /**
         * Ahora agregamos cada uno de los datos de la categoría.
         */
        for (int i = 1; i <= categorias.size(); i++) {
            d = new DatoAuxiliar();
            d.setNombreTabla("GrupoSanguineo");
            d.setValor(categorias.get(i - 1));
            d.setCodigo(i);
            session.persist(d);
        }

        /**
         * Tipo de discapacidad
         */
        categorias = new ArrayList();
        categorias.add("no tiene");
        categorias.add("pérdida o invalidez de miembros superiores");
        categorias.add("pérdida o invalidez de miembros inferiores");
        categorias.add("motriz");
        categorias.add("visual");
        categorias.add("auditiva o del lenguaje");
        categorias.add("intelectual o mental");
        categorias.add("múltiple");

        /**
         * Ahora agregamos cada uno de los datos de la categoría.
         */
        for (int i = 1; i <= categorias.size(); i++) {
            d = new DatoAuxiliar();
            d.setNombreTabla("TipoDiscapacidad");
            d.setValor(categorias.get(i - 1));
            d.setCodigo(i);
            session.persist(d);
        }


        /**
         * Nivel de Instrucción
         */
        categorias = new ArrayList();
        categorias.add("Ninguno");
        categorias.add("Primario incompleto");
        categorias.add("Primario");
        categorias.add("Secundario incompleto");
        categorias.add("Secundario");
        categorias.add("Técnico incompleto");
        categorias.add("Técnico completo");
        categorias.add("Terciario incompleto");
        categorias.add("Terciario");
        categorias.add("Universitario incompleto");
        categorias.add("Universitario");

        /**
         * Ahora agregamos cada uno de los datos de la categoría.
         */
        for (int i = 1; i <= categorias.size(); i++) {
            d = new DatoAuxiliar();
            d.setNombreTabla("Nivel Instrucción");
            d.setValor(categorias.get(i - 1));
            d.setCodigo(i);
            session.persist(d);
        }


        /**
         * Grado parentesco
         */
        categorias = new ArrayList();
        categorias.add("Conyugue");
        categorias.add("Conviviente");
        categorias.add("Hijo");
        categorias.add("Progenitor");

        /**
         * Ahora agregamos cada uno de los datos de la categoría.
         */
        for (int i = 1; i <= categorias.size(); i++) {
            d = new DatoAuxiliar();
            d.setNombreTabla("GradoParentesco");
            d.setValor(categorias.get(i - 1));
            d.setCodigo(i);
            session.persist(d);
        }


        /**
         * Nivel título
         */
        categorias = new ArrayList();
        categorias.add("Primario");
        categorias.add("Secundario");
        categorias.add("Terciario");
        categorias.add("Universitario-grado");
        categorias.add("Universitario-posgrado");

        /**
         * Ahora agregamos cada uno de los datos de la categoría.
         */
        for (int i = 1; i <= categorias.size(); i++) {
            d = new DatoAuxiliar();
            d.setNombreTabla("NivelTitulo");
            d.setValor(categorias.get(i - 1));
            d.setCodigo(i);
            session.persist(d);
        }


        /**
         * Título Clasificación
         */
        categorias = new ArrayList();
        categorias.add("Falta 1");
        categorias.add("Falta 2");

        /**
         * Ahora agregamos cada uno de los datos de la categoría.
         */
        for (int i = 1; i <= categorias.size(); i++) {
            d = new DatoAuxiliar();
            d.setNombreTabla("TituloClasificacion");
            d.setValor(categorias.get(i - 1));
            d.setCodigo(i);
            session.persist(d);
        }


        /**
         * Producción intelectual clase
         */
        categorias = new ArrayList();
        categorias.add("Publicación");
        categorias.add("Trabajo de investigación");

        /**
         * Ahora agregamos cada uno de los datos de la categoría.
         */
        for (int i = 1; i <= categorias.size(); i++) {
            d = new DatoAuxiliar();
            d.setNombreTabla("ClaseProduccionIntelectual");
            d.setValor(categorias.get(i - 1));
            d.setCodigo(i);
            session.persist(d);
        }


        /**
         * Tipos de publicación
         */
        categorias = new ArrayList();
        categorias.add("Libro-autor");
        categorias.add("Trabajo de investigación");
        categorias.add("Revista científica nivel internacional");
        categorias.add("Revista científica");
        categorias.add("Periódicos, revistas de divulgación");
        categorias.add("Artículos en la WEB");
        categorias.add("Folletos, manuales,  instructivos");
        categorias.add("Normas, reglamentos u otras disposiciones.");

        /**
         * Ahora agregamos cada uno de los datos de la categoría.
         */
        for (int i = 1; i <= categorias.size(); i++) {
            d = new DatoAuxiliar();
            d.setNombreTabla("TiposPublicacion");
            d.setValor(categorias.get(i - 1));
            d.setCodigo(i);
            session.persist(d);
        }


        /**
         * Tipos de trabajos de investigación
         */
        categorias = new ArrayList();
        categorias.add("Investigación pura analítica o documental");
        categorias.add("Investigación pura de campo o experimental");
        categorias.add("Investigación aplicada analítica o documental");
        categorias.add("Investigación aplicada de campo o experimental");

        /**
         * Ahora agregamos cada uno de los datos de la categoría.
         */
        for (int i = 1; i <= categorias.size(); i++) {
            d = new DatoAuxiliar();
            d.setNombreTabla("TiposTrabajosInvestigacion");
            d.setValor(categorias.get(i - 1));
            d.setCodigo(i);
            session.persist(d);
        }


        /**
         * Méritos-deméritos clase
         */
        categorias = new ArrayList();
        categorias.add("Mérito");
        categorias.add("Demérito");

        /**
         * Ahora agregamos cada uno de los datos de la categoría.
         */
        for (int i = 1; i <= categorias.size(); i++) {
            d = new DatoAuxiliar();
            d.setNombreTabla("MeritosDemeritosClase");
            d.setValor(categorias.get(i - 1));
            d.setCodigo(i);
            session.persist(d);
        }

        /**
         * Tipos méritos
         */
        categorias = new ArrayList();
        categorias.add("Felicitaciones");
        categorias.add("Condecoraciones");
        categorias.add("Designaciones especiales");

        /**
         * Ahora agregamos cada uno de los datos de la categoría.
         */
        for (int i = 1; i <= categorias.size(); i++) {
            d = new DatoAuxiliar();
            d.setNombreTabla("TiposMerito");
            d.setValor(categorias.get(i - 1));
            d.setCodigo(i);
            session.persist(d);
        }


        /**
         * Tipos deméritos
         */
        categorias = new ArrayList();
        categorias.add("Amonestación");
        categorias.add("Suspensión");
        categorias.add("Cese temporal");
        categorias.add("Destitución");


        /**
         * Ahora agregamos cada uno de los datos de la categoría.
         */
        for (int i = 1; i <= categorias.size(); i++) {
            d = new DatoAuxiliar();
            d.setNombreTabla("TiposDemerito");
            d.setValor(categorias.get(i - 1));
            d.setCodigo(i);
            session.persist(d);
        }


        /**
         * Estado de cargo asignado
         */
        categorias = new ArrayList();
        categorias.add("Vigente");
        categorias.add("Histórico");


        /**
         * Ahora agregamos cada uno de los datos de la categoría.
         */
        for (int i = 1; i <= categorias.size(); i++) {
            d = new DatoAuxiliar();
            d.setNombreTabla("EstadoCargo");
            d.setValor(categorias.get(i - 1));
            d.setCodigo(i);
            session.persist(d);
        }

        /**
         * Tipo de  remuneración
         */
        categorias = new ArrayList();
        categorias.add("Remuneración básica");
        categorias.add("Otros incluidos en total permanente");
        categorias.add("Se incluye en total bruto-remunerativa");
        categorias.add("Se incluye en total bruto-no remunerativa");
        categorias.add("Incentivos y otros conceptos excluidos del total bruto");


        /**
         * Ahora agregamos cada uno de los datos de la categoría.
         */
        for (int i = 1; i <= categorias.size(); i++) {
            d = new DatoAuxiliar();
            d.setNombreTabla("TipoRemuneracion");
            d.setValor(categorias.get(i - 1));
            d.setCodigo(i);
            session.persist(d);
        }

        /**
         * Tipo de Evaluaciones
         */
        categorias = new ArrayList();
        categorias.add("Por tendencias conductales");
        categorias.add("Por competencias");


        /**
         * Ahora agregamos cada uno de los datos de la categoría.
         */
        for (int i = 1; i <= categorias.size(); i++) {
            d = new DatoAuxiliar();
            d.setNombreTabla("TipoEvaluaciones");
            d.setValor(categorias.get(i - 1));
            d.setCodigo(i);
            session.persist(d);
        }

        /**
         * Tipo de ausencias
         */
        categorias = new ArrayList();
        categorias.add("Licencia ordinaria");
        categorias.add("Licencia extraordinaria");
        categorias.add("Ausencia justificada");
        categorias.add("Ausencia injustificada");


        /**
         * Ahora agregamos cada uno de los datos de la categoría.
         */
        for (int i = 1; i <= categorias.size(); i++) {
            d = new DatoAuxiliar();
            d.setNombreTabla("TipoAusencias");
            d.setValor(categorias.get(i - 1));
            d.setCodigo(i);
            session.persist(d);
        }


        List<String> paises = new ArrayList<String>();
        paises.add("Perú");
        paises.add("Argentina");
        paises.add("Uruguay");
        paises.add("Chile");
        paises.add("Paraguay");


        /**
         * Ahora agregamos cada uno de los datos de los paises.
         */
        for (int i = 1; i <= paises.size(); i++) {
            d = new DatoAuxiliar();
            d.setNombreTabla("Paises");
            d.setValor(paises.get(i - 1));
            d.setCodigo(i);
            session.persist(d);
        }


        List<String> nacionalidades = new ArrayList<String>();
        nacionalidades.add("Peruana");
        nacionalidades.add("Argentina");
        nacionalidades.add("Uruguaya");
        nacionalidades.add("Chilena");
        nacionalidades.add("Paraguaya");


        /**
         * Ahora agregamos cada uno de los datos de las nacionalidades.
         */
        for (int i = 1; i <= nacionalidades.size(); i++) {
            d = new DatoAuxiliar();
            d.setNombreTabla("Nacionalidades");
            d.setValor(nacionalidades.get(i - 1));
            d.setCodigo(i);
            session.persist(d);
        }





        /*
         * Agregamos los tipos de usuarios
         */

        categorias = new ArrayList();
        categorias.add("Administrador de usuarios general");
        categorias.add("Administrador de usuarios de un organismo");
        categorias.add("Administrador sistema");
        categorias.add("Operador ABM de SERVIR");
        categorias.add("Operador ABM de organismo");
        categorias.add("Operador lectura de organismo");
        categorias.add("Operador lectura general - analistas");


        /**
         * Agregamos los códigos de cargos
         */
        crearCodigosCargos(session);

        /*
         * Agregamos los códigos de formación profesional
         */
        crearFormacionProfesional(session);


//    crearCodigosEstructuralesCargo(session);
    }

    public static final void crearDatosUE(Session session) {
        DatoAuxiliar d;

        d = new DatoAuxiliar();
        d.setNombreTabla("NivelGobierno");
        d.setValor("Nivel Nacional");
        d.setCodigo(1);
        session.persist(d);

        d = new DatoAuxiliar();
        d.setNombreTabla("NivelGobierno");
        d.setValor("Nivel Regional");
        d.setCodigo(1);
        session.persist(d);

        d = new DatoAuxiliar();
        d.setNombreTabla("SectorGobierno");
        d.setValor("AMBIENTAL");
        d.setCodigo(1);
        session.persist(d);

        d = new DatoAuxiliar();
        d.setNombreTabla("Pliego");
        d.setValor("PODER JUDICIAL");
        d.setCodigo(1);
        session.persist(d);
    }

    public static void sanearDatosAuxiliaresDeConceptosRemunerativos(Session session) {
        recrearDatosAuxiliares(session, "TipoRemuneracion",
                "Remuneración básica",
                "Remuneración reunificada",
                "Otras remuneraciones del total permanente",
                "Bonificaciones remunerativas",
                "Bonificaciones no remunerativas",
                "Incentivos y otros conceptos excluidos del total bruto",
                "Remuneración total o no clasificada");

        recrearDatosAuxiliares(session, "TipoRemuneracionStd",
                "Básico",
                "Bonificación",
                "Incentivo",
                "Aguinaldo");

    }

    private static void recrearDatosAuxiliares(Session session, String nombreTabla, String... items) {
        Query query = session.createQuery("DELETE FROM DatoAuxiliar d WHERE d.nombreTabla = :nombreTabla");
        query.setString("nombreTabla", nombreTabla);
        int rowCount = query.executeUpdate();
        System.out.printf("Al recrear los Datos Auxiliares de la tabla \"%s\" se borraron %d registros.\n", nombreTabla, rowCount);

        for (int i = 0; i < items.length; i++) {
            DatoAuxiliar d = new DatoAuxiliar();
            d.setNombreTabla(nombreTabla);
            d.setValor(items[i]);
            d.setCodigo(i + 1);
            session.persist(d);
        }
    }
}
