package sdq.agent.behaviour;

import java.util.Vector;

import sdq.agent.TaskDistributionAgent;
import jade.content.onto.basic.Action;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;

public class TaskDistributionScheduler extends CyclicBehaviour{

	private Agent myAgent;
	Vector<Action> tasks; 

	public TaskDistributionScheduler(Agent a) {
		myAgent = a;
		tasks = ((TaskDistributionAgent)myAgent).getTasks();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void action() {
		// TODO Auto-generated method stub
		tasks = ((TaskDistributionAgent)myAgent).getTasks();
		
		if(!tasks.isEmpty())
		{

		Action a = ((TaskDistributionAgent)myAgent).getNextTask();
	//	System.out.println(" In Cyclic Behaviour: tasks "+tasks.size()+
		//		" finding a home for record "+((TransferReport)a.getAction()).getSDQ().getpatientID());
		Behaviour assignToFollowUp= new AssignToFollowUp(myAgent, a);

		((TaskDistributionAgent)myAgent).addBehaviour(assignToFollowUp);
		//int x = assignToFollowUp.onEnd();
		//System.out.println(" behaviour added for record ");
		block(10000);
		//((TaskDistributionAgent)myAgent).removeBehaviour(assignToFollowUp);

		//tasks = ((TaskDistributionAgent)myAgent).getTasks();

		}
	}



}
