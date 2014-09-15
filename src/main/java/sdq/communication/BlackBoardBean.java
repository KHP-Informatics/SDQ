package sdq.communication; 
/*****************************************************************

This the message channel between the GateWayAgent and the servlet

*****************************************************************/

public class BlackBoardBean  implements java.io.Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private String message = new String("");
	private String receiver = new String("");
	private String reply = new String("");


	private static BlackBoardBean bean = new BlackBoardBean();

	private BlackBoardBean()	{
		
	}
	
	public String getMessage()	{
		  	return message;
	}
	
	public void setMessage(String str)	{
		message=str;
	}
	
	public void setReply(String s){
		reply = s; 
	}
	
	public String getReply(){
		return reply; 
	}
	public String getReceiver()	{
		return receiver;
	}
	
	
	public void setReceiver(String receiver)	{
		this.receiver=receiver;
	}
		
	public static BlackBoardBean instance(){
		
		return bean; 
	}

}
