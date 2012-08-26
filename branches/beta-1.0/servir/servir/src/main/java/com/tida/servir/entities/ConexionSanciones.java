/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.*;


/**
 *
 * @author Morgan
 */

@Entity
@Table(name = "RSC_CONEXIONSANCIONES")
public class ConexionSanciones {
    
    private String password;
    private String usuario; 
    private String servidor;
    private String request;
    private String driver;
    private String tablespace;
    private String instancia;
    private String tabla_rns_persona;
    private String tabla_rns_funcionario_persona;
    private String tabla_adm_institucion;
    private String tabla_rns_sancion;
    private Long id;
    
    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTabla_adm_institucion() {
        return tabla_adm_institucion;
    }

    public void setTabla_adm_institucion(String tabla_adm_institucion) {
        this.tabla_adm_institucion = tabla_adm_institucion;
    }

    public String getTabla_rns_funcionario_persona() {
        return tabla_rns_funcionario_persona;
    }

    public void setTabla_rns_funcionario_persona(String tabla_rns_funcionario_persona) {
        this.tabla_rns_funcionario_persona = tabla_rns_funcionario_persona;
    }

    public String getTabla_rns_persona() {
        return tabla_rns_persona;
    }

    public void setTabla_rns_persona(String tabla_rns_persona) {
        this.tabla_rns_persona = tabla_rns_persona;
    }

    public String getTabla_rns_sancion() {
        return tabla_rns_sancion;
    }

    public void setTabla_rns_sancion(String tabla_rns_sancion) {
        this.tabla_rns_sancion = tabla_rns_sancion;
    }

    public String getInstancia() {
        return instancia;
    }

    public void setInstancia(String instancia) {
        this.instancia = instancia;
    }

    public String getTablespace() {
        return tablespace;
    }

    public void setTablespace(String tablespace) {
        this.tablespace = tablespace;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServidor() {
        return servidor;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

  
}
