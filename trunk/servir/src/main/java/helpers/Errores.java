/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package helpers;

/**
 *
 * @author ale
 */
public class Errores {
    // Errores generales de fecha
    public static final String FECHA_DESDE_HASTA_COMPARACION= "La fecha desde no puede ser posterior a la fecha hasta";
    public static final String ERROR_FECHA_EGRESO_PREVIA_INGRESO = "La fecha de egreso no puede ser previa a la de ingreso";
    public static final String ERROR_FECHA_HASTA_PREVIA_DESDE = "La fecha hasta no puede ser previa a la fecha desde";
    public static final String ERROR_FECHA_EGRESO_PREVIA_ACTUAL = "La fecha de egreso no puede ser posterior a la fecha actual";
    public static final String ERROR_FECHA_EMISION_PREVIA_ACTUAL = "La fecha de emisión no puede ser posterior a la fecha actual";
    public static final String ERROR_FECHA_DICTADO_PREVIA_ACTUAL = "La fecha de dictado no puede ser posterior a la fecha actual";
    public static final String ERROR_FECHA_PREVIA_ACTUAL = "La fecha no puede ser posterior a la fecha actual";
    public static final String ERROR_FECHA_NACIMIENTO_PREVIA_ACTUAL = "La fecha de nacimiento no puede ser posterior a la fecha actual";
    public static final String ERROR_FECHA_DESDE_PREVIA_ACTUAL = "La fecha desde no puede ser posterior a la fecha actual";
    public static final String ERROR_EDAD_MAYOR = "La edad debe ser mayor o igual a la edad mínima: ";

    // Errores de ABMDato Auxiliar
    public static final String DATOAUXILIAR_CODIGO_REPETIDO = "Código ingresado existente";
    public static final String DATOAUXILIAR_NO_TABLA_RELACION = "El valor de código ingresado a relacionar no existe en la tabla relación";

    // Errores de AMOrgano
    public static final String ERROR_BORRAR_ORGANO = "El órgano tiene unidades orgánicas asociadas.";
    public static final String ERROR_ORGANO_REPETIDO = "El código del órgano debe ser único dentro de la Entidad Unidad Ejecutora";

    // Errores en AMUnidad Organica
    public static final String ERROR_DEPENDE_CARGO = "La unidad orgánica tiene cargos asociados.";
    public static final String ERROR_COD_UND_ORG_UNICA = "Código de unidad orgánica repetido. El código de la unidad orgánica debe ser único dentro del órgano. Unidad orgánica preexistente.";

    // Errores del ABMCargo
    public static final String ERROR_COD_CARGO_UNICO = "El código del cargo debe ser único dentro de la entidad.";
    public static final String ERROR_BORRAR_CARGO = "El cargo tiene cargos asignados asociados.";
    public static final String ERROR_CTD_PUESTOS_MAY_TOTALES = "El cargo no puede tener mas puestos ocupados que totales.";
    public static final String ERROR_CARGO_OCUPADO = "El cargo ya está asignado.";

    // Errores de Usuario
    public static final String ERROR_NOMBRE_USUARIO_UNICO = "Ya existe un usuario con ese nombre y apellido.";
    public static final String ERROR_NO_HAY_TRABAJADOR = "No hay un trabajador que tenga este tipo y numero de documento.";
    public static final String ERROR_LOGIN_USUARIO_UNICO = "Ya existe un usuario con ese login.";
    public static final String ERROR_EMAIL_USUARIO_UNICO = "Ya existe un usuario con ese email.";
    public static final String ERROR_USUARIO_NO_EXISTENTE = "No existe un usuario con la identificación ingresada.";

    // Errores de familiares
    public static final String ERROR_FAMILIAR_DUPLICADO = "Ya existe un familiar con ese documento";

    // Errores de Trabajador nuevo
    public static final String ERROR_TRABAJADOR_EXISTENTE = "El trabajador ya existe.";
    public static final String ERROR_TRABAJADOR_TIENE_CARGO = "El trabajador ya tiene un cargo en la EntidadUE:";
    public static final String ERROR_TRABAJADOR_DNI_EXISTENTE = "Ya existe un trabajador diferente con ese documento.";

        // Errores de Busqueda de trabajador por dni para reportes
    public static final String ERROR_TRABAJADOR_NO_EXISTENTE = "No existe un trabajador con los datos ingresados.";

