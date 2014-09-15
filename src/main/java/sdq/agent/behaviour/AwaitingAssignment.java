package sdq.agent.behaviour;

import sdq.agent.FollowUpAdapterAgent;
import sdq.data.progress.Assignments;
import sdq.ontology.SDQ;
import sdq.ontology.TransferReport;
import jade.content.ContentManager;
import jade.content.onto.basic.Action;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

//***********************************************************************
	public class AwaitingAssignment extends CyclicBehaviour {
	
		MessageTemplate template ; 
	  	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void onStart(){
			//System.out.println(myAgent.getLocalName()+" awaiting assignment !");
		}

		public void action(){
		try{
			if (!((FollowUpAdapterAgent)myAgent).isLocked())
			{
				ACLMessage msg = myAgent.receive();
				
				if(msg != null && msg.getPerformative() == ACLMessage.QUERY_IF){
					
				ContentManager cm = myAgent.getContentManager();		
				Action act = (Action) cm.extractContent(msg);
					TransferReport tr = (TransferReport) act.getAction();
					SDQ sdq = tr.getSDQ();
				Assignments ass = Assignments.getInstance();
					
				ACLMessage reply = msg.createReply();
				////System.out.println(" agent "+myAgent.getLocalName()+""+
					//	" assignment lookup returned "+ass.lookupPatient(sdq.getpatientID()));
				if(((FollowUpAdapterAgent)myAgent).isLocked() || 
						ass.lookupPatient(sdq.getpatientID()) != -1
						){
					reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
					myAgent.send(reply);
				}
					else if(ass.lookupPatient(sdq.getpatientID()) == -1){
							reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
							myAgent.send(reply);
							ACLMessage confirmation = myAgent.blockingReceive();
					//	block(200 + (int)(Math.random()*1000));
							if(confirmation.getPerformative() == ACLMessage.CONFIRM &&
									ass.lookupPatient(sdq.getpatientID()) == -1)
							{
								ass.add(myAgent.getAID(), sdq.getpatientID());
								((FollowUpAdapterAgent)myAgent).lockTo(sdq.getpatientID());
								ACLMessage finalisation = confirmation.createReply();
								finalisation.setPerformative(ACLMessage.CONFIRM);
								myAgent.send(finalisation);

								if(((FollowUpAdapterAgent)myAgent).addFollowUpBehaviour(
										act)){
									
								///	//System.out.println(" locking up succesfful");
								
								//	//System.out.println(" adding! to assignment "
								//	+myAgent.getAID()+" "+sdq.getpatientID());

									
								}
								else{
								//	//System.out.println(" agent "+myAgent.getLocalName()+" wasn't locked because it's already there!");
								}

							}//end if confirm
						else {
							reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
						//	//System.out.println(myAgent.getLocalName()+"'s proposal rejected"
						//			+" for record "+sdq.getpatientID());
							myAgent.send(reply);

						}
					}
					
				}

			}
			
			
				else{
					//System.out.println(myAgent.getLocalName()+" is blocked ");
					block();
				}
			}catch(Exception e){e.printStackTrace();}
		}//end action
	

	}//end AwaitingAssignment