package sdq;

import org.example.Start;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.*;
import jade.core.Runtime;

public class SDQ {
	public static AgentController runSDQ(){
		String host = "localhost";
		String port = "1099";
		String name = "backbone";
		Runtime rt = Runtime.instance();
		Profile p = new ProfileImpl();
		p.setParameter(Profile.MAIN_HOST, host);
		p.setParameter(Profile.MAIN_PORT, port);
		
		ContainerController cc = rt.createMainContainer(p);
		
		if(cc!=null){
			try{
				AgentController ac = cc.createNewAgent(name,
										"sdq.agent.BackBoneAgent",
										null);
				ac.start();
				return ac;
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public static void main(String args[]) throws Exception{
		runSDQ();		
		Start.startServer();
	}
	
	
	
}
