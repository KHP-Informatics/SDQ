package org.example;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
public class LoginServlet extends HttpServlet
{
	
	
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse res)
              throws ServletException, IOException {
res.setContentType("text/html");
PrintWriter out = res.getWriter();

String account = req.getParameter("account");
String password = req.getParameter("password");
String pin = req.getParameter("pin");

if (!allowUser(account, password, pin)) {
out.println("<HTML><HEAD><TITLE>Access Denied</TITLE></HEAD>");
out.println("<BODY>Your login and password are invalid.<BR>");
out.println("You may want to <A HREF=\"/login.html\">try again</A>");
out.println("</BODY></HTML>");
}
else {
out.println("<HTML><HEAD><TITLE>Access Denied</TITLE></HEAD>");
out.println("<BODY> Success! Please while we load your account.<BR>");
out.println("</BODY></HTML>");        
HttpSession session = req.getSession(true);

req.setAttribute("pin",pin);  

RequestDispatcher rd = req.getRequestDispatcher("home");
rd.forward(req,res);

session.setAttribute("logon.isDone", account);  // just a marker object

try {
String target = (String) session.getAttribute("login.target");
if (target != null) {
res.sendRedirect(target);
return;
}
}
catch (Exception ignored) { }

}
}

protected boolean allowUser(String account, String password, String pin) {
return true;
}
	
}