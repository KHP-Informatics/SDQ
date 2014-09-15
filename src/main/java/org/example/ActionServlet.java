package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sdq.agent.dbmanagement.DatabaseInformation;


public class ActionServlet extends HttpServlet implements DatabaseInformation{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pin;  
	boolean due; 
	private String fname, lname; 
	private int gender_id; 
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
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response){
		
		
	}
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	

		  due = Boolean.valueOf(request.getParameter("due"));
		  pin = Integer.parseInt((String) request.getParameter("pin"));
		  fname = request.getParameter("fname");
		  lname= request.getParameter("lname");
		  gender_id = Integer.parseInt(
					  request.getParameter("gender_id"));
		    request.setAttribute("pin",pin); 
		    request.getSession().setAttribute("pin",pin); // add to session
		   // this.getServletConfig().getServletContext().setAttribute("pin",pin); 
		    
		    request.setAttribute("due",due); 
		    request.getSession().setAttribute("due",due); // add to session

		    request.setAttribute("fname",fname);
		    request.getSession().setAttribute("fname",fname); // add to session

		    request.setAttribute("lname",lname);
		    request.getSession().setAttribute("lname",lname); // add to session

		    request.setAttribute("gender_id",gender_id);
		    request.getSession().setAttribute("gender_id",gender_id); // add to session

    	
		  String body; 
		  
		  if(due){
			  body = form();
		  }
		  else
		  {
			  body = "<table width=100% border=0>";
		      body += "<tr style=\"font-size:13px;font-weight:bold\"><td>";
		      body += 		  " You have no pending actions. You've filled out the ";
		      body += 	" most recently required SDQ. Thanks!";
		   	  body += "</td></tr>";
		   	  body += "</table>";
		  }
		  response.setContentType("text/html");
	PrintWriter out = response.getWriter();
			
		    	out.println(HTML.getHead(pin,due,fname,lname));
		    	out.println(HTML.getMenu(pin,due,fname,lname,gender_id));
		    	out.println(HTML.getMain(pin,due,fname,lname,gender_id, body));
		    	out.println(HTML.getFooter());
		    	
			out.flush();
			out.close();
	    	
    
    	
    }
    
    private String form(){
    	String form = "";
    	form = 	"<table width=100% border=0>";
    	form += "<tr style=\"font-size:13px;font-weight:bold\"><td>";
		form += "Please answer every item even if you are not 100% sure that your answer is right.";
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
		String [] questions = HTML.Questions(gender_id);
		for(int i = 0; i< questions.length; i++){
			form += "<tr  style=\"font-size:12px;background-color:#ffffff\">";
			form += "<td style=\"border: 1px solid #CCC;\" }>";
			form += questions[i];
			form += "</td>";
			form += "<td style=\"border: 1px solid #CCC;\" }>";
			form += "<input type=\"radio\" name=\"s"+(i+1)+"\"  value=\"0\" data-toggle=\"radio\">";
			form += "</td>";
			form += "<td style=\"border: 1px solid #CCC;\" }>";
			form += "<input type=\"radio\" name=\"s"+(i+1)+"\"  value=\"1\" data-toggle=\"radio\">";
			form += "</td>";
			form += "<td style=\"border: 1px solid #CCC;\" }>";
			form += "<input type=\"radio\" name=\"s"+(i+1)+"\"  value=\"2\" data-toggle=\"radio\">";
			form += "</td>";
			form += "</tr>";
	
			
		}		
		form += "<tr>";
		form += "<td colspan=\"4\"> &nbsp;";
		form += "</td>";
		form += "</tr>";
		form += "<tr>";
		form += "<td colspan=\"4\" align=\"right\">";
		form += "<INPUT TYPE=SUBMIT VALUE=\" Next >> \">";   
		
		form += "<input type=\"hidden\" name=\"pin\" value=\"";
		form += pin+"\"/><br />";
		form += "<input type=\"hidden\" name=\"due\" value=\"";
		form += due+"\"/><br />";
		
		form += "<input type=\"hidden\" name=\"fname\" value=\"";
		form += fname+"\"/><br />";
		form += "<input type=\"hidden\" name=\"lname\" value=\"";
		form += lname+"\"/><br />";
		form += "<input type=\"hidden\" name=\"gender_id\" value=\"";
		form += gender_id+"\"/><br />";		
		
		form += "</td>";
		form += "</tr>";
		form += "</table>";


		form += "</FORM>";
		form += "</td></tr>";
		form += "</table>";
    	
    	return form; 
    }   

}