    // Errores de EntidadUE
    public static final String ERROR_ORGANISMO_REPETIDO = "Ya existe una Entidad Unidad Ejecutora con ese código. Deberá ingresar otro.";
    public static final String ERROR_NO_EXISTE_EUE = "La Entidad /Unidad Ejecutora no existe.";
    public static final String ERROR_NO_CODIGO_EXISTE_EUE = "No existe una Entidad /Unidad Ejecutora que contenga el código ingresado.";
    public static final String NO_SE_PUEDE_INGRESAR_INFORMACION_EN_ESTA_ENTIDAD = "No está habilitada la Entidad/U. Ejecutora para el ingreso de información.";
    public static final String NO_SE_PUEDE_PROCESAR_BATCH_EN_ESTA_ENTIDAD = "La Entidad/U. Ejecutora no está habilitada para proceso batch.";
    public static final String HAY_ERRORES_PROCESAR_ARCHIVO_ENTIDADUE = "Hay errores al procesar el archivo csv de Entidades / Unidades Ejecutoras (ORGAN1).";
    public static final String ERROR_PLIEGO_OBLIGATORIO = "El dato Pliego es obligatorio.";
    public static final String ERROR_SECTOR_OBLIGATORIO = "El dato Sector es obligatorio.";
    public static final String ERROR_NIVEL_OBLIGATORIO = "El dato Nivel de Gobierno es obligatorio.";
    public static final String ERROR_PROVINCIA_OBLIGATORIO = "El dato Provincia es obligatorio para entidades de nivel local.";
    public static final String ERROR_DEPARTAMENTO_OBLIGATORIO = "El dato Departamento es obligatorio para entidades de nivel local.";


    //Errores de conceptos remuneratios
    public static final String ERROR_CONCEPTO_REPETIDO = "Ya existe un concepto con ese código. Deberá ingresar otro.";
    public static final String ERROR_CONCEPTO_DESC_REP = "Ya existe un concepto con esa descripción. Deberá ingresar otra.";
    public static final String ERROR_CONCEPTO_EXCLUYENTES = "Los conceptos Totales son excluyentes, sólo puede haber uno de ellos y no puede estar combinado con otros conceptos.";
    
    //Errores de remuneraciones personales
    public static final String ERROR_REM_CONC_EXCLUYENTES = "No pueden haber conceptos remunerativos repetidos en las remuneraciones personales.";


    //Errores tipo documento/ numero documento
    public static final String ESTE_NUMERO_DE_DOCUMENTO_NO_ES_UN_INT= "El número de documento debe ser numérico:";
    
    public static final String ESTE_NUMERO_DE_DOCUMENTO_TIENE_MAS_QUE_8_DIGITS= "El número de documento deberá exactamente solamente 8 dígitos.";
    
    //errores merito demerito
    public static final String CODIGO_CLASE_MERITO_DEMERITO_NO_EXISTE= "El código de la clase merito/demerito no existe";
    
    //errores produccion intelectual
    public static final String CODIGO_CLASE_PRODUCION_INTELECTUAL_NO_EXISTE= "El código de la clase producción intelecutual no existe";

    //errores MigrarUO
    public static final String ERROR_UNIDAD_DESTINO_NULA= "Debe seleccionar una unidad orgánica destino para fusionar";


    //errores batch
    public static final String NO_SE_PUEDE_PROCESAR_ESTE_ARCHIVO_CON_ESTA_ENTIDAD = "No se puede procesar este archivo con esta entidad: ";
    public static final String HAY_MAS_UNA_ENTIDAD_ARCHIVO = "Hay mas de una entidad en el archivo ORGAN1 ";
    public static final String NO_HAY_ENTIDADUE_EN_EL_ARCHIVO_ORGAN1 = "Debe haber al menos una Entidad / U. Ejecutora en el archivo ORGAN1.";
    public static final String NO_HAY_DATOAUXILIAR_CODIGO = "No hay dato en la tabla auxiliar que se corresponda a este código de dato auxiliar: ";
    public static final String NO_HAY_DATOAUXILIAR_VALOR = "No hay dato en la tabla auxiliar que se corresponda a este valor de dato auxiliar: ";
    public static final String EL_CODIGO_DEBE_SER_NUMERICO = "El código debe ser numérico ";
    public static final String CODIGO_NO_ENCONTRADO = "Código estado no encontrado ";
    public static final String TIPO_DOCUMENTO_NO_EXISTE = "No hay un tipo de documento que corresponda a este código: ";
    public static final String FALTA_CAMPOS_OBLIGATORIOS = "Faltan campos obligatorios en el archivo: ";
    public static final String NO_HAY_UN_LEGAJO_QUE_TENGA_ESTE_ENTIDAD_UEJECUTORA = "No hay legajo que tenga esta Entidad / U. Ejecutora";
    public static final String Y_ESTE_TRABAJADOR = " y este trabajador ";
    public static final String NO_HAY_UN_LEGAJO_QUE_TIENE_ESTE_ORGANISMO_INFORMANTE = "No hay legajo que contenga esta Entidad / U. Ejecutora ";
    public static final String ERROR_CREANDO_XLS_DE = "Error creando el archivo de ";
    public static final String ERROR_ESCRIBIENDO_XLS_DE = "Error escribiendo el archivo de ";
    public static final String ESTE_ENTIDADUE_NO_PERMITE_PROCESO_BATCH = "Esta Entidad / U. Ejecutora no permite hacer el proceso batch: ";
    public static final String ERROR_NO_HAY_TRABAJADOR_TIPO_NUMERO = "No existe trabajador con este tipo y número de documento: ";
    public static final String TAMANO_CAMPOS_ARCHIVO_DIFFERENTE = "La cantidad de campos del archivo es diferente de lo que debería ser. Archivo: ";
    
