package sdq.communication;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Emailer {

	
	public static void sendMail(String text, String mailFrom, String mailTo, String subject) throws Exception {  
	    try {  
	        Properties props = System.getProperties();  
	        props.setProperty("mail.smtp.port", "587");  
	        props.setProperty("mail.smtp.socketFactory.port", "587");  
	        props.setProperty("mail.smtp.host", "smtp.gmail.com");  
	        props.setProperty("mail.smtp.starttls.enable", "true");  
	        props.setProperty("mail.smtp.auth", "true");  
	   
	        Authenticator auth = new MyAuthenticator();  
	        Session smtpSession = Session.getInstance(props, auth);
	        
	       // System.out.println(" got email session - "+smtpSession.getProperties().toString());
	        smtpSession.setDebug(false);  
	   
	        MimeMessage message = new MimeMessage(smtpSession);  
	        message.setFrom(new InternetAddress(mailFrom));  
	        message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(mailTo));  
	   
	        final String encoding = "UTF-8";  
	   
	        message.setSubject(subject, encoding);  
	        message.setText(text, encoding);  
	        Transport.send(message);  
	    } catch (Exception e) {  
	     //  e.printStackTrace(); 
	    }  
	}  
	
	
	public static String createReminderTitle(String fname, String lname,
			int informant){
String title ="";
if(informant == 0)
title = "Reminder: A full report on your child "+fname+" "+lname;
else if(informant ==1)
title = "Reminder: A full report on your student "+fname+" "+lname;
else
title = fname+" "+lname+ "Reminde: Your Full Report";

return title; 
}
public static String createReminderMessage(int patientID){
String message = 
"Greetings, \n this is a reminder about the follow-up report waiting for you to pick up.. on the report you filled at SLAM. You can access the report"+
" by signing up to our automated management system at https://http://localhost:8080/"+
" once you've signed up, you can view your report by clicking"+
" the link: "+"+http://localhost:8080/sdq-webapp/index.jsp?pid="+
patientID+ " the link should automatically direct you to your"+
"corresponding profile. If for some reason it does not, please "+
" enter your ID when prompted. Your ID is"+patientID; 


return message; 
}

	public static String createInitialSDQTitle(String fname, String lname,
										int informant){
		String title ="";
		if(informant == 0)
			title = " Welcome to your child "+fname+" "+lname+"'s SDQ Tracker";
		else if(informant ==1)
			title = " Welcome to your student "+fname+" "+lname+"'s SDQ Tracker";
			else
				title = " Welcome to your your SDQ Tracker, "+fname+" "+lname;

		return title; 
	}
	public static String createInitialSDQMessage(int patientID){
		String message = 
		"Greetings, \n this is a follow-up on the report you filled at SLAM. You can access the report"+
		" by signing up to our automated management system at https://http://localhost:8080/"+
				" once you've signed up, you can view your report by clicking"+
		" the link: "+"+http://localhost:8080/index.jsp?pid="+
				patientID+ " the link should automatically direct you to your"+
				"corresponding profile. If for some reason it does not, please "+
				" enter your ID when prompted. Your ID is"+patientID; 

		
		return message; 
	}
	   
	
	public static String createFollowUpSDQTitle(String fname, String lname,
			int informant){
String title ="";
if(informant == 0)
title = " A full report on your child "+fname+" "+lname;
else if(informant ==1)
title = " A full report on your student "+fname+" "+lname;
else
title = fname+" "+lname+ " Your Full Report";

return title; 
}
public static String createFollowUpSDQMessage(int patientID){
String message = 
"Greetings, \n this is a follow-up on the report you filled at SLAM. You can access the report"+
" by signing up to our automated management system at https://http://localhost:8080/"+
" once you've signed up, you can view your report by clicking"+
" the link: "+"+http://localhost:8080/sdq-webapp/index.jsp?pid="+
patientID+ " the link should automatically direct you to your"+
"corresponding profile. If for some reason it does not, please "+
" enter your ID when prompted. Your ID is"+patientID; 


return message; 
}
	static class MyAuthenticator extends Authenticator {  
	    public PasswordAuthentication getPasswordAuthentication() {  
	        String username = "sdq.tracker@gmail.com";  
	        String password = "meegoreng12";  
	   
	        return new PasswordAuthentication(username, password);  
	    }  
	} 
	
}
