package sdq.data.progress;

import jade.core.AID;

import java.util.Vector;

public class Assignments {
	Vector <Integer> patient_IDS;
	Vector <AID> agents; 
		
	private static Assignments instance = new Assignments();
	public static Assignments getInstance() { return instance; }
	
	// Private constructor
	private Assignments() {
		patient_IDS = new Vector<Integer>();
		agents = new Vector<AID>();
	}
	
	public synchronized void add(AID aid, int patient){
		
		if(patient_IDS.indexOf(patient) == -1){
		patient_IDS.add(patient);
		agents.add(aid);
		}
		
		/*System.out.println("\t AGENT \t\t PATIENT");
		System.out.println("*********************");
		for(int i = 0; i< agents.size();i++){
			System.out.println("\t"+agents.get(i).getLocalName()+"\t"+patient_IDS.get(i));	
		}*/
	}
	
	
	public synchronized void remove(AID aid){
		
		int index = agents.indexOf(aid);
		patient_IDS.remove(index);
		agents.remove(index);
	}
	public void setPatient(AID aid, int p){
		int index = agents.indexOf(aid);
		patient_IDS.set(index, p);
		
	}
	
	public void setAgent(AID aid, int p){
		int index = patient_IDS.indexOf(p);
		agents.set(index, aid);
		
	}
	public int lookupPatient(int p){
		return patient_IDS.indexOf(p);
	}
	
	public int lookupAgent(AID a){
		return agents.indexOf(a);
	}
		
}
