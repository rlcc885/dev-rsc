/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import com.tida.servir.entities.ConfiguracionAcceso;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Jurguen Zambrano
 */
public class SMTPConfig {

    /**
     * @param titulo : titulo del mensaje
     * @param mensaje : Cuerpo del Mensaje
     * @param paraEmail : Email receptor del mensaje
     * @return true si el envío es conforme y false si no es así.
     */
    public static synchronized boolean sendMail(String titulo, String mensaje, String paraEmail, ConfiguracionAcceso ca) {
        boolean envio = false;

//        #SMTP configuration
//        mail.transport.protocol=smtp
//        mail.host=smtp.gmail.com
//        mail.smtp.auth=true
//        mail.smtp.port=465
//        mail.smtp.socketFactory.port=465
//        mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory
//        mail.smtp.socketFactory.fallback=false
//        mail.smtp.quitwait=false
//        mail.smtp.mail.sender=tuCorreo@gmail.com
//        mail.user=tuCorreo@gmail.com
//        mail.password=tuClaveGmail
//        mail.email=tuCorreo@gmail.com

      final String accountMail = ca.getSMTP_usuario();
      final String accountPass = ca.getSMTP_clave();
        
        try {

            //Propiedades de la conexion
            Properties propiedades = new Properties();
            //propiedades.setProperty("mail.transport.protocol", props.getString("mail.transport.protocol"));
            propiedades.setProperty("mail.smtp.host", "smtp.gmail.com");
            propiedades.setProperty("mail.smtp.port", "587");
            //propiedades.setProperty("mail.smtp.host", ca.getSMTP_servidor());
            //propiedades.setProperty("mail.smtp.port", ca.getSMTP_puerto());
            propiedades.setProperty("mail.smtp.auth", "true");
            propiedades.setProperty("mail.smtp.starttls.enable", "true");
//            propiedades.put("mail.smtp.socketFactory.class", props.getString("mail.smtp.socketFactory.class"));
//            propiedades.put("mail.smtp.socketFactory.fallback", props.getString("mail.smtp.socketFactory.fallback"));
//            propiedades.put("mail.smtp.mail.sender", props.getString("mail.smtp.mail.sender"));
//            propiedades.setProperty("mail.smtp.quitwait", props.getString("mail.smtp.quitwait"));

            //Preparamos la Sesion autenticando al usuario
            Session session = Session.getInstance(propiedades, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(accountMail, accountPass);
                }
            });

            //Preparamos el Mensaje
            MimeMessage message = new MimeMessage(session);
            message.setSender(new InternetAddress(accountMail));
            message.setSubject(titulo);
            message.setContent(mensaje, "text/html; charset=utf-8");
            message.setFrom(new InternetAddress(accountMail));
            message.setReplyTo(InternetAddress.parse(accountMail));

            if (paraEmail.indexOf(',') > 0) {
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(paraEmail));
            } else {
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(paraEmail));
            }

            //envío del mensaje
            Transport.send(message);
            envio = true;

        } catch (Throwable e) {
            envio = false;
            System.out.println(e.getMessage());
        } finally {
            return envio;
        }
    }
}
