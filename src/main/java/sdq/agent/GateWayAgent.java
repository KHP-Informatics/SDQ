package sdq.agent;
/*****************************************************************

This agent receives the blackboard object  
and its content will be sent to the proper agent

*****************************************************************/

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import sdq.communication.BlackBoardBean;
import sdq.data.progress.Schedule;
import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.gateway.*;

public class GateWayAgent extends GatewayAgent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BlackBoardBean board = null;
	boolean act = false; 
		
	protected void processCommand(java.lang.Object obj) {
			
		if (obj instanceof BlackBoardBean)	{
		
			board = (BlackBoardBean)obj;
			System.out.println(" process COMMANDDD IN GATEWAY AGENT WORKING "+
			board.getMessage());
			
			act = true; 
			
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
			msg.addReceiver(new AID( board.getReceiver(), AID.ISLOCALNAME) );
			msg.setContent(board.getMessage());			    
			send(msg);
		}
		
	}

	public void setup()
	{/*
		// Waiting for the answer
		addBehaviour(new TickerBehaviour(this,500) 
		{


			private static final long serialVersionUID = 1L;

			@SuppressWarnings("deprecation")
			public void onTick() {

				 if(act){
					 StringTokenizer tk  = 
							 new StringTokenizer(board.getMessage(),";");
					//	System.out.println(" "
						//		+ "JADE GATEWAY AGENT ACT "+ board.getMessage());
						act = false; 
						
						if(tk.countTokens() == 1) //send message action called by home servlet
						{
				
						boolean due = Schedule.getInstance().isDue(
								Integer.parseInt(board.getMessage()));
						
						String reply = "";
						
						//System.out.println(" IN GATE WAY DUE: "+due);
						int counter = Schedule.getInstance().getCounter(
								Integer.parseInt(board.getMessage()));

						//System.out.println(" IN GATE WAY COUNTER:"+counter);
						Date dueDate = Schedule.getInstance().dueDate(
								Integer.parseInt(board.getMessage()));
				
						 reply = MessageFormat.format("{0};{1};{2};{3};{4}", due,
								dueDate.getDay(), dueDate.getMonth(), dueDate.getYear(), counter);
				

						board.setReply(reply);
						releaseCommand(board);				
						//System.out.println(" SET BACK BOARD REPY "+reply);
						}
						
						else{ //makedue action from InsertServlet
							int pin = Integer.parseInt(tk.nextToken());
						
							Calendar nextDueDate = Calendar.getInstance();
							nextDueDate.add(Calendar.MONTH, 1);
							
						//	Schedule.getInstance().noLongerDue(pin);
						//	Schedule.getInstance().setDueDate(pin, nextDueDate.getTime());
							
							String reply = String.valueOf(nextDueDate.getTime().toLocaleString());  //send done to InsertServlet
							board.setReply(reply); 
							releaseCommand(board);
						}

				 }
				 else{
					 block();
				 }
				ACLMessage msg = receive();
				
				if ((msg!=null)&&(board!=null))	{	
					System.out.println(" gateway RECEEEIIIVEEDD MESSAGE ");
					board.setMessage(msg.getContent());
					releaseCommand(board);				
				} else block();
				
			 }
		});	
		*/
		super.setup();
	}
		
}
