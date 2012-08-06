package com.tida.servir.entities;

/**
 *
 * @author Jurguen Zambrano
 */
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;

/*
 * @NamedNativeQueries({ @NamedNativeQuery( name = "callSpUsuarioAcceso",
 * query="select
 * trabajador_id,nrodocumento,id,descmenu,nivel,menu_id,accesoselect,accesoupdate,accesodelete,accesoreport
 * from USUARIOACCESO where nrodocumento=:in_nrodocumento and nivel=:in_nivel",
 * resultClass = UsuarioAcceso.class, ) })
 */
     
 @NamedNativeQueries({
    @NamedNativeQuery(name = "callSpEventoAcceso",
    query = "CALL SP_EVENTOACCESO(?,:in_rol_id,:in_tipoevento_id,:in_perfil_id,:in_entidad_id)",
    resultClass = BusquedaEvento.class,
    hints = {
        @QueryHint(name = "org.hibernate.callable", value = "true")
    })
  })

@Entity
public class BusquedaEvento implements Serializable {

    @Basic(optional = false)
    @Column(name = "ID")
    @Id
    @NonVisual
    private long id;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "ENTIDAD_ID")
    @NonVisual
    private long entidadid;
    @Column(name = "TRABAJADOR_ID")
    @NonVisual
    private long trabajadorid;
    @Column(name = "TIPOEVENTO_ID")
    @NonVisual
    private long tipoeventoid;
    @Column(name = "ESTADO")
    @NonVisual
    private Boolean estadoevento;
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fechaevento;
    @Column(name = "APELLIDOS")
    private String nombres;
    @Column(name = "ENTIDAD")
    private String entidad;
    @Column(name = "PAGINA")
    @NonVisual
    private String pagina;
    
    public BusquedaEvento() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public long getEntidadid() {
        return entidadid;
    }

    public void setEntidadid(long entidadid) {
        this.entidadid = entidadid;
    }
    
    public long getTrabajadorid() {
        return trabajadorid;
    }

    public void setTrabajadorid(long trabajadorid) {
        this.trabajadorid = trabajadorid;
    }

    public long getTipoeventoid() {
        return tipoeventoid;
    }

    public void setTipoeventoid(long tipoeventoid) {
        this.tipoeventoid = tipoeventoid;
    }
    
    public Boolean getEstadoevento() {
        return estadoevento;
    }

    public void setEstadoevento(Boolean estadoevento) {
        this.estadoevento = estadoevento;
    }
    
    public Date getFechaevento() {
        return fechaevento;
    }

    public void setFechaevento(Date fechaevento) {
        this.fechaevento = fechaevento;
    }   
    
    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    
    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }  
    
    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }
}
