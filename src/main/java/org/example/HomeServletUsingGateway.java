package org.example;

import jade.wrapper.gateway.JadeGateway;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sdq.action.SendMessageAction;
import sdq.agent.dbmanagement.DatabaseInformation;
import sdq.communication.BlackBoardBean;
import jade.util.leap.Properties;
import jade.core.Profile;


public class HomeServletUsingGateway extends HttpServlet implements DatabaseInformation
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String pin; 
	private  Connection connection;

	
	   public void init() throws ServletException {
   
			Properties pp = new Properties();
			pp.setProperty(Profile.MAIN_HOST, "localhost");
			pp.setProperty(Profile.MAIN_PORT, "1099");

			JadeGateway.init("sdq.agent.GateWayAgent", pp);
			
		  System.out.println(" initialisied gateway");
			System.out.println(" home servlet init  , gateway "+
					JadeGateway.isGatewayActive());
			connection = this.setupDatabaseConnection();

	}
	
	   
	   //the oringinal doGet with Gateway is in the doPost here. Just grab the code 
	   //ZI
	  public void doGet(HttpServletRequest req, HttpServletResponse res)
              throws ServletException, IOException {
		  		  
			//int pin = Integer.parseInt(req.getParameter("pin"));
		  int pin = 297138; 
			SendMessageAction action = new SendMessageAction(pin);
			BlackBoardBean board = action.perform(this, req, res);
		
			String reply = board.getReply(); 
			
		    StringTokenizer st = new StringTokenizer(reply,";");
		    boolean due = Boolean.valueOf(st.nextToken());
		    int day = Integer.parseInt(st.nextToken());
		    int month = Integer.parseInt(st.nextToken());
		    int year = Integer.parseInt(st.nextToken());
		    int count = Integer.parseInt(st.nextToken()); 

		    Calendar c = new GregorianCalendar();
		    c.set(year + 1900, month, day);
		    Date dueDate = c.getTime();
			res.setContentType("text/html");			
			PrintWriter out = res.getWriter();
			String details = this.processRecord(pin);
			StringTokenizer tokenizer = new StringTokenizer(details,";");
			String fname = tokenizer.nextToken();
			String lname = tokenizer.nextToken();
			int gender_id = Integer.parseInt(tokenizer.nextToken());

	    	req.setAttribute("pin",pin); 
	    	req.setAttribute("fname",fname);
	    	req.setAttribute("lname",lname);
	    	req.setAttribute("gender_id",gender_id);

	    	 req.getSession().setAttribute("pin",pin);
	    	 req.getSession().setAttribute("fname",fname);
	    	 req.getSession().setAttribute("lname",lname);
	    	 req.getSession().setAttribute("gender_id",gender_id);

			//out.print("Reply:"+board.getReply());
		    	out.println(HTML.getHead(pin,due,fname,lname));
		    	out.println(HTML.getMenu(pin,due,fname,lname,gender_id));
		    	out.println(HTML.getMain(pin,due,fname,lname,gender_id,
		    			display(pin, count, dueDate, due, fname, lname, gender_id)));
		    	out.println(HTML.getFooter());
			out.flush();
			out.close();	
	  }
	  
	  
	  private String display(int pin,int count, 
			  					Date dueDate, boolean due,
			  					String fname, String lname, int gender_id)
	  {
		  
		  String text = ""; 
	    	text = 	"<table style=\"width:100%;height:100%;border:1;vertical-align:text-top;\">";
			text += "<tr style=\"font-size:13px;background-color:#ffffff;vertical-align:text-top;\">";
	    	text += "<td>";
	    	
		text += "Hello and welcome to the SDQ tracker for <b>"+fname+" "+lname+"</b>";
	    	if(due){
		  		text += " <br> Your next SDQ is due ";//+dueDate.toString();
		  		text +=  ". It would be great if you could fill it up using the Actions page ";
		  		text += " so that you can obtain the latest analysis based on what the experts say.";

		    }

		    else{
		  		text += " <br> You don't have any due or pending SDQs";// is due on "+dueDate.toString();
		  		text += ". You can browse your previous questionnaires and detailed reports by visiting. ";
		  		text += " the appropriate links on the left hand side";
		    }
			text += "</td>";
			text += "</tr>";
			text += "</table>";
		  return text; 
	  }
	  
	  
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

		private String processRecord(int patientID){
			String fname="", lname="'"; 
			int gender_id = 0; 
			try{
			String patientTableQuery = "SELECT DISTINCT dbo.tblPatient.Patient_ID, Forename, Surname,"+
										"Gender_ID,"+
										" Date_Of_Birth from dbo.tblPatient, dbo.tblSLAMSDQ "+
										"WHERE dbo.tblPatient.Patient_ID = "+patientID;
			Statement statement = connection.createStatement();
			ResultSet patientSet = statement.executeQuery(patientTableQuery);
	

			while(patientSet.next()){
				
				fname =  patientSet.getObject("Forename") != null ? patientSet.getString("Forename") : " ";
				lname =  patientSet.getObject("Surname") != null ? patientSet.getString("Surname") : " ";
				gender_id = patientSet.getObject("Gender_ID") != null ? patientSet.getInt("Gender_ID") : -1;				
				
				
			}

			// System.out.println(" made action "+ tr.getInformant().getClass()+ " "+tr.getSDQ().getSDQID());
			}catch(Exception e){e.printStackTrace();}
			

			return fname+";"+lname+";"+gender_id; 
		}
}