    public static final String CODIGO_ENTIDAD_NO_EXISTE_01= "Código de entidad / unidad ejecutora no existente. Código entidad: ";
    public static final String CODIGO_ENTIDAD_NO_EXISTE_02= ". Archivo: ";
    public static final String CODIGO_UNIDAD_ORGANICA_NO_EXISTE_01= "Código de unidad orgánica no existente. Codigo unidad orgánica: ";
    public static final String CODIGO_UNIDAD_ORGANICA_NO_EXISTE_02= ". Archivo: ";
    public static final String CODIGO_TRABAJADOR_NO_EXISTE_01= "No exite trabajador con este tipo y número de documento. Tipo de documento: ";
    public static final String CODIGO_TRABAJADOR_NO_EXISTE_02= ". Número de documento: ";
    public static final String CODIGO_TRABAJADOR_NO_EXISTE_03= ". Archivo: ";
    public static final String CODIGO_CARGO_NO_EXISTE_01= "No exite cargo con este código de cargo y este código de entidad. Código de cargo: ";
    public static final String CODIGO_CARGO_NO_EXISTE_02= ". Código entidad: ";
    public static final String CODIGO_CARGO_NO_EXISTE_03= ". Archivo: ";
    public static final String CARGO_NO_EXISTE_01= "Código de cargo inexistente. Código cargo: ";
    public static final String CARGO_NO_EXISTE_02= ". Archivo: ";
    public static final String LEGAJO_NO_EXISTE_01= "Código de legajo inexistente. Código de legajo: ";
    public static final String LEGAJO_NO_EXISTE_02= ". Archivo: ";
    public static final String LEGAJO_MULTIPLE_MISMO_TRABAJADOR_ENTIDAD= "Ya existe un legajo para el trabajador en la entidad: ";
    public static final String CODIGO_LEGAJO_NO_EXISTE_01 = "No existe legajo con este trabajador y esta entidad UE. Tipo de documento de trabajador: ";
    public static final String CODIGO_LEGAJO_NO_EXISTE_02 = ". Nro documento: ";
    public static final String CODIGO_LEGAJO_NO_EXISTE_03 = ". Código entidadUE: ";
    public static final String CODIGO_LEGAJO_NO_EXISTE_04 = ". Archivo: ";
    public static final String CODIGO_CARGO_ASIGNADO_NO_EXISTE_01 = "No existe cargo asignado que tenga este cargo y este legajo. Código de cargo: ";
    public static final String CODIGO_CARGO_ASIGNADO_NO_EXISTE_02 = ". Código de legajo: ";
    public static final String CODIGO_CARGO_ASIGNADO_NO_EXISTE_03 = ". Archivo: ";
    public static final String CODIGO_CONCEPTO_REMUNERATIVO_NO_EXISTE_01 = "No existe concepto remunerativo con este código de concepto remunerativo y este código de entidad Ue. Código concepto remunerativo:  ";
    public static final String CODIGO_CONCEPTO_REMUNERATIVO_NO_EXISTE_02 = ". Código de entidad UE: ";
    public static final String CODIGO_CONCEPTO_REMUNERATIVO_NO_EXISTE_03 = ". Archivo: ";
    public static final String ESTADO_ALTO_FECHA_FIN = "Error en el cargo asignado. El estado recibido es ALTO mientras este cargo asignado tiene una fecha de fin.";
    public static final String NIVEL_UNIDAD_ORGANICA_ANTECESORA_MAYOR_NIVEL_UNIDAD_ORGANICA = "El nivel de la unidad orgánica antecesora es mayor que el nivel de la unidad orgánica.";
    public static final String NO_HAY_UNIDAD_ORGANICA_ANTECESORA = "Nivel de la unidad orgánica mayor a 1 pero no se especifica la unidad orgánica antecesora.";
    public static final String ERROR_ARCHIVO_CONCEPTO_REMUNERATIVO_PERIODICIDAD = "Error en el archivo Concepto Remunerativo. Las periodicidades debe estar entre 0 y 3.";
    public static final String CODIGO_PERIODICIDAD_NO_ES_UN_INT= "El codigo de periodicidad debe ser numérico:";
    public static final String ERROR_PARSEANDO_TIPO_DATE01= "Error parseando tipo date (se necesita este tipo de formato dd/MM/aaaa). Date: ";
    public static final String ERROR_FECHA_INVALIDA= "Fecha inválida. Fecha: ";
    public static final String ERROR_PARSEANDO_TIPO_DATE02= ". Archivo: ";
    public static final String ERROR_CASCADA_UBIGEO= "Ubigeo en cascada inválido [DEPTO - PROV - DIST] : ";
    public static final String ERROR_UBIGEO_SECTOR_PLIEGO= "No corresponde el código de ubigeo con el de sector y pliego  ";
    public static final String ERROR_CASCADA_TIPO_CONSTANCIA= "Tipo de constancia documental no se coincide con la tabla de categoría de constancia documental : ";
    
    //errores sancion
    public static final String PROBLEMA_ACCEDER_BASE_DATO = "Problema para tomar los datos Sanciones.";
}
