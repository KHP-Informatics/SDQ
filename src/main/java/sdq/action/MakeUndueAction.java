package sdq.action;
/*****************************************************************
SendMessageAction carries out sending the message to the GateWayAgent
*****************************************************************/

import jade.wrapper.gateway.JadeGateway;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sdq.communication.BlackBoardBean;

public class MakeUndueAction implements Action {

	private int pin; 
	public MakeUndueAction(int p ){
		pin = p;
	}
	public BlackBoardBean perform(HttpServlet servlet,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException{

		// create a BlackBoard for the session if it not exist
		BlackBoardBean board =BlackBoardBean.instance();
		
		board.setReceiver("gateway");
		board.setMessage(pin+";"+false);
		
		try	{
			JadeGateway.execute(board);
		} catch(Exception e) { e.printStackTrace(); }
						
		

		return board; 
		
	}

}

