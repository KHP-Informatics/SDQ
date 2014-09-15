package sdq.agent;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.wrapper.*;

public class BackBoneAgent extends Agent {
	private static final long serialVersionUID = 1L;
		AgentController dbp = null;
		String dbpName = "dbPinger";
		String reportGeneratorNames[] =new String[20];
		AgentController reportGeneratorController[] = new AgentController[reportGeneratorNames.length];
		
		String gatewayAgentName = "gateway";
		AgentController gatewayController = null; 
	
		String taskDistributorName = "taskManager";
		AgentController taskDistributorController = null; 
		
		public void setup(){
			registerMyself();
 			fireAgents();
			//setEnabledO2ACommunication(true,0); 			
		}

		private void registerMyself(){
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			ServiceDescription sd = new ServiceDescription();
			sd.setType("Backbone");
			sd.setName(getLocalName()+"-Backbone");
			dfd.addServices(sd);
			try{
				DFService.register(this, dfd);
			}
			catch(FIPAException fe){fe.printStackTrace();}		
		}
		


		public void fireAgents(){
			try{
				AgentContainer container = (AgentContainer)getContainerController();
				dbp = container.createNewAgent(dbpName,
							"sdq.agent.DatabasePingerAgent", null);
				dbp.start();

				
				taskDistributorController = container.createNewAgent(taskDistributorName,
							"sdq.agent.TaskDistributionAgent", null);
				taskDistributorController.start();	
				

				//report generator agents
				for(int i = 0; i< reportGeneratorNames.length; i++){
					reportGeneratorNames[i] = "follow-up-monitor-"+i;
				}

				for(int i =0; i< reportGeneratorNames.length; i++){
					

					reportGeneratorController[i] = container.createNewAgent(
								reportGeneratorNames[i],
							"sdq.agent.FollowUpAdapterAgent", null);
					reportGeneratorController[i].start();	
				}
				
				gatewayController = container.createNewAgent(
						gatewayAgentName, 
						"sdq.agent.GateWayAgent", null);
				gatewayController.start();			
			}catch(Exception any){any.printStackTrace();}

		}
}



