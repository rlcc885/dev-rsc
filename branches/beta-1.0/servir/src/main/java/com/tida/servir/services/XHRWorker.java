/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.services;

import annotations.XHR;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.services.ClassTransformation;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.ComponentMethodAdvice;
import org.apache.tapestry5.services.ComponentMethodInvocation;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.TransformMethod;
import org.apache.tapestry5.services.TransformMethodSignature;

/**
 *
 * @author ale
 * Clase para hacer uso de la annotation XHR y que esto nos permita degradar ajax
 * para ie9
 */

public class XHRWorker implements ComponentClassTransformWorker {

   private Request request;

   public XHRWorker(Request request){
      this.request = request;
   }

   public void transform(ClassTransformation transformation,
         MutableComponentModel model) {
      for(final TransformMethod method:
         transformation.matchMethodsWithAnnotation(XHR.class)){
         TransformMethodSignature signature = method.getSignature();

         if(!"void".equals(signature.getReturnType())){
            method.addAdvice(new ComponentMethodAdvice(){

               public void advise(ComponentMethodInvocation invocation) {
                  invocation.proceed();
                  Object result = invocation.getResult();
                  if(!request.isXHR()){
                     result = defaultForReturnType(result.getClass());
                  }
                  invocation.overrideResult(result);
               }

            });
         }else {
            throw new RuntimeException("XHR can be applied to non-void event handlers only");
         }
      }
   }

   private Object defaultForReturnType(Class<?> returnType){
      if (!returnType.isPrimitive()) return null;
      if (returnType.equals(boolean.class)) return false;
      return 0;
   }
}