package sdq.agent;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.basic.Action;
import jade.core.Agent;
import jade.core.behaviours.ThreadedBehaviourFactory;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import sdq.agent.behaviour.AwaitingAssignment;
import sdq.data.progress.Assignments;
import sdq.ontology.*;

public class FollowUpAdapterAgent extends Agent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int lockedToPatient; 
	private boolean taken; 

	private Codec codec = new SLCodec();
	private Ontology ontology = SDQOntology.getInstance();

	private SDQ lastSDQ; 
	private Informant lastInformant; 
	private ThreadedBehaviourFactory tbf;

	public void setup(){
		getContentManager().registerLanguage(codec);
		getContentManager().registerOntology(ontology);
		
		tbf = new ThreadedBehaviourFactory();
		registerMyself();
		lockedToPatient = -1;
		
		taken = false; 
		addBehaviour(tbf.wrap(new AwaitingAssignment()));
	}
	
	public synchronized boolean addFollowUpBehaviour(Action act){
		TransferReport tr = (TransferReport)act.getAction();
		SDQ sdq = tr.getSDQ();
			
		Informant inf = tr.getInformant();

		//create new scheduler agent for the record.
		AgentContainer container = (AgentContainer)getContainerController();
		AgentController sch = null;
		String schName = "scheduler-"+sdq.getpatientID();
		try {
				sch = container.createNewAgent(schName,
					"sdq.agent.SchedulerAgent", new Object[] {act});
				sch.start();
				lockTo(sdq.getpatientID());
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//System.out.println(getLocalName()+" could not create scheduler agent for record "+sdq.getpatientID()+
				//	" record has already been locked ");
			return false; 
		}

		lastSDQ = sdq; 
		lastInformant = inf; 
		
		unlock();
		return true;
	}
	
	public Codec getCodec(){
		return codec;
	}	
	public SDQ getSDQ(){
		return lastSDQ; 
	}
	public Informant getInformant(){
		return lastInformant;  
	}

	//*****************************registering with the yellow pages
	private void registerMyself(){
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Following-up");
		sd.setName(getLocalName()+"-Following-up");
		dfd.addServices(sd);
		try{
			DFService.register(this, dfd);
		}
		catch(FIPAException fe){fe.printStackTrace();}		
	}
	
	public void lockTo(int patient){
		taken = true; 
		lockedToPatient = patient;
		//System.out.println(getLocalName()+" is locked to "+patient);
	}
	
	
	public void unlock(){
		Assignments ass = Assignments.getInstance();
		
		ass.remove(this.getAID());
		//System.out.println( getLocalName()+" used to be locked to "+
		//		+ lockedToPatient+" is now fre again!!! ");

		lockedToPatient = -1; 
		taken = false; 

		lastSDQ = new SDQ();
		lastInformant = new Informant();

	}
	public int getLockingPatient(){
		return lockedToPatient;
	}

	public boolean isLocked(){
		return taken;
	}

	
}
