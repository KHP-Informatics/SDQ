package sdq.agent.dbmanagement;

import java.sql.Connection;

public interface DatabaseInformation {
	public static final  String databaseURL = "jdbc:sqlserver://BABYLON-PC\\SQLBABYLON;databaseName=CareNotesTrng"; 
	public static final  String userName = "sa";
	public static final  String password = "meegoreng12";

	public Connection setupDatabaseConnection();

}
