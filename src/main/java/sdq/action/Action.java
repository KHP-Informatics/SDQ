package sdq.action;
/*****************************************************************

Generic Action interface for the servlet

*****************************************************************/

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import sdq.communication.BlackBoardBean;

public interface Action {

	 public BlackBoardBean perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) 
	 	throws IOException, ServletException;
	 
}
