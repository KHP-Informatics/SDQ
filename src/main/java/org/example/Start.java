package org.example;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.File;
import java.io.IOException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.webapp.WebAppContext;


public class Start extends AbstractHandler
{
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) 
        throws IOException, ServletException
    {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        response.getWriter().println("<h1>Hello World</h1>");
    }
 
	public static void startServer() throws Exception {
   
        
        
        Server server = new Server(8080);

        
        WebAppContext context = new WebAppContext();
        //context.setDescriptor("/WEB-INF/web.xml");           
       //context.setResourceBase("src/viewer/sdq-webapp/src/main/webapp/");
     // context.setDescriptor( "WEB-INF/web.xml");  
       context.setWar("target/sdq-webapp-1.5.war");
       // context.setResourceBase("../DemoWithMultiChannels/src/");
        //context.setContextPath("/");            
       //context.setParentLoaderPriority(true);   
        server.setHandler(context);
     //   context.setDescriptor(ClassLoader.getSystemResource("/WEB-INF/web.xml").toString());

        //context.setDescriptor(getClass().getClassLoader().getResource("/WEB-INF/web.xml").toString());
        //context.setParentLoaderPriority(true);
        System.out.println("Starting Server!");     
        
        
        System.out.println("***************IN START.java RELATIVE PATH curent-working directory*****************");
        System.out.println(new File(".").getAbsolutePath());

        server.setHandler(context);
        
                  server.start();
                 server.join();

    }
}



