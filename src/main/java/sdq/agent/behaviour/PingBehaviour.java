package sdq.agent.behaviour;

import jade.content.*;
import jade.content.onto.basic.Action;
import jade.core.behaviours.*;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

import java.sql.*;
import java.util.Date;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.LocalDate;
import org.joda.time.Years;

import sdq.agent.DatabasePingerAgent;
import sdq.data.progress.Assignments;
import sdq.ontology.Informant;
import sdq.ontology.SDQ;
import sdq.ontology.TransferReport;

public class PingBehaviour extends TickerBehaviour{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Connection dbConnection;
	Agent myAgent;
	java.util.Date lastUpdated;

	public PingBehaviour(Agent a, Connection c){
		super(a,600);
		
		myAgent = a;
		dbConnection = c; 
	}
	public void onTick(){
		
		////System.out.println(" UPDATING RECORDS");
		myAgent.addBehaviour(new GatherAgents(myAgent));
		try{
		updateRecords();
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void updateRecords() throws FIPAException{
		String sdqTableQuery;
		
		if (lastUpdated == null){ //ZI: change this in final version
			lastUpdated = java.sql.Date.valueOf("2011-09-04");
		}

					sdqTableQuery = "SELECT SLAM_SDQ_ID, Patient_ID,Assessed_By_Staff_ID,"
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
					+ " from dbo.tblSLAMSDQ where Type_ID=0";
					//+ " AND Assessment_Date > "+lastUpdated;
		//************run query *//
		try{
		Statement statement = null;
		statement = dbConnection.createStatement();
		ResultSet pingedSet =  statement.executeQuery(sdqTableQuery);
	
			while(pingedSet.next()){	
				if(Assignments.getInstance().lookupPatient(
					Integer.parseInt(pingedSet.getString("PATIENT_ID"))) == -1)
	//ZI:revisit	//&& pingedSet.getDate("Assessment_Date").after(lastUpdated))
					{
				//System.out.println(" \t GOT RECORD FOR patient "+pingedSet.getString("PATIENT_ID")+
					//	" assess: "+pingedSet.getDate("Assessment_Date").toString());
					
				//	Assignments.getInstance().add(null, 
					//		Integer.parseInt(pingedSet.getString("PATIENT_ID")));
					processRecord(pingedSet);
					////System.out.println(" done send record for patient ");
				}
			}			


				//finaliseUpdate();

			//finaliseUpdate();
			pingedSet.close();
		}catch(SQLException e){e.printStackTrace();}
		lastUpdated = new java.sql.Date(Calendar.getInstance().getTime().getTime());
	}
	//***********************************************************
	
	@SuppressWarnings("deprecation")
	private void processRecord(ResultSet rs){	
		try{
		int infId = rs.getObject("Informant_ID") != null ? rs.getInt("Informant_ID") : -1;
		String infRel = rs.getObject("Informant_Relationship") != null ? rs.getString("Informant_Relationship") : "NA";
		String infDetails = rs.getString("Informant_Detail");
		
		Pattern p = Pattern.compile("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b",
			    Pattern.CASE_INSENSITIVE);
			Matcher matcher = p.matcher(infDetails);
			Set<String> emails = new HashSet<String>();
			while(matcher.find()) {
			  emails.add(matcher.group());
			}
			
			Object emailsArray []= emails.toArray();
			String emailAddress = emailsArray[0].toString();
			

		
		int patientID = rs.getObject("PATIENT_ID") != null ? rs.getInt("PATIENT_ID") : -1;
		
		String patientTableQuery = "SELECT dbo.tblPatient.Patient_ID, Forename, Surname,"+
									"Gender_ID,"+
									" Date_Of_Birth from dbo.tblPatient, dbo.tblSLAMSDQ "+
									"WHERE dbo.tblPatient.Patient_ID = "+patientID;
		Statement statement = dbConnection.createStatement();
		ResultSet patientSet = statement.executeQuery(patientTableQuery);
		String fname="", lname=""; Date dob = new Date();

		int gender_id=-1;

		while(patientSet.next()){
			 fname =  patientSet.getObject("Forename") != null ? patientSet.getString("Forename") : " ";
			lname =  patientSet.getObject("Surname") != null ? patientSet.getString("Surname") : " ";
			dob =  patientSet.getObject("Date_Of_Birth") != null ? patientSet.getDate("Date_Of_Birth") : new Date(1,1,1970);
			gender_id = patientSet.getObject("Gender_ID") != null ? patientSet.getInt("Gender_ID") : -1;
		}

		////System.out.println(" name: "+fname +" " +lname);
		
		String gender=""; 
		if(gender_id ==2){
			gender = "f";
		}
		else if(gender_id ==3){
			gender="m";
		}
		else{
			gender="NA";
		}

		int age_range = rs.getObject("Age_Range_ID") != null ? rs.getInt("Age_Range_ID") : -1;
		int age; 
		
		LocalDate dobL = new LocalDate(dob);
		if(dob.equals(new Date(1,1,1970))){
			if(age_range ==0)
					age = 4; 
			else if(age_range ==1)
					age = 10;
			else 
				age = 15;
		}
		else
		{
			LocalDate now = new LocalDate();
			Years years = Years.yearsBetween(dobL, now);
			age = years.getYears();
		}
		
		
		////System.out.println(" PATTIENEEETTT DETAILLLLSSS "+fname+" "+lname+" "+dob.getYear()+" "+dob.getMonth()+" "+dob.getDay()+" "+dob.toString()+" AGGEEE "+age+ " gender "+ gender);

		
		Informant i = new Informant();
		i.setFirstName("zina");
		i.setLastName("ibrahim");
		i.setType(infId);
		i.setRelationship(infRel);
		i.setEmail(emailAddress);
		i.setPhoneNumber("07582859501");
		
		////System.out.println(" GOT INFO FOR INFORMANT: "+i.getFirstName()+" "+i.getLastName()+
			//	i.getType()+" "+i.getRelationship()+" "+i.getEmail()+" "+i.getPhoneNumber());
		
				
		SDQ sdq = new SDQ();

		int sdqID = rs.getObject("SLAM_SDQ_ID") != null ? rs.getInt("SLAM_SDQ_ID") : -1;
		int staffID = rs.getObject("Assessed_By_Staff_ID") != null ? rs.getInt("Assessed_By_Staff_ID") : -1;
		java.sql.Date dateSQL = rs.getObject("Assessment_Date") != null ? rs.getDate("Assessment_Date") : new java.sql.Date(14,04,1989);
	    java.util.Date date = new java.util.Date(dateSQL.getTime());
		
		////System.out.println(" IN PING BEVHAVIOUR; GRABBED ASSESSMENT DATE"+
			//		date);
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
		

		TransferReport tr = new TransferReport();
		tr.setInformant(i);
		tr.setSDQ(sdq);
		
		sendRecord(tr);
		}catch(Exception e){e.printStackTrace();
		}	
	}
	
	private void sendRecord(TransferReport tr){
		try {
			
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("Task-manager");
			template.addServices(sd);
			
			DFAgentDescription result [] = DFService.search(myAgent,template);
			AID  taskDistributorAgent = result[0].getName();			
	
			//System.out.println(" PINGER FOUND TASK MANAGER!!!! FOR RECORD+ "+tr.getSDQ().getpatientID()+" " +taskDistributorAgent.getLocalName());
		final ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.setLanguage(((DatabasePingerAgent)myAgent).getCodec().getName());
		msg.setOntology(((DatabasePingerAgent)myAgent).getOntology().getName());
		msg.setConversationId(tr.getSDQ().getpatientID()+"");
		msg.addReceiver(taskDistributorAgent);
		msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));

		Action act = new Action(); 
		act.setAction(tr); 
		act.setActor(myAgent.getAID());
		
		ContentElementList cel = new ContentElementList();
		cel.add(act);
		ContentManager cm = myAgent.getContentManager();

		cm.fillContent(msg, cel);
		myAgent.send(msg);

		//MessageTemplate mt = 
			//       MessageTemplate.and(  
			  //         MessageTemplate.MatchPerformative( ACLMessage.INFORM),
			    //       MessageTemplate.MatchConversationId(msg.getConversationId())) ;
		
		//ACLMessage reply = myAgent.blockingReceive(mt);
		//while(reply == null)
			////System.out.println(" balala");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	}//end sendRecord
}
