package com.tida.servir.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RSC_DIRECTORIOREPORTE")
public class DirectorioReporte implements Serializable {
    
    @Id
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "DIRECTORIO")
    private String dir;

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
