/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package annotations;

/**
 *
 * @author ale
 */
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface XHR {

}