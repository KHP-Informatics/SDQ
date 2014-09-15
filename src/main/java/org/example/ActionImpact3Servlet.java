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


public class ActionImpact3Servlet extends HttpServlet implements DatabaseInformation{

	
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

		 response.setContentType("text/html");
		  PrintWriter out = response.getWriter();
			
		    	out.println(HTML.getHead(pin,due,fname,lname));
		    	out.println(HTML.getMenu(pin,due,fname,lname,gender_id));
		    	out.println(HTML.getMain(pin,due,fname,lname,gender_id, form(request, gender_id)));
		    	out.println(HTML.getFooter());
			out.flush();
			out.close();
    }
    
    private String form(HttpServletRequest request, int gender_id){

    	String form = 	"<table width=100% border=0>";
    	form += "<tr style=\"font-size:13px\"><td>";
    	form += "<td>";		
		form += "<FORM ACTION=actionimpact3Validation METHOD=POST>";
		form += "<table width = 90%, align=\"center\" border=\"0\">";
 
		
		//*************IMPACT 3 
		form += "<tr  style=\"font-size:13px\"><td>";
    	form += "</td>";

    	
    	form += "<td>Not at all";	
    	form += "</td>";
    	
    	form += "<td>A little";	
    	form += "</td>";
    	
    	
    	form += "<td>A medium amount";	
    	form += "</td>";

    	
    	form += "<td>A great deal";	
    	form += "</td>";

		form += "</tr>";		
		
		
    	form += "<tr><td class=\"bordered\">";
    	if(gender_id ==2)
		form += "Do the difficulties upset or distress her?";
    	else
    		form += "Do the difficulties upset or distress him?";

    	form += "</td>";
    	form += "<td class=\"bordered\">";		
			form += "<input type=\"radio\" name=\"i3\"  value=\"0\" data-toggle=\"radio\">";
		form += "</td>";
		
    	form += "<td class=\"bordered\">";		
			form += "<input type=\"radio\" name=\"i3\"  value=\"1\" data-toggle=\"radio\">";
		form += "</td>";		

    	form += "<td class=\"bordered\">";		
			form += "<input type=\"radio\" name=\"i3\"  value=\"2\" data-toggle=\"radio\">";
		form += "</td>";
		
    	form += "<td class=\"bordered\">";		
			form += "<input type=\"radio\" name=\"i3\"  value=\"3\" data-toggle=\"radio\">";
		form += "</td>";
		form += "</tr>";
		form += "<tr class=\"bordered\"><td colspan=5>&nbsp; </td></tr>";
		form += "<tr><td colspan=5>&nbsp; </td></tr>";

		//*************IMPACT 4, 5, 6, 7
		form += "<tr style=\"font-size:13px\"><td>";
    	
    	if(gender_id ==2)
    		form += "Do the difficulties interfere with her everyday life in the following areas?";
        	else
        		form += "Do the difficulties interfere with his everyday life in the following areas?";
    	form += "</td>";

    	
    	form += "<td>Not at all";	
    	form += "</td>";
    	
    	form += "<td>A little";	
    	form += "</td>";
    	
    	
    	form += "<td>A medium amount";	
    	form += "</td>";

    	
    	form += "<td>A great deal";	
    	form += "</td>";
    	
    	
		form += "</tr>";		
		
		
    	form += "<tr><td class=\"bordered\">";

    	form += "Home life";

    	form += "</td>";
    	form += "<td class=\"bordered\">";		
			form += "<input type=\"radio\" name=\"i4\"  value=\"0\" data-toggle=\"radio\">";
		form += "</td>";
		
    	form += "<td class=\"bordered\">";		
			form += "<input type=\"radio\" name=\"i4\"  value=\"1\" data-toggle=\"radio\">";
		form += "</td>";		
		

    	form += "<td class=\"bordered\">";		
			form += "<input type=\"radio\" name=\"i4\"  value=\"2\" data-toggle=\"radio\">";
		form += "</td>";
	
    	form += "<td class=\"bordered\">";		
			form += "<input type=\"radio\" name=\"i4\"  value=\"3\" data-toggle=\"radio\">";
		form += "</td>";
		form += "</tr>";		
	
		
		
    	form += "<tr><td class=\"bordered\">";

    	form += "Friendship";

    	form += "</td>";
    	form += "<td class=\"bordered\">";		
			form += "<input type=\"radio\" name=\"i5\"  value=\"0\" data-toggle=\"radio\">";
		form += "</td>";

    	form += "<td class=\"bordered\">";		
			form += "<input type=\"radio\" name=\"i5\"  value=\"1\" data-toggle=\"radio\">";
		form += "</td>";		

    	form += "<td class=\"bordered\">";		
			form += "<input type=\"radio\" name=\"i5\"  value=\"2\" data-toggle=\"radio\">";
		form += "</td>";

    	form += "<td class=\"bordered\">";		
			form += "<input type=\"radio\" name=\"i5\"  value=\"3\" data-toggle=\"radio\">";
		form += "</td>";
		form += "</tr>";	
		
		
    	form += "<tr><td class=\"bordered\">";

    	form += "Classroom learning";

    	form += "</td>";
    	form += "<td class=\"bordered\">";		
			form += "<input type=\"radio\" name=\"i6\"  value=\"0\" data-toggle=\"radio\">";
		form += "</td>";
    	form += "<td class=\"bordered\">";		
			form += "<input type=\"radio\" name=\"i6\"  value=\"1\" data-toggle=\"radio\">";
		form += "</td>";		

    	form += "<td class=\"bordered\">";		
			form += "<input type=\"radio\" name=\"i6\"  value=\"2\" data-toggle=\"radio\">";
		form += "</td>";

    	form += "<td class=\"bordered\">";		
			form += "<input type=\"radio\" name=\"i6\"  value=\"3\" data-toggle=\"radio\">";
		form += "</td>";
				
    	form += "<tr><td class=\"bordered\">";
    	form += "Leisure activities";

    	form += "</td>";
    	form += "<td class=\"bordered\">";		
			form += "<input type=\"radio\" name=\"i7\"  value=\"0\" data-toggle=\"radio\">";
		form += "</td>";

    	form += "<td class=\"bordered\">";		
			form += "<input type=\"radio\" name=\"i7\"  value=\"1\" data-toggle=\"radio\">";
		form += "</td>";		

    	form += "<td class=\"bordered\">";		
			form += "<input type=\"radio\" name=\"i7\"  value=\"2\" data-toggle=\"radio\">";
		form += "</td>";
		
    	form += "<td class=\"bordered\">";		
			form += "<input type=\"radio\" name=\"i7\"  value=\"3\" data-toggle=\"radio\">";
		form += "</td>";
		form += "</tr>";
		
		form += "<tr><td colspan=5>&nbsp; </td></tr>";
		form += "<tr><td colspan=5>&nbsp; </td></tr>";

	
		//*********end impact 3-7 form
		
		//*********impact 8 form
		
	
		
		//*************IMPACT 3 
		form += "<tr style=\"font-size:13px\"><td>";
    	form += "</td>";

    	
    	form += "<td>Not at all";	
    	form += "</td>";
    	
    	form += "<td>A little";	
    	form += "</td>";
    	
    	
    	form += "<td>A medium amount";	
    	form += "</td>";

    	
    	form += "<td>A great deal";	
    	form += "</td>";
    	
    	
		form += "</tr>";		
		
		
    	form += "<tr><td class=\"bordered\">";
		form += "Do the difficulties put a burden on you or the family as a whole?";


    	form += "</td>";
    	form += "<td class=\"bordered\">";		
			form += "<input type=\"radio\" name=\"i8\"  value=\"0\" data-toggle=\"radio\">";
		form += "</td>";

    	form += "<td class=\"bordered\">";		
			form += "<input type=\"radio\" name=\"i8\"  value=\"1\" data-toggle=\"radio\">";
		form += "</td>";		

    	form += "<td class=\"bordered\">";		
			form += "<input type=\"radio\" name=\"i8\"  value=\"2\" data-toggle=\"radio\">";
		form += "</td>";

    	form += "<td class=\"bordered\">";		
			form += "<input type=\"radio\" name=\"i8\"  value=\"3\" data-toggle=\"radio\">";
		form += "</td>";
		form += "</tr>";	
		
		form += "<tr><td colspan=5>&nbsp; </td></tr>";


		//***********submit + hidden parameters
		form += "<tr>";
		form += "<td colspan=\"5\"> &nbsp;";
		form += "</td>";
		form += "</tr>";
		form += "<tr>";
		form += "<td colspan=\"5\" align=\"right\">";
		form += "<INPUT TYPE=SUBMIT VALUE=\" Submit >> \">";   

		
		form += "</td>";
		form += "</tr>";
		form += "</table>";

		form += "</FORM>";
		form += "</td></tr>";
		form += "</table>";
    	
    	return form; 
    }   

}
