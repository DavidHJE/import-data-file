package com.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.model.Database;

public class SendMail {

	 public static void sendTo(String to) {

	        // Recipient's email ID needs to be mentioned.
	        //String to = "xineh74758@kibwot.com";

	        // Sender's email ID needs to be mentioned
	        String from = "mailtest1200@gmail.com";

	        // Assuming you are sending email from through gmails smtp
	        String host = "smtp.gmail.com";

	        // Get system properties
	        Properties properties = System.getProperties();

	        // Setup mail server
	        properties.put("mail.smtp.host", host);
	        properties.put("mail.smtp.port", "465");
	        properties.put("mail.smtp.ssl.enable", "true");
	        properties.put("mail.smtp.auth", "true");

	        // Get the Session object.// and pass username and password
	        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

	            protected PasswordAuthentication getPasswordAuthentication() {

	                return new PasswordAuthentication("mailtest1200@gmail.com", "z@z3s4hr");

	            }

	        });

	        // Used to debug SMTP issues
	        session.setDebug(true);

	        try {
	            // Create a default MimeMessage object.
	            MimeMessage message = new MimeMessage(session);

	            // Set From: header field of the header.
	            message.setFrom(new InternetAddress(from));

	            // Set To: header field of the header.
	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	            // Set Subject: header field
	            message.setSubject("Recupération du mot de passe");

	            Database db = Database.getInstance();
	    		Connection connexion = db.getConnexion();
	    		
	    		Date dateNow = new Date(System.currentTimeMillis());
	    		String codeString = Integer.toString(getCode());
	    		
	    		
	    		PreparedStatement pst = connexion.prepareStatement("INSERT INTO public.reset_password(email, code, reset_at) VALUES (?, ?, ?);");
	    		pst.setString(1, to);
	    		pst.setString(2, codeString);
	    		pst.setTimestamp(3, new Timestamp(dateNow.getTime()));
	    		pst.execute();
	            
	            // Now set the actual message
	            message.setText("Veuillez utilisé le code suivant: " + codeString + " pour réinialisé votre mot de passe!");

	            System.out.println("sending...");
	            // Send message
	            Transport.send(message);
	            System.out.println("Sent message successfully....");
	        } catch (MessagingException mex) {
	            mex.printStackTrace();
	        } catch (SQLException e) {
				e.printStackTrace();
			}

	    }
	 
	 	public static int getCode() {
	 			double x = Math.random()*((999999-100000)+1)+100000;
	 			return (int)x;
		}

}
