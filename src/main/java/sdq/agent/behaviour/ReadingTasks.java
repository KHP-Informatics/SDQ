package sdq.agent.behaviour;

import sdq.agent.TaskDistributionAgent;
import jade.content.ContentManager;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.content.onto.basic.Action;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
//***********************************************************************
	public class ReadingTasks extends CyclicBehaviour {
		private static final long serialVersionUID = 1L;
		public void action() {	
			MessageTemplate mt = 
				       MessageTemplate.and(  
				           MessageTemplate.MatchPerformative( ACLMessage.INFORM ),
				           MessageTemplate.MatchPerformative( ACLMessage.INFORM ));   		
			ACLMessage message = myAgent.receive(mt);
			if(message != null){
				try {			
					ContentManager cm = myAgent.getContentManager();		
					Action act = (Action) cm.extractContent(message);

						((TaskDistributionAgent)myAgent).addTask(act);
			
					} catch (UngroundedException e) {
					// TODO Auto-generated catch block
						e.printStackTrace();
						} catch (CodecException e) {
					// TODO Auto-generated catch block
						e.printStackTrace();
						} catch (OntologyException e) {
					// TODO Auto-generated catch block
						e.printStackTrace();
						}
			}
			else{
				block();
			}
		}//end action
	}//end Task Reader	