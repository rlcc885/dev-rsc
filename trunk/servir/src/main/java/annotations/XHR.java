/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package annotations;

/**
 *
 * @author ale
 */
import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface XHR {

}