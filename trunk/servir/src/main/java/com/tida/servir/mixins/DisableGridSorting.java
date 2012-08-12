/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.mixins;

import java.util.List;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.MixinAfter;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.beaneditor.PropertyModel;
import org.apache.tapestry5.corelib.components.Grid;

/**
 *
 * @author Jurguen Zambrano
 */
@MixinAfter
public class DisableGridSorting {
    @InjectContainer 
        private Grid grid; 

        void setupRender() { 
                if (grid.getDataSource().getAvailableRows() == 0) 
                        return; 

                BeanModel<?> model = grid.getDataModel(); 
                List<String> propertyNames = model.getPropertyNames(); 
                for (String propName : propertyNames) { 
                        PropertyModel propModel = model.get(propName); 
                        propModel.sortable(false); 
                } 
        } 
}
