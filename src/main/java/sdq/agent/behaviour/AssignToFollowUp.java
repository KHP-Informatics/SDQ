package sdq.agent.behaviour;

import java.util.Vector;

import sdq.agent.TaskDistributionAgent;
import sdq.data.progress.Assignments;
import sdq.ontology.SDQ;
import sdq.ontology.SDQOntology;
import sdq.ontology.TransferReport;
import jade.content.ContentElementList;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class AssignToFollowUp extends FSMBehaviour{

	private static final long serialVersionUID = 1L;
	int nResponders; 

	private Action  action;
	private boolean cava;  

	private Agent myAgent; 
	private Codec codec = new SLCodec();
	private Ontology ontology = SDQOntology.getInstance();

	private static final String STATE_A = "A";
	private static final String STATE_B = "B";
	
	public Codec getCodec(){
			return codec;
	}
		
	public Ontology getOntology(){
		return ontology;
	}

	public AssignToFollowUp(Agent a, Action act){
		action = act; 
		myAgent = a; 
		cava = false; 
		myAgent.getContentManager().registerLanguage(codec);
		myAgent.getContentManager().registerOntology(ontology);

		registerFirstState(new FindFollowUp(action), STATE_A);
		registerLastState(new Done(), STATE_B);
		
		registerTransition(STATE_A, STATE_A, -1);
		registerTransition(STATE_A, STATE_B, 0);	
	}
	
	public int onEnd() {
		//////System.out.println("FSM behaviour completed. All scheduler agents:");
		//////System.out.println("**********************************************");
	
			Vector <AID> schedulers = new Vector<AID>();
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("Scheduling");

			template.addServices(sd);
			try{
				
				DFAgentDescription[] result = DFService.search(myAgent,template);
				for(int i = 0; i < result.length; i++){
					schedulers.addElement(result[i].getName());
					//////System.out.println(" Scheduler: "+result[i].getName().getLocalName());
				}
			}
			catch(FIPAException fe){
				fe.printStackTrace();
			}
			
		//myAgent.doDelete();
		return 0;
		//return super.onEnd();
	}

	private class Done extends OneShotBehaviour{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			// TODO Auto-generated method stub	
			//////System.out.println(" phase 2 - done behaviour executed");
		}
		
		public int onEnd() {
			return 0;
		}
	}
	private class FindFollowUp extends OneShotBehaviour{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		Vector<AID> reportGeneratorAgents;
		private Action action; 
		private int exitValue = -1; 
		public FindFollowUp(Action act){
			
				action = act; 
		}
			
			public synchronized void action(){
				getFollowUpAgents();
				
				TransferReport tr = (TransferReport) action.getAction();
				SDQ sdq = tr.getSDQ();
				ACLMessage query = new ACLMessage(ACLMessage.QUERY_IF);
				
				query.setLanguage(((TaskDistributionAgent)myAgent).getCodec().getName());
				query.setOntology(((TaskDistributionAgent)myAgent).getOntology().getName());
				query.setConversationId(tr.getSDQ().getpatientID()+"");
				
				for(int i = 0; i< reportGeneratorAgents.size();i++){
					block(100 + (int)(Math.random()*1000));
					if(i>0){
						query.removeReceiver(reportGeneratorAgents.get(i-1));
					}
					query.addReceiver(reportGeneratorAgents.get(i));
					//query.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
					
					Action act = new Action(); 
					act.setAction(tr); 
					act.setActor(myAgent.getAID());
					
					ContentElementList cel = new ContentElementList();
					cel.add(act);
					ContentManager cm = myAgent.getContentManager();
					try {
						cm.fillContent(query, cel);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					myAgent.send(query);
					////////System.out.println(" query sent by assign to follow up behaviour"+
					//" about  "+tr.getSDQ().getpatientID()+ " i is: "+i);
					ACLMessage reply = myAgent.blockingReceive();

					if(reply == null || reply.getPerformative()==ACLMessage.REJECT_PROPOSAL){		
					//	//////System.out.println(" IN ASSIGN TO FOLLOW UP BEHAVIOUR OF TASK DISTRIBUTION "+
					//" PROPOSAL REJEECTED  BYYY "+reply.getSender().getLocalName());
						////////System.out.println(" still finding a home for record");
					}

					else if(reply.getPerformative() == ACLMessage.ACCEPT_PROPOSAL){
						////////System.out.println(" IN ASSIGN TO FOLLOW UP BEHAVIOUR OF TASK DISTRIBUTION "+
					//" PROPOSAL nominated BYYY "+reply.getSender().getLocalName()+" RECORD: "+
						//		sdq.getpatientID());
						Assignments ass = Assignments.getInstance();
						if(ass.lookupPatient(sdq.getpatientID()) != -1){
							ACLMessage rejection = reply.createReply();
							rejection.setPerformative(ACLMessage.REJECT_PROPOSAL);
							myAgent.send(rejection);
							exitValue = 0;
							break; 
						}
						
						if(!cava)
						{
							cava = true; 
						ACLMessage confirmation = reply.createReply();
						confirmation.setPerformative(ACLMessage.CONFIRM);
						myAgent.send(confirmation);

						
						ACLMessage finalisation = myAgent.blockingReceive();
						if(finalisation.getPerformative()==ACLMessage.CONFIRM){
						exitValue = 0;
						break;
						}
						
						}
						else
						{
							ACLMessage rejection = reply.createReply();
							rejection.setPerformative(ACLMessage.REJECT_PROPOSAL);
							myAgent.send(rejection);
							exitValue = 0;
							break;
						}

					}
				}//end looping through FollowUPAgent agents				
		}
			
		public int onEnd() {
				return exitValue;
		}
		
			private void getFollowUpAgents(){
				reportGeneratorAgents = new Vector<AID>();
				DFAgentDescription template = new DFAgentDescription();
				ServiceDescription sd = new ServiceDescription();
				sd.setType("Following-up");
				template.addServices(sd);

				try{
					
					DFAgentDescription[] result = DFService.search(myAgent,template);
					for(int i = 0; i < result.length; i++){
						reportGeneratorAgents.addElement(result[i].getName());			
					}
				}
				catch(FIPAException fe){
					fe.printStackTrace();
				}
			}
		
	}//end private class 


}
