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


public class ActionImpact2ValidationServlet extends HttpServlet implements DatabaseInformation{

	/**
	 * 
	 */
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
		
	protected void doGet(HttpServletRequest request, HttpServletResponse response){
		
		
	}
	  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	    {
			 int pin =  (Integer)request.getSession().getAttribute("pin"); // add to session
			 boolean due =  (Boolean )request.getSession().getAttribute("due"); // add to session
			 String fname =  (String)request.getSession().getAttribute("fname"); // add to session
			 String lname =  (String)request.getSession().getAttribute("lname"); // add to session
			 int gender_id =  (Integer)request.getSession().getAttribute("gender_id"); // add to session

			 String par = request.getParameter("i2");
			   	request.setAttribute("i2",par);
			    request.getSession().setAttribute("i2",par); // add to session

			   	Boolean valid  = (par != null);
			   	
			  response.setContentType("text/html");
		   	
			   	if(valid){
			    	 RequestDispatcher dispatcher =
			    	    	 getServletContext().getRequestDispatcher("/actionimpact3");
			    	    	 dispatcher.include(request,response);
			   	}
			   	else{	
			   		PrintWriter out = response.getWriter();
					
			    	out.println(HTML.getHead(pin,due,fname,lname));
			    	out.println(HTML.getMenu(pin,due,fname,lname,gender_id));
			    	out.println(HTML.getMain(pin,due,fname,lname,gender_id, form()));
			    	out.println(HTML.getFooter());
				out.flush();
				out.close();
			   	}

	    }
    private String form(){
    	String form = "";
    	form = 	"<table width=100% border=0>";
    	form += "<tr style=\"font-size:13px;font-weight:bold\"><td>";
    	form += "<td>";		
		form += "<FORM ACTION=actionimpact2Validation METHOD=POST>";
		form += "<table width = 90%, align=\"center\" border=\"0\">";
		
	  	form += "<tr style=\"font-size:13px;color:#FF9966;font-weight:bold\"><td>";
			form += "<td colspan=\"2\">";
			
			form += "Please make sure you have selected one of the answers to the question below. ";

			form += "</td>";
			form += "</tr>";
			
	    	form += "<tr style=\"font-size:13px;font-weight:normal\"><td>";
			form += "<td colspan=\"2\">";
			
			form += "How long have these difficulties been present?";
			
			form += "</td>";
			form += "</tr>";
			
			
	
			form += "<tr style=\"font-size:13px;font-weight:normal\"><td>";
	    	form += "<td>";		
				form += "<input type=\"radio\" name=\"i2\"  value=\"0\" data-toggle=\"radio\">";
			form += "</td>";
	    	form += "<td>";		
				form += "Less than one month";
			form += "</td>";
			form += "</tr>";

	    	form += "<tr style=\"font-size:13px;font-weight:normal\"><td>";
	    	form += "<td>";		
				form += "<input type=\"radio\" name=\"i2\"  value=\"1\" data-toggle=\"radio\">";
			form += "</td>";
	    	form += "<td>";		
				form += " 1-5 months";
			form += "</td>";
			form += "</tr>";
			
	    	form += "<tr style=\"font-size:13px;font-weight:normal\"><td>";
	    	form += "<td>";		
				form += "<input type=\"radio\" name=\"i2\"  value=\"2\" data-toggle=\"radio\">";
			form += "</td>";
	    	form += "<td>";		
				form += "6-12 months";
			form += "</td>";
			form += "</tr>";		
			
		
	    	form += "<tr style=\"font-size:13px;font-weight:normal\"><td>";
	    	form += "<td>";		
				form += "<input type=\"radio\" name=\"i2\"  value=\"3\" data-toggle=\"radio\">";
			form += "</td>";
	    	form += "<td>";		
				form += "Over a year";
			form += "</td>";
			form += "</tr>";
		
			form += "<tr>";
			form += "<td colspan=\"2\"> &nbsp;";
			form += "</td>";
			form += "</tr>";
			form += "<tr>";
			form += "<td colspan=\"2\" align=\"right\">";
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
