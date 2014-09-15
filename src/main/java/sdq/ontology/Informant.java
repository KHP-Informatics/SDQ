package sdq.ontology;

import jade.content.*;

public class Informant implements Concept {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String firstName; 
	private String lastName; 
	private int informantType;
	
	private String relationship; 
	private String email;
	private String phoneNumber;
	
	
	public  int getInformantType(){
		return informantType;
	}
	
	public void setInformantType(int t){
		informantType = t;
	}
	
	
	public String getFirstName(){
		return firstName; 
	}
	
	
	public String getLastName(){
		return lastName; 
	}
	
	public int getType(){
		return informantType;
	}
	
	public String getRelationship(){
		return relationship; 
	}
	
	public String getEmail(){
		return email ;
	}
	
	public String getPhoneNumber(){
		return phoneNumber;
	}
	
	public void setFirstName(String f){
		firstName =f;
	}
	
	public void setLastName(String l){
		lastName = l;
	}
	
	public void setType(int t){
		informantType = t;
	}
	
	public void setRelationship(String r){
		relationship = r;
	}
	
	public void setEmail(String e){
		email = e; 
	}
	
	public void setPhoneNumber(String n){
		phoneNumber = n; 
	}
	
	public String toString(){
		return firstName+" "+lastName+" "+" # "+
				phoneNumber+" @ "+email+
				" relationship: "+
				relationship;
	}
}
