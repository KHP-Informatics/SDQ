package sdq.agent;

import java.util.Vector;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.basic.Action;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import sdq.agent.behaviour.ReadingTasks;
import sdq.agent.behaviour.TaskDistributionScheduler;
import sdq.ontology.*;
public class TaskDistributionAgent extends Agent{
	 
	private static final long serialVersionUID = 1L;
	
	private Codec codec = new SLCodec();
	private Ontology ontology = SDQOntology.getInstance();
	private Behaviour readingTasks; 
	int counter; 
 
	Vector<Action> tasks; 
	
	public void setup(){
		getContentManager().registerLanguage(codec);
		getContentManager().registerOntology(ontology);
		
		readingTasks = new ReadingTasks();
		counter = 0;
		tasks = new Vector<Action>();

		registerMyself();
		addBehaviour(readingTasks);
		 addBehaviour (new TaskDistributionScheduler(this));	
	}

	public String toString(){
		String all="";
		for(int i = 0; i< tasks.size(); i++)
			all += ((TransferReport)((Action)tasks.get(i)).
					getAction()).getSDQ().getpatientID()+"\n";
		return all; 
	}
	
	public Action getNextTask(){
		if(tasks.size() == 0)	
			return null; 
		else{ 		
			//process tasks in a FIFO - always grab what's in index 0 (added first)
			Action t = (Action)tasks.get(0);
			System.out.println(" GOT NEXT ACTION AND REMOVING IT- "+((TransferReport)t.getAction()).getSDQ().getpatientID());
			tasks.remove(0);
			tasks.trimToSize();
			return t;
		}
	}
	
	public Vector<Action> getTasks(){
		return tasks; 
	}

	public Codec getCodec(){
		return codec;
	}
	
	public Ontology getOntology(){
		return ontology;
	}
	public void setTasks(Vector<Action> t){
		tasks = t; 
	}
	
	public void addTask(Action act){
		System.out.println(" added task "+((TransferReport)act.getAction()).getSDQ().getpatientID());
		tasks.add(act);
		System.out.println(" PRINTING ALL TASKS: ");
		System.out.println(" ******************");
		for(int i  = 0 ; i< tasks.size(); i++)
			System.out.println(((TransferReport)tasks.get(i).getAction())
					.getSDQ().getpatientID());
	}	
	public boolean tasksPending(){
		return (tasks.size() > 0);
	}
	
	public void clearTasks(){
		System.out.println(" TASKS ARE BEING CLEARED ");
		tasks.clear();
	}
	
	//*****************************registering with the yellow pages
	private void registerMyself(){
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Task-manager");
		sd.setName(getLocalName());
		dfd.addServices(sd);
		try{
			DFService.register(this, dfd);
		}
		catch(FIPAException fe){fe.printStackTrace();}		
	}
	
}
