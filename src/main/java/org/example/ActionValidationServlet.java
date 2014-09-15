package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sdq.agent.dbmanagement.DatabaseInformation;


public class ActionValidationServlet extends HttpServlet implements DatabaseInformation{
	private static final long serialVersionUID = 1L;

	
	public Connection setupDatabaseConnection()
	{
	
			 Connection con;  
			System.setProperty("java.net.preferIPv6Addresses","true");
				try{
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					con = DriverManager.getConnection(databaseURL,userName,password);
					System.out.println(" connection established");
					return con;
				}catch(Exception e)
				{return null; }
			
	}		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
     String parameters [] = new String[25];
     boolean valid [] = new boolean[25];

     for(int i = 0; i< 25; i++){
	   	parameters[i]  = request.getParameter("s"+(i+1));
	   	request.setAttribute("s"+(i+1), parameters[i]);
	    request.getSession().setAttribute("s"+(i+1), parameters[i]); // add to session

	   	valid[i]  = (parameters[i] != null);
		//  System.out.println(" in action validation: s "+(i+1)+" : "+parameters[i]+" "+request.getParameter("s"+(i+1)));

	 }

	 if(getCourse(valid) ==0){
			 
		 int pin =  (Integer)request.getSession().getAttribute("pin"); // add to session
		 boolean due =  (Boolean )request.getSession().getAttribute("due"); // add to session
		 String fname =  (String)request.getSession().getAttribute("fname"); // add to session
		 String lname =  (String)request.getSession().getAttribute("lname"); // add to session
		  int gender_id =  (Integer)request.getSession().getAttribute("gender_id"); // add to session

		    response.setContentType("text/html");
		    PrintWriter out = response.getWriter();	
			    	out.println(HTML.getHead(pin,due,fname,lname));
			     	out.println(HTML.getMenu(pin,due,fname,lname,gender_id));
			    	out.println(HTML.getMain(pin,due,fname,lname,gender_id,makePendingBody(request,parameters,valid)));
			    	out.println(HTML.getFooter());
			    	out.flush();
			    	out.close();	 	
	 }
	 else{ //go to ActionSecondPageServlet
		 
	    	 RequestDispatcher dispatcher =
	    	 getServletContext().getRequestDispatcher("/actionimpact1");
	    	 dispatcher.forward(request,response);
	 }
   	
	}
	
	private int getCourse(boolean valid[]){
		int course = 1; 
		for(int i = 0; i< valid.length; i++){
			if(valid[i] == false)
				course = 0; 
		}
 
		return course; 
	}
    
    private String makePendingBody(HttpServletRequest request, String parameters[], boolean valid[]){
    	String form = "";
    	form = 	"<table width=100% border=0>";
    	form += "<tr style=\"font-size:13px;font-weight:bold\"><td>";
    	form += "Please make sure that every item has been answered. Thank you !";
    	form += "</td></tr>";
    	form += "<tr><td>";
    	form += "&nbsp;</td></tr>";
    	form += "<tr><td>";		
		form += "<FORM ACTION=actionvalidation METHOD=POST>";
		form += "<table width = 90%, align=\"center\" border=\"0\">";
		form += "<tr>";
		form += "<td colspan=\"4\">";
		form += "</td>";
		form += "</tr>";
		
	  int gender_id =  (Integer)request.getSession().getAttribute("gender_id"); // add to session

		String text[] = HTML.Questions(gender_id);
		for(int i = 0; i< 25; i++){
			//checked="checked"
				if(valid[i]){
				form += "<tr style=\"font-size:12px;background-color:#ffffff\">";
				form += "<td style=\"border: 1px solid #CCC;\" }>";
				form += text[i];
				form += "</td>";
				form += "<td style=\"border: 1px solid #CCC;\" }>";
				if(parameters[i].equals("0")){
					
				form += "<input type=\"radio\" checked=\"checked\" name=\"s"+(i+1)+"\"  value=\"0\" data-toggle=\"radio\">";
				}
				else{
				form += "<input type=\"radio\" name=\"s"+(i+1)+"\"  value=\"0\" data-toggle=\"radio\">";

				}
					
				form += "</td>";
				
				form += "<td style=\"border: 1px solid #CCC;\" }>";
				if(parameters[i].equals("1")){
				form += "<input type=\"radio\" checked=\"checked\" name=\"s"+(i+1)+"\"  value=\"1\" data-toggle=\"radio\">";
				}
				else{
				form += "<input type=\"radio\" name=\"s"+(i+1)+"\"  value=\"1\" data-toggle=\"radio\">";

				}
				
				form += "<td style=\"border: 1px solid #CCC;\" }>";
				if(parameters[i].equals("2")){
				form += "<input type=\"radio\" checked=\"checked\" name=\"s"+(i+1)+"\"  value=\"2\" data-toggle=\"radio\">";
				}
				else{
				form += "<input type=\"radio\" name=\"s"+(i+1)+"\"  value=\"2\" data-toggle=\"radio\">";

				}
					
				form += "</td>";
				form += "</tr>";
			}
			
			else{	
				form += "<tr style=\"font-size:12px;background-color:#FFFFD6\">";
				form += "<td style=\"border: 1px solid #CCC;\" }>";
				form += text[i];
				form += "</td>";
				form += "<td style=\"border: 1px solid #CCC;\" }>";
				form += "<input type=\"radio\" name=\"s"+(i+1)+"\"  value=\"0\" data-toggle=\"radio\">";
				form += "</td>";
				form += "<td style=\"border: 1px solid #CCC;\" }>";
				form += "<input type=\"radio\" name=\"s"+(i+1)+"\"  value=\"1\" data-toggle=\"radio\">";
				form += "<td style=\"border: 1px solid #CCC;\" }>";
				form += "<input type=\"radio\" name=\"s"+(i+1)+"\"  value=\"2\" data-toggle=\"radio\">";
				form += "</td>";
				form += "</tr>";
			}
			
		}

		form += "<tr>";
		form += "<td colspan=\"4\"> &nbsp;";
		form += "</td>";
		form += "</tr>";
		form += "<tr>";
		form += "<td colspan=\"4\" align=\"right\">";
		form += "<INPUT TYPE=SUBMIT VALUE=\" Next >> \">";   

		
		form += "</td>";
		form += "</tr>";
		form += "</table>";


		form += "</FORM>";
		form += "</td></tr>";
		form += "</table>";
    	
    	return form; 
    }   

}
