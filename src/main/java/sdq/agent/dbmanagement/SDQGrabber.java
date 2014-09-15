package sdq.agent.dbmanagement;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;





import sdq.ontology.SDQ;

public class SDQGrabber implements DatabaseInformation{
	private Connection con; 
	private static SDQGrabber instance = new SDQGrabber();

	// ----------> Method to access the singleton ontology object
	public static SDQGrabber getInstance() { return instance; }
	
	public SDQGrabber(){
		con = setupDatabaseConnection();
	}
	
	public Connection setupDatabaseConnection()
	{
			System.setProperty("java.net.preferIPv6Addresses","true");
				try{
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					con = DriverManager.getConnection(databaseURL,userName,password);
					//System.out.println(" connection established");
					return con;
				}catch(Exception e)
				{return null; }
		
	}
	
	public SDQ getLatestSDQ(int patientID) throws SQLException{
			String sdqTableQuery = "SELECT * from dbo.tblSLAMSDQ where Patient_ID = "+
			patientID+" ORDER BY Assessment_Date DESC";

			Statement statement = con.createStatement();
			SDQ sdq = new SDQ();
			//************run query *//

		    ResultSet grabberSet = statement.executeQuery(sdqTableQuery);	    
				if(grabberSet.next()){
					int sdqID = grabberSet.getObject("SLAM_SDQ_ID") != null ? grabberSet.getInt("SLAM_SDQ_ID") : -1;
					int staffID = grabberSet.getObject("Assessed_By_Staff_ID") != null ? grabberSet.getInt("Assessed_By_Staff_ID") : -1;
					@SuppressWarnings("deprecation")
					java.sql.Date dateSQL = grabberSet.getObject("Assessment_Date") != null ? grabberSet.getDate("Assessment_Date") : new java.sql.Date(14,04,1989);
				    java.util.Date date = new java.util.Date(dateSQL.getTime());
					//System.out.println(" IN PING BEVHAVIOUR; GRABBED ASSESSMENT DATE"+
						//		date);
					int item1 = grabberSet.getObject("Item_1_Considerate_ID") != null ? grabberSet.getInt("iTEM_1_Considerate_ID") : -1;
					int item2 = grabberSet.getObject("Item_2_Restless_ID") != null ? grabberSet.getInt("Item_2_Restless_ID") : -1;
					int item3 = grabberSet.getObject("Item_3_Aches_ID") != null ? grabberSet.getInt("Item_3_Aches_ID") : -1;
					int item4 = grabberSet.getObject("Item_4_Shares_ID") != null ? grabberSet.getInt("Item_4_Shares_ID") : -1;
					int item5 = grabberSet.getObject("Item_5_Tempers_ID") != null ? grabberSet.getInt("Item_5_Tempers_ID") : -1;
					int item6 = grabberSet.getObject("Item_6_Solitary_ID") != null ? grabberSet.getInt("Item_6_Solitary_ID") : -1;
					int item7 = grabberSet.getObject("Item_7_Obedient_ID") != null ? grabberSet.getInt("Item_7_Obedient_ID") : -1;
					int item8 = grabberSet.getObject("Item_8_Worries_ID") != null ? grabberSet.getInt("Item_8_Worries_ID") : -1;
					int item9 = grabberSet.getObject("Item_9_Helpful_ID") != null ? grabberSet.getInt("Item_9_Helpful_ID") : -1;
					int item10 = grabberSet.getObject("Item_10_Fidgety_ID") != null ? grabberSet.getInt("Item_10_Fidgety_ID") : -1;
					int item11 = grabberSet.getObject("Item_11_Friend_ID") != null ? grabberSet.getInt("Item_11_Friend_ID") : -1;
					int item12 = grabberSet.getObject("Item_12_Fights_ID") != null ? grabberSet.getInt("Item_12_Fights_ID") : -1;
					int item13 = grabberSet.getObject("Item_13_Unhappy_ID") != null ? grabberSet.getInt("Item_13_Unhappy_ID") : -1;
					int item14 = grabberSet.getObject("Item_14_Popular_ID") != null ? grabberSet.getInt("Item_14_Popular_ID") : -1;
					int item15 = grabberSet.getObject("Item_15_Distractible_ID") != null ? grabberSet.getInt("Item_15_Distractible_ID") : -1;
					int item16 = grabberSet.getObject("Item_16_Clingy_ID") != null ? grabberSet.getInt("Item_16_Clingy_ID") : -1;
					int item17 = grabberSet.getObject("Item_17_Kind_ID") != null ? grabberSet.getInt("Item_17_Kind_ID") : -1;
					int item18 = grabberSet.getObject("Item_18_ID") != null ? grabberSet.getInt("Item_18_ID") : -1;
					int item19 = grabberSet.getObject("Item_19_Victimized_ID") != null ? grabberSet.getInt("Item_19_Victimized_ID") : -1;
					int item20 = grabberSet.getObject("Item_20_Volunteers_ID") != null ? grabberSet.getInt("Item_20_Volunteers_ID") : -1;
					int item21 = grabberSet.getObject("Item_21_Reflective_ID") != null ? grabberSet.getInt("Item_21_Reflective_ID") : -1;
					int item22 = grabberSet.getObject("Item_22_ID") != null ? grabberSet.getInt("Item_22_ID") : -1;
					int item23 = grabberSet.getObject("Item_23_Better_With_Adults_ID") != null ? grabberSet.getInt("Item_23_Better_With_Adults_ID") : -1;
					int item24 = grabberSet.getObject("Item_24_Fears_ID") != null ? grabberSet.getInt("Item_24_Fears_ID") : -1;
					int item25 = grabberSet.getObject("Item_25_Attention_ID") != null ? grabberSet.getInt("Item_25_Attention_ID") : -1;					
					int impact1 = grabberSet.getObject("Impact_1_Problem_ID") != null ? grabberSet.getInt("Impact_1_Problem_ID") : -1;
					int impact2 = grabberSet.getObject("Impact_2_Duration_ID") != null ? grabberSet.getInt("Impact_2_Duration_ID") : -1;
					int impact3 = grabberSet.getObject("Impact_3_Distress_ID") != null ? grabberSet.getInt("Impact_3_Distress_ID") : -1;
					int impact4 = grabberSet.getObject("Impact_4_Home_Life_ID") != null ? grabberSet.getInt("Impact_4_Home_Life_ID") : -1;
					int impact5 = grabberSet.getObject("Impact_5_Peer_ID") != null ? grabberSet.getInt("Impact_5_Peer_ID") : -1;
					int impact6 = grabberSet.getObject("Impact_6_Learning_ID") != null ? grabberSet.getInt("Impact_6_Learning_ID") : -1;
					int impact7 = grabberSet.getObject("Impact_7_Leisure_ID") != null ? grabberSet.getInt("Impact_7_Leisure_ID") : -1;
					int impact8 = grabberSet.getObject("Impact_8_Burden_ID") != null ? grabberSet.getInt("Impact_8_Burden_ID") : -1;

					sdq.setSDQID(sdqID);
					sdq.setDate(date);
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
					return sdq; 
				}
				else
					throw new SQLException();
		}

	
	 public SDQ getSpecificRecord(int patientID, Date grabbedSDQDate){ 
		 SDQ sdq = new SDQ();
		 
		 
		    java.sql.Date sqlDate = new java.sql.Date(grabbedSDQDate.getTime());

		    
		    
			String sdqTableQuery = "SELECT TOP 1 SLAM_SDQ_ID, "
					+ "Patient_ID,Assessment_Date"
					+ " from dbo.tblSLAMSDQ where Patient_ID = "+
					patientID+" AND "+
					"Assessment_Date = "+sqlDate;
								
					CallableStatement statement = null;
				    Date date = new Date(); 
					//************run query *//
					try{
					 statement = con.prepareCall(sdqTableQuery,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

				    ResultSet sdqSet = statement.executeQuery();
				    int count =1; 
						while(sdqSet.next()){
							if(count ==1){
							int sdqID = sdqSet.getInt("SLAM_SDQ_ID");
							java.sql.Date dateSQL = sdqSet.getDate("Assessment_Date") ;

							if(dateSQL != null){	
							date = new java.util.Date(dateSQL.getTime());
							}
							else{
								Calendar cal = Calendar.getInstance();
								cal.setTimeInMillis(0);
								cal.set(1989, 1, 1);
								date = cal.getTime(); // get back a Date object
							}
							
							
							sdq.setSDQID(sdqID);
							sdq.setPatientID(patientID);
							sdq.setDate(date);
							}
						}
				
					}catch(SQLException e){e.printStackTrace();}
					
			return sdq; 
	 }
	 
	 
	
		
}
