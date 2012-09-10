package com.tida.servir.components;

import com.tida.servir.entities.Usuario;
import com.tida.servir.entities.UsuarioTrabajador;
import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.services.javascript.StylesheetLink;
import org.apache.tapestry5.services.javascript.StylesheetOptions;

/**
 * Layout component for pages of application servir.
 */
@Import(stylesheet = {"context:layout/layout.css",
    "context:layout/tabs-layout.css",
    "context:layout/tabs.css"},
library = {"context:layout/protofish-min.js",
    "context:layout/navegador.js"})
public class LayoutLogin {

    /**
     * The page title, for the <title> element and the <h1> element.
     */
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;
    @Property
    private String pageName;
    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String sidebarTitle;
    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private Block sidebar;
    @Inject
    private ComponentResources resources;
    @Environmental
    private JavaScriptSupport javaScriptSupport;
    @Inject
    @Path("context:layout/ie6.css")
    private Asset ie6;
    @Inject
    @Path("context:layout/ie7.css")
    private Asset ie7;
    @Inject
    @Path("context:layout/ie8.css")
    private Asset ie8;
    @Inject
    @Path("context:layout/ff.css")
    private Asset ff;
    @Inject
    @Symbol(SymbolConstants.APPLICATION_VERSION)
    @Property
    private String appVersion;
    
    @SessionState
    @Property
    private UsuarioTrabajador usuarioTrabajador;
    
    
    // add an IE-only style sheet if browser is IE
    void afterRender() {
        javaScriptSupport.importStylesheet(new StylesheetLink(ie6, new StylesheetOptions(null, "IE 6")));
        javaScriptSupport.importStylesheet(new StylesheetLink(ie7, new StylesheetOptions(null, "IE 7")));
        javaScriptSupport.importStylesheet(new StylesheetLink(ie8, new StylesheetOptions(null, "gte IE 8")));
        javaScriptSupport.importStylesheet(new StylesheetLink(ff, new StylesheetOptions(null, "ff")));
        
   }

    public String getClassForPageName() {
        return resources.getPageName().equalsIgnoreCase(pageName)
                ? "current_page_item"
                : null;
    }

    public String[] getPageNames() {
        return new String[]{"Index", "Ayuda"};
    }
}