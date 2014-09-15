package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sdq.action.MakeUndueAction;
import sdq.agent.dbmanagement.DatabaseInformation;
import sdq.communication.BlackBoardBean;


public class InsertSDQServlet extends HttpServlet implements DatabaseInformation{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection connection;  
	 
	 public void init() throws ServletException {
			connection = this.setupDatabaseConnection();

	}
	public Connection setupDatabaseConnection()
	{
		
			System.setProperty("java.net.preferIPv6Addresses","true");
				try{
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					connection = DriverManager.getConnection(databaseURL,userName,password);
					System.out.println(" connection established");
					return connection;
				}catch(Exception e)
				{return null; }
			
	}
		
	protected void doGet(HttpServletRequest request, HttpServletResponse response){
		

	}
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	
    	String body="";
		int pin =  (Integer)request.getSession().getAttribute("pin"); // add to session
		boolean due =  (Boolean )request.getSession().getAttribute("due"); // add to session
		String fname =  (String)request.getSession().getAttribute("fname"); // add to session
		String lname =  (String)request.getSession().getAttribute("lname"); // add to session
		int gender_id =  (Integer)request.getSession().getAttribute("gender_id"); // add to session
		int staffID = 0, wardID=0, informantID = 0, ageRangeID=0; 
	    String informantRel = "", informantDetails="";
		int totalS = 0; 
		String s[] = new String[25]; 
		int totalI = 0; 
		String impact[] = new String[8]; 
	
	    	//1. staff_id, team_ward, informant_id, informant_relationship, age_range_id

			String sdqTableQuery = "SELECT Patient_ID,Assessed_By_Staff_ID,"
			+ "Team_Ward_ID, Informant_ID, Informant_Relationship,"
			+ "Age_Range_ID, Informant_detail"
			+ " from dbo.tblSlamSDQ where Patient_ID = "+pin+" ORDER BY Assessment_Date DESC";
			try{
			Statement statement = connection.createStatement();
			ResultSet insertResultSet = statement.executeQuery(sdqTableQuery);

			if(insertResultSet.next()){
			staffID = insertResultSet.getObject("Assessed_By_Staff_ID") != null ?
									(Integer)insertResultSet.getObject("Assessed_By_Staff_ID") : -1;

			wardID = insertResultSet.getObject("Team_Ward_ID") != null ?
					(Integer)insertResultSet.getObject("Team_Ward_ID") : -1;

			informantID = insertResultSet.getObject("Informant_ID") != null ?
						(Integer)insertResultSet.getObject("Informant_ID") : -1;

			informantRel = insertResultSet.getObject("Informant_Relationship") != null ?
						(String)insertResultSet.getObject("Informant_Relationship") : " not specified ";
						
			ageRangeID = insertResultSet.getObject("Age_Range_ID") != null ?
						(Integer)insertResultSet.getObject("Age_Range_ID") : -1;
			

			informantDetails = insertResultSet.getObject("Informant_detail") != null ?
						(String)insertResultSet.getObject("Informant_detail") : " not specified ";
						

			for(int i = 0;  i< s.length;  i++){
				s[i] = (String)request.getSession().getAttribute("s"+(i+1));
				totalS += Integer.parseInt(s[i]);
			}
			
			for(int i = 0;  i< impact.length; i++){
			  impact[i] = (String)request.getSession().getAttribute("i"+(i+1));
			  totalI += Integer.parseInt(impact[i]);
			}
						  
			}
			// System.out.println(" made action "+ tr.getInformant().getClass()+ " "+tr.getSDQ().getSDQID());
			}catch(Exception e){e.printStackTrace();}
			
	
		 String queryColumns = "(Patient_ID, Assessed_By_Staff_ID, Assessment_Date, Team_Ward_ID, Informant_ID,"+
				 			"Informant_Relationship, Age_Range_ID, Type_ID, Informant_Detail,"+
				 			"Item_1_Considerate_ID, Item_2_Restless_ID, "+
				 			"Item_3_Aches_ID, Item_4_Shares_ID, Item_5_Tempers_ID, "+
				 			"Item_6_Solitary_ID, Item_7_Obedient_ID, Item_8_Worries_ID, "+
				 			"Item_9_Helpful_ID,Item_10_Fidgety_ID, Item_11_Friend_ID, "+
				 			"Item_12_Fights_ID, Item_13_Unhappy_ID, Item_14_Popular_ID, "+
				 			"Item_15_Distractible_ID, Item_16_Clingy_ID, Item_17_Kind_ID, "+
				 			"Item_18_ID, Item_19_Victimized_ID, Item_20_Volunteers_ID, "+
				 			"Item_21_Reflective_ID, Item_22_ID, Item_23_Better_With_Adults_ID, "+
				 			"Item_24_Fears_ID, Item_25_Attention_ID,"+
				 			"Impact_1_Problem_ID, "+
				 			"Impact_2_Duration_ID, Impact_3_Distress_ID, Impact_4_Home_Life_ID, "+
				 			"Impact_5_Peer_ID, Impact_6_Learning_ID, Impact_7_Leisure_ID, "+
				 			"Impact_8_Burden_ID, Emotional_Symptoms_Score, Conduct_Problems_Score, "+
				 			"Hyperactivity_Score, Peer_Problems_Score, Prosocial_Score, Total_Difficulties_Score, "+
				 			"Impact_Score,End_Date, User_Created, Create_Dttm, User_Updated, Updated_Dttm)";
		 String queryValues = "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
		java.util.Date myDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
		PreparedStatement PStmt = null;
		

