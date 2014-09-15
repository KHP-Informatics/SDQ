package sdq.agent;

import jade.content.onto.basic.Action;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import sdq.agent.behaviour.ScheduleMonitoring;
import sdq.ontology.*;

public class SchedulerAgent extends Agent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Action action; 
	private Behaviour scheduleMonitoring; 

	private SDQ sdq; 
	private Informant informant; 
	private int patient; 
	
	public void setup(){
		
		 Object[] args = getArguments();
		  action= (Action)args[0];
		  sdq = ((TransferReport)action.getAction()).getSDQ();
		  informant = ((TransferReport)action.getAction()).getInformant();
		  patient = ((TransferReport)action.getAction()).getSDQ().getpatientID();
		  registerMyself();
		   
		  //System.out.println(" I AM SCHEDULE MONITOR "+getLocalName()+" IS UP AND RUNNING**************");
		scheduleMonitoring  = new ScheduleMonitoring(sdq);
		addBehaviour(scheduleMonitoring);
	}
	
	private void registerMyself(){
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Scheduling");
		sd.setName(getLocalName()+"-Scheduling");
		dfd.addServices(sd);
		try{
			DFService.register(this, dfd);
		}
		catch(FIPAException fe){//System.out.println(
	//			"^^^^^^^^^^^^^^another agent has already started monitoring "+
		//" patient - I'd better commit suicide ^^^^^^^^");
		
		this.doDelete();
		}		
	}
	public int getPatient(){
		return patient;
	}
	
	public SDQ getSDQ(){
		return sdq; 
	}
	
	public Informant getInformant(){
		return informant; 
	}
}
