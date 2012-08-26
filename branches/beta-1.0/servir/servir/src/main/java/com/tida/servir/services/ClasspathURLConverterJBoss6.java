/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.services;

/**
 *
 * @author ale
 */

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;

import org.apache.tapestry5.ioc.services.ClasspathURLConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClasspathURLConverterJBoss6 implements ClasspathURLConverter {
        private static Logger _logger = LoggerFactory.getLogger(ClasspathURLConverterJBoss6.class);

        public URL convert(URL url) {
                if (url != null && url.getProtocol().startsWith("vfs")) {
                        // supports virtual filesystem used by JBoss 6.x
                        try {
                                URLConnection connection = url.openConnection();
                                Object virtualFile = connection.getContent();
                                File physicalFile = (File) invoke(virtualFile, "getPhysicalFile");
                                URL physicalFileURL = physicalFile.toURI().toURL();
                                return physicalFileURL;
                        }
                        catch (Exception e) {
                                _logger.error(e.getCause().toString());
                        }
                }
                return url;
        }

        private Object invoke(Object target, String methodName) throws NoSuchMethodException, InvocationTargetException,
                        IllegalAccessException {
                Class<?> type = target.getClass();
                Method method;
                try {
                        method = type.getMethod(methodName);
                }
                catch (NoSuchMethodException e) {
                        method = type.getDeclaredMethod(methodName);
                        method.setAccessible(true);
                }
                return method.invoke(target);
        }


}
