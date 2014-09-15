package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sdq.agent.dbmanagement.DatabaseInformation;



public class SDQ1Servlet extends HttpServlet implements DatabaseInformation{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pin; 
	private String fname, lname; 
	private int gender_id; 
	boolean due = false; 
	private  Connection connection;

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
	
	public void runQuery(HttpServletResponse response){

		String sdqTableQuery = "SELECT TOP 1 SLAM_SDQ_ID, Patient_ID,Assessed_By_Staff_ID,"
		+ "Assessment_Date, Team_Ward_ID, Informant_ID, Informant_Relationship,"
		+ "Age_Range_ID, Type_ID, Informant_detail,"
		+ "Item_1_Considerate_ID,"		//0 not true, 1 somewhat true, 2 certainly true
		+ "Item_2_Restless_ID,"
		+ "Item_3_Aches_ID,"
		+ "Item_4_Shares_ID,"
		+ "Item_5_Tempers_ID,"
		+ "Item_6_Solitary_ID,"
		+ "Item_7_Obedient_ID,"
		+ "Item_8_Worries_ID,"
		+ "Item_9_Helpful_ID,"
		+ "Item_10_Fidgety_ID,"
		+ "Item_11_Friend_ID,"
		+ "Item_12_Fights_ID,"
		+ "Item_13_Unhappy_ID,"
		+ "Item_14_Popular_ID,"
		+ "Item_15_Distractible_ID,"
		+ "Item_16_Clingy_ID,"
		+ "Item_17_Kind_ID,"
		+ "Item_18_ID,"   //lies 
		+ "Item_19_Victimized_ID,"
		+ "Item_20_Volunteers_ID,"
		+ "Item_21_Reflective_ID,"
		+ "Item_22_ID,"   //steals 
		+ "Item_23_Better_With_Adults_ID,"
		+ "Item_24_Fears_ID,"
		+ "Item_25_Attention_ID,"
		+ "Impact_1_Problem_ID,"	//0 not at all 1 only a little 2 quite a lot
		+ "Impact_2_Duration_ID," 	//3 a great deal, empty string if not answered.
		+ "Impact_3_Distress_ID,"
		+ "Impact_4_Home_Life_ID,"
		+ "Impact_5_Peer_ID,"
		+ "Impact_6_Learning_ID,"
		+ "Impact_7_Leisure_ID,"
		+ "Impact_8_Burden_ID"
		+ " from dbo.tblSLAMSDQ where Patient_ID = "+pin+" ORDER BY Assessment_Date ASC";
		
		
		CallableStatement statement = null;

		//************run query *//
		try{
		 statement = connection.prepareCall(sdqTableQuery,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

	    ResultSet sdq1Set = statement.executeQuery();
	
	    int count =1; 
	    
			while(sdq1Set.next()){
				if(count ==1){
				processRecord(sdq1Set, response);
				}
				count++;


			}
			sdq1Set.close();
	
		}catch(SQLException e){e.printStackTrace();}
	}
	
	
	
	private void processRecord(ResultSet rs, HttpServletResponse response){
		
		try{
		int patientID = rs.getObject("PATIENT_ID") != null ? rs.getInt("PATIENT_ID") : -1;
		//System.out.println(" FOR PATIENTTTT "+patientID);
		String patientTableQuery = "SELECT DISTINCT dbo.tblPatient.Patient_ID, Forename, Surname,"+
									"Gender_ID,"+
									" Date_Of_Birth from dbo.tblPatient, dbo.tblSLAMSDQ "+
									"WHERE dbo.tblPatient.Patient_ID = "+patientID;
		Statement statement = connection.createStatement();
		ResultSet patientSet = statement.executeQuery(patientTableQuery);
	
		while(patientSet.next()){
			
			display(rs, patientSet, response);
		}

		// System.out.println(" made action "+ tr.getInformant().getClass()+ " "+tr.getSDQ().getSDQID());
		}catch(Exception e){e.printStackTrace();}
		
		
	}
	
	
	public void display(ResultSet rs,
			ResultSet patientSet, HttpServletResponse response){
		try{
			String link = "http://www.youthlog.info/py/yiminfo/receiveSDQ.py?l=euk&c=uk&";

		int infId = rs.getObject("Informant_ID") != null ? rs.getInt("Informant_ID") : -1;
		Date date = rs.getObject("Assessment_Date") != null ? rs.getDate("Assessment_Date") : new Date();
		
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    int year = cal.get(Calendar.YEAR);
	    int month = 1+cal.get(Calendar.MONTH);
	    int day = cal.get(Calendar.DAY_OF_MONTH);
		int gender_id = patientSet.getObject("Gender_ID") != null ? patientSet.getInt("Gender_ID") : 1;

		link += "d="+day+"&m="+month+"&y="+year+"&"; 
		if(infId == 0)
			link += "&i=p";
		else if(infId ==1)
			link += "&i=t";
		else
			link += "&i=s";
		
		link += "&e=0&n=X&g=1&a=13";

		
		link += "&e=0&n=X&g="+gender_id+"&a=13";

		String body = form(link, rs);
		
		  PrintWriter out = response.getWriter();
	    	out.println(HTML.getHead(pin,due,fname,lname));
	    	out.println(HTML.getMenu(pin,due,fname,lname,gender_id));
	    	out.println(HTML.getMain(pin, due, fname,lname,
	    								gender_id, body));
	    	out.println(HTML.getFooter());

		}catch(Exception e){e.printStackTrace();}


	}
	
	  private String form(String link, ResultSet rs){
	    	String form = "";
			try{
			String s[] = new String[25];
			String i[] = new String[8];
			s[0] = rs.getObject("Item_1_Considerate_ID") != null ? ""+rs.getInt("Item_1_Considerate_ID") : "";
			s[1] = rs.getObject("Item_2_Restless_ID") != null ? ""+rs.getInt("Item_2_Restless_ID") : "";
			s[2] = rs.getObject("Item_3_Aches_ID") != null ? ""+rs.getInt("Item_3_Aches_ID") : "";
			s[3] = rs.getObject("Item_4_Shares_ID") != null ? ""+rs.getInt("Item_4_Shares_ID") : "";
			s[4] = rs.getObject("Item_5_Tempers_ID") != null ? ""+rs.getInt("Item_5_Tempers_ID") : "";
			s[5] = rs.getObject("Item_6_Solitary_ID") != null ? ""+rs.getInt("Item_6_Solitary_ID") : "";
			s[6] = rs.getObject("Item_7_Obedient_ID") != null ? ""+rs.getInt("Item_7_Obedient_ID") : "";
			s[7] = rs.getObject("Item_8_Worries_ID") != null ? ""+rs.getInt("Item_8_Worries_ID") : "";
			s[8] = rs.getObject("Item_9_Helpful_ID") != null ? ""+rs.getInt("Item_9_Helpful_ID") : "";
			s[9] = rs.getObject("Item_10_Fidgety_ID") != null ? ""+rs.getInt("Item_10_Fidgety_ID") : "";
			s[10] = rs.getObject("Item_11_Friend_ID") != null ? ""+rs.getInt("Item_11_Friend_ID") : "";
			s[11] = rs.getObject("Item_12_Fights_ID") != null ? ""+rs.getInt("Item_12_Fights_ID") : "";
			s[12] = rs.getObject("Item_13_Unhappy_ID") != null ? ""+rs.getInt("Item_13_Unhappy_ID") : "";
			s[13] = rs.getObject("Item_14_Popular_ID") != null ? ""+rs.getInt("Item_14_Popular_ID") : "";
			s[14] = rs.getObject("Item_15_Distractible_ID") != null ? ""+rs.getInt("Item_15_Distractible_ID") : "";
			s[15] = rs.getObject("Item_16_Clingy_ID") != null ? ""+rs.getInt("Item_16_Clingy_ID") : "";
			s[16] = rs.getObject("Item_17_Kind_ID") != null ? ""+rs.getInt("Item_17_Kind_ID") : "";
			s[17] = rs.getObject("Item_18_ID") != null ? ""+rs.getInt("Item_18_ID") : "";
			s[18] = rs.getObject("Item_19_Victimized_ID") != null ? ""+rs.getInt("Item_19_Victimized_ID") : "";
			s[19] = rs.getObject("Item_20_Volunteers_ID") != null ? ""+rs.getInt("Item_20_Volunteers_ID") : "";
			s[20] = rs.getObject("Item_21_Reflective_ID") != null ? ""+rs.getInt("Item_21_Reflective_ID") : "";
			s[21] = rs.getObject("Item_22_ID") != null ? ""+rs.getInt("Item_22_ID") : "";
			s[22] = rs.getObject("Item_23_Better_With_Adults_ID") != null ? ""+rs.getInt("Item_23_Better_With_Adults_ID") : "";
			s[23] = rs.getObject("Item_24_Fears_ID") != null ? ""+rs.getInt("Item_24_Fears_ID") : "";
			s[24] = rs.getObject("Item_25_Attention_ID") != null ? ""+rs.getInt("Item_25_Attention_ID") : "";	
			i[0] = rs.getObject("Impact_1_Problem_ID") != null ? ""+rs.getInt("Impact_1_Problem_ID") : "";
			i[1]  = rs.getObject("Impact_2_Duration_ID") != null ? ""+rs.getInt("Impact_2_Duration_ID") : "";
			i[2]  = rs.getObject("Impact_3_Distress_ID") != null ? ""+rs.getInt("Impact_3_Distress_ID") : "";
			i[3]  = rs.getObject("Impact_4_Home_Life_ID") != null ? ""+rs.getInt("Impact_4_Home_Life_ID") : "";
			i[4]  = rs.getObject("Impact_5_Peer_ID") != null ? ""+rs.getInt("Impact_5_Peer_ID") : "";
			i[5]  = rs.getObject("Impact_6_Learning_ID") != null ? ""+rs.getInt("Impact_6_Learning_ID") : "";
			i[6]  = rs.getObject("Impact_7_Leisure_ID") != null ? ""+rs.getInt("Impact_7_Leisure_ID") : "";
			i[7]  = rs.getObject("Impact_8_Burden_ID") != null ? ""+rs.getInt("Impact_8_Burden_ID") : "";

			for(int scount = 0; scount< s.length;scount++){
				link += "&s"+(scount+1)+"="+s[scount];
			}

			for(int icount = 0; icount< i.length; icount++){
				link += "&i"+(icount+1)+"="+s[icount];
			}	
	    	form = 	"<table width=100% border=0>";
	    	form += "<tr style=\"font-size:13px;font-weight:bold\"><td colspan=\"2\">";
			form += "Below are the scores you've entered for the first SDQ  evaluation. You can view the full analysis";
			form += " <a href = \""+link+"\" target=\"_new\"> Here </a>";
	    	form += "</td></tr>";
			form += "<tr>";
			form += "<td colspan=\"2\">";
			form += "</td>";
			form += "</tr>";
			String [] questions = HTML.Questions(gender_id);
			for(int row = 0; row< 25; row++){
				
				if(row % 2 == 0){
				form += "<tr  style=\"font-size:12px;background-color:#F0F0F0\">";
				}
				else
				{
					form += "<tr  style=\"font-size:12px;background-color:#F8F8F8\">";
				}
				form += "<td style=\"border: 1px solid #CCC;\" }>";
				form += questions[row];
				form += "</td>";
				form += "<td style=\"border: 1px solid #CCC;\" }>";
				form +=  processSValue(s[row]);
				form += "</td>";
				form += "</tr>";
			}	
			
			String [] questionsImpact = HTML.QuestionsImpact(gender_id);
			for(int row = 0; row< 8; row++){		
				if(row % 2 == 0){
				form += "<tr  style=\"font-size:12px;background-color:#F0F0F0\">";
				}
				else
				{
					form += "<tr  style=\"font-size:12px;background-color:#F8F8F8\">";
				}
				form += "<td style=\"border: 1px solid #CCC;\" }>";
				form += questionsImpact[row];
				form += "</td>";
				form += "<td style=\"border: 1px solid #CCC;\" }>";
				form +=  processIValue(i[row], row);
				form += "</td>";
				form += "</tr>";	
			 }	
			form += "</table>";
			}
	    	
			catch(SQLException e){e.printStackTrace();}
	    	return form; 
	    }  
	  
	  public String processIValue(String i, int index){
		  if(index == 0){
			  if(i.equals("0"))
				  return "No";
			  else if(i.equals("1"))
				  return "Yes - minor difficulties";
			  else
				  if(i.equals("2"))
					  return "Yes - definite difficulties";
			  else if(i.equals("3"))
				  return "Yes - severe difficulties";
			  else
				  return "Not specified";
		  }
		  else if(index == 1){
			  if(i.equals("0"))
				  return "Less than one month";
			  else if(i.equals("1"))
				  return "1-5 months";
			  else
				  if(i.equals("2"))
					  return "6-12 months";
			  else if(i.equals("3"))
				  return "Over a year";
			  else
				  return "Not specified";
		  }
		  else{
			  if(i.equals("0"))
				  return "Not at all";
			  else if(i.equals("1"))
				  return "A little";
			  else
				  if(i.equals("2"))
					  return "A medium amount";
			  else if(i.equals("3"))
				  return "A great deal";
			  else
				  return "Not specified";
		  }		  
	  }
	  
	  public String processSValue(String s){
		  if(s.equals("0"))
			  return "Not True";
		  else if(s.equals("1"))
			  return "Partly True";
		  else
			  return "Certainly True";
			  
	  }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {	
		  pin = Integer.parseInt((String) request.getParameter("pin"));
		  due = Boolean.valueOf(request.getParameter("due"));

		 fname = request.getParameter("fname");
		  lname= request.getParameter("lname");
		  gender_id = Integer.parseInt(
				  request.getParameter("gender_id"));

	    request.setAttribute("pin",pin); 
	    request.setAttribute("due",due); 
	    request.setAttribute("fname",fname);
	    request.setAttribute("lname",lname);
	    request.setAttribute("gender_id",gender_id+"");

		  connection = setupDatabaseConnection();
		  response.setContentType("text/html");
		  runQuery(response);
    }
}
