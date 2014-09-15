package sdq.agent.behaviour;

import java.util.Vector;

import sdq.agent.DatabasePingerAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class GatherAgents extends SimpleBehaviour {
	private boolean done = false; 
	private static final long serialVersionUID = 1L;
	Agent myAgent; 
	
	public GatherAgents(Agent a){
		myAgent = a; 
		
	}
	public void action() {
		done = false; 
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Following-up");
		template.addServices(sd);
		
		try{					
			DFAgentDescription[] result = DFService.search(myAgent,template);
			Vector<AID> reportGeneratorAgents = new Vector<AID>();
			for(int i = 0; i < result.length; i++){
				reportGeneratorAgents.addElement(result[i].getName());			
			}
			
			((DatabasePingerAgent)myAgent).setReportGeneratorAgents(reportGeneratorAgents);
		}
		catch(FIPAException fe){
			fe.printStackTrace();
		}
		done  = true; 
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return done; 
	}


}
