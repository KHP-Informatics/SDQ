package sdq.agent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Vector;

import jade.core.AID;
import jade.core.Agent;
import jade.content.lang.*;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import sdq.ontology.SDQOntology;

public class DatabasePingerAgent extends Agent{
	private static final long serialVersionUID = 1L;
	private String databaseURL = "jdbc:sqlserver://BABYLON-PC\\SQLBABYLON;databaseName=CareNotesTrng"; 
	private String userName = "sa";
	private String password = "meegoreng12";
	private Connection connection;
	private Vector<AID> reportGeneratorAgents;
	private Vector<String> lockedUpRecords; 
	
	private Codec codec = new SLCodec();
	 private Ontology ontology = SDQOntology.getInstance();
	
	public Codec getCodec(){
		return codec;
	}
	
	public Ontology getOntology(){
		return ontology;
	}
		
	public void setup(){		
		getContentManager().registerLanguage(codec);
		getContentManager().registerOntology(ontology);
		setupDatabaseConnection();
		lockedUpRecords = new Vector<String>();

		addBehaviour (new sdq.agent.behaviour.GatherAgents(this));
		addBehaviour(new sdq.agent.behaviour.PingBehaviour(this,connection));	
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

	public Vector<AID> getReportGeneratorAgents(){
		return reportGeneratorAgents;
	}
	public void setReportGeneratorAgents(Vector<AID> r){
		reportGeneratorAgents =r ;
	}

	public boolean isLockedUp(String record){
		for(String rec:lockedUpRecords){
			if(record.equals(rec))
				return true;
		}
		return false;
	}
	
	public void lockUp(String record){
		lockedUpRecords.add(record);
	}
	
}
