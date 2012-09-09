package com.tida.servir.pages;

import com.tida.servir.entities.Entidad;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;

@Import(stylesheet ={"context:layout/chart/jquery.jOrgChart.css",
    "context:layout/chart/custom.css",
    "context:layout/jquery.datepick.css"},
    library={"context:layout/jquery.tools.min.js",
        "context:layout/jquery.jOrgChart.js",
    "context:layout/organigrama.js"})
public class PageOrganigrama {

    @Property
    @SessionState
    private Entidad entidad;
    @Property
    private String title;

    @Log
    void onActivate() {
        title = "Organigrama - "+entidad.getDenominacion();
    }
   
}