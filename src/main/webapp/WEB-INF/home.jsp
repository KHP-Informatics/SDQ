<html>
<head>
<title>Home</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<head>
   <link rel="stylesheet" href="<c:url value="/resources/flat-ui.css" />" />

</head>
<body>

<table width="100%" height="100" border="1"> 
	<tr>
		<td >
			<%@include file='menu.jsp'%>
		</td>
	</tr>
	
	
	<tr>
	
		<td colspan="3">
		
		
		
		

Greetings, 



<p></p>
The link on this page provides a detailed analysis based on your most recent SDQ scores.
It provides many useful links and books to help you understand your or your 
child's circumstances and work on improving them. 

<p></p>
To view your current report, please follow this 
<a href="<%= request.getContextPath() %>/things/weight">link </a>


<p></p>
In order to help your healthcare workers provide better insight on your or 
your child's circumstances and work together to improve them, we provide you
the means of supplying them with monthly scores recording your child's 
improved circumstances. Your next monthly scores are due WHEN. You can 
provide them using the update scores link on the main menu. 

 <%
 		Object pinNumber = request.getParameter("pin");

        String redirectURL = "/homeServe?pin="+pinNumber;
        response.sendRedirect(redirectURL);

    %>

		</td>
	</tr>
	
</table>


</body>
</html>