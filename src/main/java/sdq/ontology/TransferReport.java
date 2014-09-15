package sdq.ontology;
import jade.content.*;

public class TransferReport implements AgentAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Informant informant;
	private SDQ sdq;
	
	public void setInformant(Informant i){
		informant = i;
	}
	
	public Informant getInformant(){
		return informant;
	}
	
	public void setSDQ(SDQ s){
		sdq = s;
	}
	
	public SDQ getSDQ(){
		return sdq;
	}
}