		String query = "INSERT INTO dbo.tblSlamSDQ "+queryColumns +
					   " VALUES ("+queryValues+");"; 
		try {
			PStmt = connection.prepareStatement(query);
			PStmt.setInt(1, pin);
			PStmt.setInt(2, staffID);
			PStmt.setDate(3,sqlDate);
			PStmt.setInt(4, wardID);
			PStmt.setInt(5,informantID);
			PStmt.setString(6, informantRel);
			PStmt.setInt(7, ageRangeID);
			PStmt.setInt(8, 1);
			PStmt.setString(9, informantDetails);
			for(int i = 10; i<35; i++){
				PStmt.setInt(i, Integer.parseInt(s[i-10]));
			}
			for(int i = 35, j=0; i < 43; i++,j++){
				PStmt.setInt(i,  Integer.parseInt(impact[j]));
			}
			
			//ZI: the invididual scores for the different scales - DO
			for(int i = 43; i< 48; i++){
				PStmt.setInt(i,0);
			}
			PStmt.setInt(48, totalS);
			PStmt.setInt(49, totalI);
			PStmt.setDate(50,sqlDate);
			PStmt.setString(51, "Agent");
			PStmt.setDate(52,sqlDate);
			PStmt.setString(53, "Agent");
			PStmt.setDate(54,sqlDate);		
			PStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			body="something is wrong";
			e.printStackTrace();
		}

		
		//change due in session!!!!
	/*	MakeUndueAction action = new MakeUndueAction(pin);
		BlackBoardBean board = action.perform(this, request, response);
		String nextDueDateDescription = board.getReply();  //reply should be done. I'm not using it here. 
		
		*/
		due = false; 
		request.getSession().setAttribute("due",false); // add to session

			  body += "<table width=100% border=0>";
		      body += "<tr style=\"font-size:13px;font-weight:bold\"><td>";
			  body += "Thank you for completing "+fname+"'s assessment.";
			  if(gender_id == 2)
		      body += "The detailed analysis of her progress";
			  else
			      body += "The detailed analysis of his progress ";
			  body += " will be available in about a month's time.";
			  body +=" In the meantime,  ";
			  body += " THINK OF WHAT TO GIVE THEM NOW. Examples: graph of score improvemnt? or  ";
			  body += " the difference int he total scores of the differnt scales from those of the previous SDQ. to give hopes or something";
			  body += "</td></tr>";
		   	  body += "</table>";

		  response.setContentType("text/html");
		  PrintWriter out = response.getWriter();
			
		    	out.println(HTML.getHead(pin,due,fname,lname));
		    	out.println(HTML.getMenu(pin,due,fname,lname,gender_id));
		    	out.println(HTML.getMain(pin,due,fname,lname,gender_id, body));
		    	out.println(HTML.getFooter());	    	
			out.flush();
			out.close();
    }

}
