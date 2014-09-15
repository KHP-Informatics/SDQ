package sdq.data.progress;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class Schedule {
	
	private static Schedule instance = new Schedule("hi");
	public static Schedule getInstance() { return instance; }
		
	Vector<Integer> due_patients; 
	Vector<Integer> all_patients; 
	Vector<Date> all_due_dates;
	Vector<Integer> all_sdq_counters; 
	
	private String databaseURL = "jdbc:sqlserver://BABYLON-PC\\SQLBABYLON;databaseName=SdqUsers"; 
	private String userName = "sa";
	private String password = "meegoreng12";
	private Connection connection;

	// Private constructor
	private Schedule(String x) {
		due_patients = new Vector<Integer>();
		all_patients = new Vector<Integer>();
		all_due_dates =  new Vector<Date>();
		all_sdq_counters = new Vector<Integer>();
		
		setupDatabaseConnection();	
	}
	
	public void setupDatabaseConnection(){
		 
		System.setProperty("java.net.preferIPv6Addresses","true");
			try{
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				connection = DriverManager.getConnection(databaseURL,userName,password);
			}catch(Exception e)
			{e.printStackTrace();}
	}
	
	public void addNewRecord(int patient, Date dueDate, int count,
				boolean isDue){

		String searchQuery ="SELECT * from dbo.tblSchedule where pin = "+patient;
		String addQuery = "";
		//************run query *//
		try{
			Statement searchStatement = null;
			searchStatement = connection.createStatement();
			ResultSet searchSet =  searchStatement.executeQuery(searchQuery);
	
			if(searchSet .next()){
				SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
				String formattedDate = df.format(dueDate);

				java.util.Date date1 = df.parse(formattedDate);
				java.sql.Date dSql = new java.sql.Date(date1.getTime());
				int isDueInt; 
				if(isDue)
					isDueInt = 1; 
				else
					isDueInt = 0; 
				addQuery = "UPDATE dbo.tblSchedule SET dueDate= '"+dSql+"', "+
							"counter = "+count+" , due = "+isDueInt+
							" WHERE pin = "+patient; 
				System.out.println(" ADD QUERY: "+addQuery);
				Statement addStatement = connection.createStatement();
				addStatement.execute(addQuery);
			}			
			else
			{
	
				 	String queryValues = "?,?,?,?";
				 	
					addQuery = "INSERT INTO dbo.tblSchedule "+
							   " VALUES ("+queryValues+");"; 
					java.sql.Date sqlDate = new java.sql.Date(dueDate.getTime());
					PreparedStatement PStmt = null;
		
					try {
						PStmt = connection.prepareStatement(addQuery);
						PStmt.setInt(1, patient);
						PStmt.setBoolean(2,isDue);
						PStmt.setDate(3,sqlDate);
						PStmt.setInt(4, count);	
						PStmt.executeUpdate();
						PStmt.close();
					}catch(Exception e){e.printStackTrace();}					
			}
			

			searchSet.close();

		}catch(Exception e){e.printStackTrace();}
	}


	public boolean isDue(int patient){
		String isDueQuery = "SELECT due from dbo.tblSchedule"+
							" WHERE pin = "+patient; 
		Statement isDueStatement;
		try {
				isDueStatement = connection.createStatement();
				ResultSet isDueSet = isDueStatement.executeQuery(isDueQuery);
				boolean isDue = false; 
				if(isDueSet.next()){
					isDue = isDueSet.getBoolean("due");
				}
			
				isDueSet.close();
				return isDue; 
		} catch (SQLException e) {
					//TODO Auto-generated catch block
					e.printStackTrace();
					return false; 
		}
		//return due_patients.contains(new Integer(patient));
	}
	

	public void setDueDate(int p, Date d){
		
		java.sql.Date dsql = new java.sql.Date(d.getTime());
		String setDueDateQuery = "UPDATE dbo.tblSchedule SET "+
								" dueDate= "+dsql+
								" WHERE pin = "+p; 
		Statement setDueDateStatement;
		try {
			setDueDateStatement = connection.createStatement();
			setDueDateStatement.execute(setDueDateQuery);
			setDueDateStatement.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
