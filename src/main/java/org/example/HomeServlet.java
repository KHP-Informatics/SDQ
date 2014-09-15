package org.example;

import jade.wrapper.gateway.JadeGateway;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sdq.action.SendMessageAction;
import sdq.agent.dbmanagement.DatabaseInformation;
import sdq.communication.BlackBoardBean;
import sdq.data.progress.Schedule;
import jade.util.leap.Properties;
import jade.core.Profile;


public class HomeServlet extends HttpServlet implements DatabaseInformation
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

		/*	JadeGateway.init("sdq.agent.GateWayAgent", pp);
			
		  System.out.println(" initialisied gateway");
			System.out.println(" home servlet init  , gateway "+
					JadeGateway.isGatewayActive());*/
			connection = this.setupDatabaseConnection();

	}
	

	  public void doGet(HttpServletRequest req, HttpServletResponse res)
              throws ServletException, IOException {

			int pin = Integer.parseInt(req.getParameter("pin"));

		  	int count = 0;
		  	boolean due = false; 
		  	Date dueDate = new Date(); 
		  	
			String databaseURL = "jdbc:sqlserver://BABYLON-PC\\SQLBABYLON;databaseName=SdqUsers"; 
			String userName = "sa";
			String password = "meegoreng12";
			Connection scheduleConnection;

			System.setProperty("java.net.preferIPv6Addresses","true");
			try{
						Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
						scheduleConnection = DriverManager.getConnection(databaseURL,userName,password);

				String searchQuery ="SELECT * from dbo.tblSchedule where pin = "+pin;

			//************run query *//
	
				Statement searchStatement = null;
				searchStatement = scheduleConnection.createStatement();
				ResultSet searchSet =  searchStatement.executeQuery(searchQuery);
		
				if(searchSet .next()){
					  due = searchSet.getBoolean("due");
					  dueDate = searchSet.getDate("dueDate");
					  count = searchSet.getInt("counter");
				}
				
				scheduleConnection.close();
			}
		  catch(Exception e){e.printStackTrace();}

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
	    	 req.getSession().setAttribute("count", count);
	    	 req.getSession().setAttribute("due", due);
	    	 req.getSession().setAttribute("dueDate", dueDate);

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
			String next; 
				if(count == 0)
					next = "second";
					else if(count == 1)
							next = "third";
						else if (count ==2)
								next = "fourth";
								else if (count ==3)
										next = "fifth";
										else 
												next = "sixth";
	    	if(due){

	    		
		  		text += " <br> Your "+next+" SDQ is due ";//+dueDate.toString();
		  		text +=  ". It would be great if you could fill it up using the Actions page ";
		  		text += " so that you can obtain the latest analysis based on what the experts say.";

		    }

		    else{
		    	
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				String formattedDate = df.format(dueDate);
				
		  		text += " <br> You don't have any due or pending SDQs";// is due on "+dueDate.toString();
		  		text += " Your "+next+" SDQ will be due on "+formattedDate;
		  		text += ". You can browse your previous questionnaires and detailed reports by visiting ";
		  		text += " the appropriate links on the left hand side.";
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