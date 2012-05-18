/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.components;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Field;
import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ComponentDefaultProvider;
import org.apache.tapestry5.util.AbstractSelectModel;
import org.apache.tapestry5.util.EnumSelectModel;


/**
 *
 * @author ale
 */
public class DateInput implements Field{

    public String getControlName() {
        return "DateInput";
    }

    public boolean isRequired() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public enum Month
{
ENERO (0),
FEBRERO (1),
MARZO (2),
ABRIL (3),
MAYO (4),
JUNIO (5),
JULIO (6),
AGOSTO (7),
SEPTIEMBRE (8),
OCTUBRE (9),
NOVIEMBRE (10),
DICIEMBRE (11);
private Month(int order)
{
this.order = order;
}
private int order;
public int getOrder()
{
return order;
}
}

    public class IntegerEncoder implements ValueEncoder
{
public String toClient(Object i)
{
return i.toString();
}

public Object toValue(String s)
{
return new Integer(s);
}
}
public class IntegerOptionModel implements OptionModel
{
private Number number;
public IntegerOptionModel(Number num)
{
number = num;
}
public Map<String, String> getAttributes()
{
return null;
}
public String getLabel()
{
return "" + number;
}
public Object getValue()
{
return number;
}
public boolean isDisabled()
{
return false;
}
}


public class IntegerSelectModel extends AbstractSelectModel
{
private List<OptionModel> options =
new ArrayList<OptionModel>();
public IntegerSelectModel(int numFrom, int numTo)
{
int increment = numTo > numFrom ? 1 : -1;
for (int i = numFrom; i <= numTo; i += increment)
{
options.add(new IntegerOptionModel(i));
}
}
public List<OptionGroupModel> getOptionGroups()
{
return null;
}
public List<OptionModel> getOptions()
{
return options;
}
}


    public String getElementName()
    {
        return null;
    }

    @Parameter(required = true)
    private Date date;

    @Inject
    private Messages messages;
    private Calendar c = Calendar.getInstance();
    @SetupRender
    void setupCalendar()
    {
        /*
        if (date==null) date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        */

        c.setTime(date == null ? new Date() : date);
        c.setLenient(false);
        c.setTimeZone(TimeZone.getDefault());
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.AM_PM, Calendar.AM);
//        ----------TODO TESTEAR
    }
    public SelectModel getDayModel()
    {
    return new IntegerSelectModel(1, 31);
    }
    public SelectModel getYearModel()
    {
    return new IntegerSelectModel(1910, 2030);
    }
    public SelectModel getMonthModel()
    {
    return new EnumSelectModel(Month.class, messages);
    }
    public ValueEncoder getEncoder()
    {
    return new IntegerEncoder();
    }
    public int getDay()
    {
    return c.get(Calendar.DATE);
    }
    public void setDay(int day)
    {
        try {
            c.set(Calendar.DATE, day);
            date = c.getTime();
        } catch (Exception e) {
          tracker.recordError(this, "Fecha Inválida: "+ this.label);
        }
    }
    public Month getMonth()
    {

        return Month.values()[c.get(Calendar.MONTH)];

    }
    public void setMonth(Month month)
    {
        try {
            c.set(Calendar.MONTH, month.getOrder());
            date = c.getTime();

        } catch (Exception e) {
          tracker.recordError(this, "Fecha Inválida: "+ this.label);
        }

    }
    public int getYear()
    {
    return c.get(Calendar.YEAR);
    }
    public void setYear(int year)
    {
        try {
            c.set(Calendar.YEAR, year);
            date = c.getTime();
        } catch (Exception e) {
          tracker.recordError(this, "Fecha Inválida: "+ this.label);
        }

    }


    @Parameter (defaultPrefix = "literal")
    private String label;
    public String getLabel()
    {
        return label;

    }

    @Inject
    private ComponentResources resources;

    @Inject
    private ComponentDefaultProvider defaultProvider;

    String defaultLabel()
    {
        return defaultProvider.defaultLabel(resources);
    }

    public String getClientId()
    {
        return resources.getId();
    }

    @Parameter
    private boolean disabled;
    public boolean isDisabled()
    {
        return disabled;
    }

    @Parameter
    private Date dateFrom;

    @Environmental
    private ValidationTracker tracker;

    

}
