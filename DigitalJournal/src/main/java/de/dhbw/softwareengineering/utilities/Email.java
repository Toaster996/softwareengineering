package de.dhbw.softwareengineering.utilities;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {

    private static Email instance;

    private final String host;
    private final int port;
    private final String simpleName;
    private final String accountName;
    private final String password;

    private Email(){
        FileConfiguration emailConfiguration = new FileConfiguration(new File("." + File.separator + "conf" + File.separator + "email.conf"));

        this.host           = emailConfiguration.getString("host");
        this.port           = emailConfiguration.getInt("port");
        this.simpleName     = emailConfiguration.getString("simpleName");
        this.accountName    = emailConfiguration.getString("accountName");
        this.password       = emailConfiguration.getString("password");
    }


    public static Email getInstance(){
        if(instance == null){
            instance = new Email();
        }

        return instance;
    }


    /**
     * Utility method to send simple HTML email via SSL SMTP
     *
     * @param recipients who should get the message
     * @param subject the subject of the mail
     * @param body the body of the mail (HTML is possible)
     */
    public void sendEmailSSL(String[] recipients, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.host", host); 				    // SMTP Host
        props.put("mail.smtp.socketFactory.port", port); 	// SSL Port
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL Factory Class
        props.put("mail.smtp.auth", "true"); 				// Enabling SMTP Authentication
        props.put("mail.smtp.port", port); 					// SMTP Port

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(accountName, password);
            }
        };

        Session session = Session.getDefaultInstance(props, auth);

        try {
            MimeMessage msg = new MimeMessage(session);

            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(accountName, simpleName));
            msg.setReplyTo(InternetAddress.parse(accountName, false));
            msg.setSubject(subject, "UTF-8");
            msg.setText(body, "UTF-8");
            msg.setSentDate(new Date());

            for(String recipient : recipients){
                msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient, false));
            }

            Transport.send(msg);
            System.out.println("Mail sending successful!");
        } catch (Exception e) {
            System.err.println("Error while sending mail!\n\t [Message]: "+e.getMessage());
        }
    }

}
