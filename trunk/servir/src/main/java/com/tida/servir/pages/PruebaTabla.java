/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.pages;

import com.tida.servir.entities.Ant_Laborales;
import com.tida.servir.entities.Trabajador;
import java.util.List;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.PrimaryKeyEncoder;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

/**
 *
 * @author ale
 */
public class PruebaTabla {
 
    @PageActivationContext
    @Property
    private Trabajador actual;

    @Component(id = "antLaboralesEdit")
    private Form _form;

    @Property
    private Ant_Laborales ant_Laborales;

 @Inject
  private Session session;

  public PrimaryKeyEncoder<Long, Ant_Laborales> getant_laborales_encoder()
  {
    return new PrimaryKeyEncoder<Long, Ant_Laborales>()
    {
      public Long toKey(Ant_Laborales value)
      {
        return value.getId();
      }

      public void prepareForKeys(List<Long> keys)
      {
      }

      public Ant_Laborales toValue(Long key)
      {
        return (Ant_Laborales) session.get(Ant_Laborales.class, key);
      }

            public Class<Long> getKeyType() {
                return Long.class;
            }
    };
  }

  @CommitAfter
  public Object onSuccess()
  {
    return this;
  }

  @CommitAfter
  Object onAddRow()
  {
    Ant_Laborales ant = new Ant_Laborales();
    ant.setTrabajador(actual);
    actual.getAnt_Laborales().add(ant);
    return ant;
  }

  @CommitAfter
  void onRemoveRow(Ant_Laborales ant_Laborales)
  {
    session.delete(ant_Laborales);
  }

}
