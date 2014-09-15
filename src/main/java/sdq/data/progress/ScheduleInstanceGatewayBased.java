package sdq.data.progress;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class ScheduleInstanceGatewayBased {
	
	private static ScheduleInstanceGatewayBased instance = new ScheduleInstanceGatewayBased("hi");
	public static ScheduleInstanceGatewayBased getInstance() { return instance; }
		
	Vector<Integer> due_patients; 
	Vector<Integer> all_patients; 
	Vector<Date> all_due_dates;
	Vector<Integer> all_sdq_counters; 
	
	// Private constructor
	private ScheduleInstanceGatewayBased(String x) {
		due_patients = new Vector<Integer>();
		all_patients = new Vector<Integer>();
		all_due_dates =  new Vector<Date>();
		all_sdq_counters = new Vector<Integer>();
		
	}
	
	public void addNewRecord(int patient, Date dueDate){
		int index = all_patients.indexOf(patient);
		//System.out.println(" IN SCHEDULE ADDING NEW RECORD FOR PATIENT: "+
		//patient+" INDEX ISSS "+index);
		if(index == -1){
			all_patients.add(patient);
			all_due_dates.add(dueDate);
			all_sdq_counters.add(1); //ZI: double check if we start with one
		}
		else{
			all_due_dates.set(index, dueDate);
			int counter = all_sdq_counters.get(index)+1;
			all_sdq_counters.add(counter);
		}
		System.out.println(" ADDED NEW RECORDDDDD FOR "+patient+" in schedule"+
		" due date is !!!! "+dueDate.toString());
	}

	public void makeDue(int patient, Date dueDate){
		
		due_patients.add(new Integer(patient));	
		setDueDate(patient, dueDate);
	}
	
	public boolean isDue(int patient){
		return due_patients.contains(new Integer(patient));
	}
	
	public void noLongerDue(int patient){
		int index = due_patients.indexOf(patient);
		due_patients.remove(index);

				int counter = all_sdq_counters.get(index);
				counter += 1; 
				all_sdq_counters.set(index, counter);

				//next month
				Calendar nextDueDate = Calendar.getInstance();
				nextDueDate.setTime(all_due_dates.get(index));
				nextDueDate.add(Calendar.MONTH, 1);
				Date nextDue = nextDueDate.getTime();	
				setDueDate(patient, nextDue);	


	}
	
	public void setDueDate(int p, Date d){
		int index = all_patients.indexOf(p);
		//removeTuple(index);
		if(index == -1){
		all_patients.add(p);
		all_due_dates.add(d);
		all_sdq_counters.add(1);
		}
		
		else
		{
			all_due_dates.set(index, d);
			int counter = all_sdq_counters.get(index);
			counter += 1; 
			all_sdq_counters.set(index, counter);
		}
	}
	
	public int getCounter(int p){
		int index = all_patients.indexOf(p);
		return all_sdq_counters.get(index);

	}
	
	public Date dueDate(int p){
		int index = all_patients.indexOf(p);
		return all_due_dates.get(index);
	}
}
