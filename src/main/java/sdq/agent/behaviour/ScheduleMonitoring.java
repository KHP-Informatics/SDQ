package sdq.agent.behaviour;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sdq.agent.dbmanagement.SDQGrabber;
import sdq.communication.Emailer;
import sdq.data.progress.Assignments;
import sdq.data.progress.Schedule;
import sdq.ontology.*;
import jade.core.behaviours.OneShotBehaviour;

public class ScheduleMonitoring extends OneShotBehaviour{

	private static final long serialVersionUID = 1L;
	String link = "http://www.youthlog.info/py/sdqreport/receiveSDQ.py?l=euk&c=uk&";
	SDQGrabber grabber = SDQGrabber.getInstance();
	private String fname, lname, gender;
	private int patientID, sdqCounter, age, informantID; 
	private String databaseURL = "jdbc:sqlserver://BABYLON-PC\\SQLBABYLON;databaseName=CareNotesTrng"; 
	private String userName = "sa";
	private String password = "meegoreng12";
	Connection connection; 
	SDQ sdqs [] = new SDQ[6];
	Date assessment_dates [] = new Date[6];
	SDQ initialSDQ; 
	String emailAddress="";
	
	public ScheduleMonitoring(SDQ s){
		setupDatabaseConnection();
		//System.out.println(" SCHEDULE MONITOR INITIALISED FOR PATIENT "+s.getpatientID());
		sdqCounter = 0;
		
		patientID = s.getpatientID();
		fname = s.getpatientFName();
		age = s.getAge();
		lname= s.getpatientLName();
		gender = s.getPatientGender();
		initialSDQ  = s; 
	
	}
	public void setupDatabaseConnection(){
		 
		System.setProperty("java.net.preferIPv6Addresses","true");
			try{
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				connection = DriverManager.getConnection(databaseURL,userName,password);
			}catch(Exception e)
			{e.printStackTrace();}
		
		////System.out.println(" Database Pinger Established database connection"); 
	}
	
