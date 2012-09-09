/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.components;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Jurguen
 */
public class OrganigramaC {

    @Inject
    private Session session;
    @Parameter(required = true)
    @Property
    private long entidadId;
    @Inject
    private ComponentResources resources;
    @Environmental
    private JavaScriptSupport jsSupport;

    @Log
    void setupRender(MarkupWriter writer) throws IOException, SQLException {
        writer.writeRaw(getOrganigrama());
        resources.renderInformalParameters(writer);
        writer.writeRaw("<div id=\"chart\" class=\"orgChart\"></div>");
        resources.renderInformalParameters(writer);
    }

    @Log
    public String getOrganigrama() throws IOException, SQLException {
        Query query = session.createSQLQuery(
                "SELECT XMLORGANIGRAMA FROM LKENTIDAD WHERE ID=:entidadId").addScalar("XMLORGANIGRAMA")
                .setParameter("entidadId", entidadId);
        Clob result = (Clob) query.uniqueResult();
        return CLOBToString(result);
    }

    private String CLOBToString(Clob cl) throws IOException, SQLException {
        if (cl == null) {
            return "";
        }
        StringBuilder strOut = new StringBuilder();
        String aux;
        BufferedReader br = new BufferedReader(cl.getCharacterStream());
        while ((aux = br.readLine()) != null) {
            strOut.append(aux);
        }
        return strOut.toString();
    }
}