	@SuppressWarnings("deprecation")
	public void resultSetToSDQ(ResultSet rs) throws SQLException{

			while(rs.next()){
				SDQ sdq = new SDQ(); 
				int sdqID = rs.getObject("SLAM_SDQ_ID") != null ? rs.getInt("SLAM_SDQ_ID") : -1;
				int staffID = rs.getObject("Assessed_By_Staff_ID") != null ? rs.getInt("Assessed_By_Staff_ID") : -1;
				java.sql.Date dateSQL = rs.getObject("Assessment_Date") != null ? rs.getDate("Assessment_Date") : new java.sql.Date(14,04,1989);
			    java.util.Date date = new java.util.Date(dateSQL.getTime());
				informantID = rs.getObject("Informant_ID") != null? rs.getInt("Informant_ID"): -1;
				
				String infDetails = rs.getString("Informant_Detail");
				
				Pattern p = Pattern.compile("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b",
					    Pattern.CASE_INSENSITIVE);
					Matcher matcher = p.matcher(infDetails);
					Set<String> emails = new HashSet<String>();
					while(matcher.find()) {
					  emails.add(matcher.group());
					}
					
					
					if(emails.size()>0)
					{Object emailsArray []= emails.toArray();
					emailAddress = emailsArray[0].toString();
					}
	
				int item1 = rs.getObject("Item_1_Considerate_ID") != null ? rs.getInt("iTEM_1_Considerate_ID") : -1;
				int item2 = rs.getObject("Item_2_Restless_ID") != null ? rs.getInt("Item_2_Restless_ID") : -1;
				int item3 = rs.getObject("Item_3_Aches_ID") != null ? rs.getInt("Item_3_Aches_ID") : -1;
				int item4 = rs.getObject("Item_4_Shares_ID") != null ? rs.getInt("Item_4_Shares_ID") : -1;
				int item5 = rs.getObject("Item_5_Tempers_ID") != null ? rs.getInt("Item_5_Tempers_ID") : -1;
				int item6 = rs.getObject("Item_6_Solitary_ID") != null ? rs.getInt("Item_6_Solitary_ID") : -1;
				int item7 = rs.getObject("Item_7_Obedient_ID") != null ? rs.getInt("Item_7_Obedient_ID") : -1;
				int item8 = rs.getObject("Item_8_Worries_ID") != null ? rs.getInt("Item_8_Worries_ID") : -1;
				int item9 = rs.getObject("Item_9_Helpful_ID") != null ? rs.getInt("Item_9_Helpful_ID") : -1;
				int item10 = rs.getObject("Item_10_Fidgety_ID") != null ? rs.getInt("Item_10_Fidgety_ID") : -1;
				int item11 = rs.getObject("Item_11_Friend_ID") != null ? rs.getInt("Item_11_Friend_ID") : -1;
				int item12 = rs.getObject("Item_12_Fights_ID") != null ? rs.getInt("Item_12_Fights_ID") : -1;
				int item13 = rs.getObject("Item_13_Unhappy_ID") != null ? rs.getInt("Item_13_Unhappy_ID") : -1;
				int item14 = rs.getObject("Item_14_Popular_ID") != null ? rs.getInt("Item_14_Popular_ID") : -1;
				int item15 = rs.getObject("Item_15_Distractible_ID") != null ? rs.getInt("Item_15_Distractible_ID") : -1;
				int item16 = rs.getObject("Item_16_Clingy_ID") != null ? rs.getInt("Item_16_Clingy_ID") : -1;
				int item17 = rs.getObject("Item_17_Kind_ID") != null ? rs.getInt("Item_17_Kind_ID") : -1;
				int item18 = rs.getObject("Item_18_ID") != null ? rs.getInt("Item_18_ID") : -1;
				int item19 = rs.getObject("Item_19_Victimized_ID") != null ? rs.getInt("Item_19_Victimized_ID") : -1;
				int item20 = rs.getObject("Item_20_Volunteers_ID") != null ? rs.getInt("Item_20_Volunteers_ID") : -1;
				int item21 = rs.getObject("Item_21_Reflective_ID") != null ? rs.getInt("Item_21_Reflective_ID") : -1;
				int item22 = rs.getObject("Item_22_ID") != null ? rs.getInt("Item_22_ID") : -1;
				int item23 = rs.getObject("Item_23_Better_With_Adults_ID") != null ? rs.getInt("Item_23_Better_With_Adults_ID") : -1;
				int item24 = rs.getObject("Item_24_Fears_ID") != null ? rs.getInt("Item_24_Fears_ID") : -1;
				int item25 = rs.getObject("Item_25_Attention_ID") != null ? rs.getInt("Item_25_Attention_ID") : -1;
				
				int impact1 = rs.getObject("Impact_1_Problem_ID") != null ? rs.getInt("Impact_1_Problem_ID") : -1;
				int impact2 = rs.getObject("Impact_2_Duration_ID") != null ? rs.getInt("Impact_2_Duration_ID") : -1;
				int impact3 = rs.getObject("Impact_3_Distress_ID") != null ? rs.getInt("Impact_3_Distress_ID") : -1;
				int impact4 = rs.getObject("Impact_4_Home_Life_ID") != null ? rs.getInt("Impact_4_Home_Life_ID") : -1;
				int impact5 = rs.getObject("Impact_5_Peer_ID") != null ? rs.getInt("Impact_5_Peer_ID") : -1;
				int impact6 = rs.getObject("Impact_6_Learning_ID") != null ? rs.getInt("Impact_6_Learning_ID") : -1;
				int impact7 = rs.getObject("Impact_7_Leisure_ID") != null ? rs.getInt("Impact_7_Leisure_ID") : -1;
				int impact8 = rs.getObject("Impact_8_Burden_ID") != null ? rs.getInt("Impact_8_Burden_ID") : -1;

				sdq.setAge(age);
				sdq.setSDQID(sdqID);
				sdq.setDate(date);
				sdq.setpatientFName(fname);
				sdq.setpatientLName(lname);
				sdq.setPatientGender(gender);
				sdq.setPatientID(patientID);
				sdq.setStaffID(staffID);
				sdq.setItem1_Considerate(item1);
				sdq.setItem12_Fights(item2);
				sdq.setItem13_Unhappy(item3);
				sdq.setItem4_Shares(item4);
				sdq.setItem5_Tempers(item5);
				sdq.setItem6_Solitary(item6);
				sdq.setItem7_Obedient(item7);
				sdq.setItem8_Worries(item8);
				sdq.setItem9_Helpful(item9);
				sdq.setItem10_Fidgety(item10);
				sdq.setItem11_Friend(item11);
				sdq.setItem12_Fights(item12);
				sdq.setItem13_Unhappy(item13);
				sdq.setItem14_Popular(item14);
				sdq.setItem15_Distractible(item15);
				sdq.setItem16_Clingy(item16);
				sdq.setItem17_Kind(item17);
				sdq.setItem18_lies(item18);
				sdq.setItem19_Victimized(item19);
				sdq.setItem20_Volunteers(item20);
				sdq.setItem21_Reflective(item21);
				sdq.setItem22_steals(item22);
				sdq.setItem17_Kind(item23);
				sdq.setItem24_Fears(item24);
				sdq.setItem24_Fears(item25);
				
				sdq.setImpact1_Problem(impact1);
				sdq.setImpact2_Duration(impact2);
				sdq.setImpact3_Distress(impact3);
				sdq.setImpact4_Home_Life(impact4);
				sdq.setImpact5_Peer(impact5);
				sdq.setImpact6_Learning(impact6);
				sdq.setImpact7_Leisure(impact7);
				sdq.setImpact8_Burden(impact8);
				System.out.println(" \t\t record: "+patientID+" sdq Counter "+sdqCounter+" sdq date: "+sdq.getDate());
				sdqs[sdqCounter] = sdq; 
				assessment_dates[sdqCounter] = sdq.getDate();
				sdqCounter++;
			}			

	}
	public void action() {	
		Assignments.getInstance().add(this.getAgent().getAID(), initialSDQ.getpatientID());

			String allSdqQuery = " SELECT * from dbo.tblSLAMSDQ where Patient_ID = "+patientID; 
			Statement allSdqStatement= null;
			
			try{
				allSdqStatement = connection.createStatement();
				ResultSet allSdqSet =  allSdqStatement.executeQuery(allSdqQuery);
				resultSetToSDQ(allSdqSet);

			}

			catch(Exception e){
				sdqCounter = 0; 
				sdqs[sdqCounter] = initialSDQ; 
				assessment_dates[sdqCounter] = initialSDQ.getDate();
				//System.out.println("**************************************here it is="+e.getMessage() + "/" + e.getClass());  
				e.printStackTrace();
			}

			Calendar lastSDQCalendar= new GregorianCalendar();
			lastSDQCalendar.setTime(sdqs[sdqCounter-1].getDate());
			lastSDQCalendar.add(Calendar.MONTH, 1);
			Date aMonthLater = lastSDQCalendar.getTime();
			
			Date today = new Date();
			if(aMonthLater.after(today)){
				//ZI: agent will only sleep the difference between the next 
				//due dates and today. 
				//System.out.println("******************CHECKING SCHEDULE OF RECORD "+sdqs[sdqCounter -1].getpatientID()+
					//	" due date after today ");
				
				Schedule.getInstance().addNewRecord(patientID, 
						aMonthLater, sdqCounter-1, false);
				
				
				long diffInMillies = aMonthLater.getTime() - (new Date()).getTime();
				
				

				if(sdqCounter == 0){
					try{
					Emailer.sendMail(
							Emailer.createInitialSDQMessage(patientID)
							, "sdq.tracker@gmail.com",
							emailAddress,
							Emailer.createInitialSDQTitle(fname,lname, 
												informantID));
					}catch(Exception e){e.printStackTrace();}
				}
				
				
				
				myAgent.doWait(diffInMillies);
				Schedule.getInstance().setDueDate(patientID, aMonthLater);
			}

			else{
				//System.out.println(" ******************CHECKING SCHEDULE OF MONITOR FOR "+patientID+" either WOKE UP or didn't sleep because sdq is due ");
				Schedule.getInstance().addNewRecord(patientID, 
						new Date(), sdqCounter-1, true);
			}
			


		while(sdqCounter <6)
		{	
			SDQ latestSDQ;
			try{
				latestSDQ = SDQGrabber.getInstance().getLatestSDQ(patientID);
			} catch(SQLException e)
			{
				latestSDQ = sdqs[sdqCounter-1];
				e.printStackTrace();
			}
			

			if(latestSDQ.getDate().after(sdqs[sdqCounter-1].getDate())){
				sdqs[sdqCounter] = latestSDQ;
				assessment_dates[sdqCounter] = latestSDQ.getDate();
				sdqCounter++;
				
				Calendar lastSDQCalendar2= new GregorianCalendar();
				lastSDQCalendar2.setTime(latestSDQ.getDate());
				lastSDQCalendar2.add(Calendar.MONTH, 1);
				
				Schedule.getInstance().addNewRecord(patientID, 
						lastSDQCalendar2.getTime(), 
						sdqCounter-1,
						false);
				//Schedule.getInstance().noLongerDue(patientID);
							
				myAgent.doWait(10000000); //wait a month for next sdq	
			}			
			else{
				
				try{
					if(!emailAddress.equals(""))
					{
					System.out.println(" $$$$$$$$$$$$$ SENDING EMAIL TO "+emailAddress);
					Emailer.sendMail(
							Emailer.createReminderMessage(patientID)
							, "sdq.tracker@gmail.com",
							emailAddress,
							Emailer.createReminderTitle(fname,lname, 
												informantID));
					}
					
					else
					{
					System.out.println(" $$$$$$$$$$$$$ SENDING EMAIL TO "+emailAddress);
					Emailer.sendMail(
							Emailer.createReminderMessage(patientID)
							, "sdq.tracker@gmail.com",
							"zina.ibrahim@gmail.com",
							Emailer.createReminderTitle(fname,lname, 
												informantID));
					}
					////System.out.println(" reminder email sent");
					}catch(Exception e){}//e.printStackTrace();}						
				
					myAgent.doWait(700000);
					
			} 	//ene else - nothing filled

			}//end while sdq_index <= 5
	}// end action 
	
	public int onEnd(){
		Assignments.getInstance().remove(this.getAgent().getAID());
		myAgent.doDelete();
		return 0; 
	}
	
}// end class
