<%@ page language="java" import="java.sql.*,java.sql.Connection,java.sql.DriverManager" %>
<html>
<head>
<link href="resources/flat-ui.css" rel="stylesheet">
</head>


<%@ page import="com.microsoft.hsg.applications.*" %>
<%@ page import="com.microsoft.hsg.*" %>
<hr>
<%
PersonInfo personInfo = (PersonInfo)session.getAttribute("wcperson");
String personName = (personInfo == null ? "" : personInfo.getPersonName());
String recordName = (personInfo == null ? "" : personInfo.getRecordName());
String personId = (personInfo == null ? "" : personInfo.getPersonId());
String recordId = (personInfo == null ? "" : personInfo.getRecordId());
String instance = (personInfo == null ? "" : HVInstanceResolver.getInstanceResolver().getInstanceForId(personInfo.getInstanceId()).getName());
%>



<body>
<table width="100%" height="100%" border="0" >
	<tr> <!-- main container -->
		<td width="40%" align="center" >
		<table border=0 bgcolor="#ffffff">
			
			<tr>
				<td>
						<img src="resources/Images/happyChildren.jpg"</img>
				</td>
			</tr>
			
			<tr height="20">
				<td bgcolor=#FFFFFF color="#ffffff">
					<br/>
					
				</td>
				
				
			</tr>
			
			<tr style="height:54;background-color:#333333;color:#ffffff">
				<td >
					<table width="100%">
					<tr>
					<td style="color:#ffffff;font-weight:bold; height:54">
						Connect with other SDQers
					</td>
					<td> 
						<img src="resources/Images/facebook.png">
					</td>
					</tr>
					
					
					</table> 
				</td>
			</tr>
		</table>
		</td>
		<td width="7%">
			&nbsp;
		</td>
		<td width="33%">
		
		<table border="0" >
			<tr height="10%">
				<td>&nbsp; </td>
			</tr>
			
			<tr>
				<td align="right">
				
				
					<img src="resources/Images/paper-bag.png" width="52"></img>
				</td>
				<td>
					<h5>SDQ Tracker</h5>
					
				<p align="left" style = "color: #888888;font-size:70%;">
								Brought to you by SLAM </p>
				</td>
			</tr>
			
		<p style ="color: #888888;font-size:70%;text-align:justify">
Welcome to the SLAM SDQ tracker. Because you're accessing sensitive info, you need to verify your identity using your Microsoft healthvault login.
	This will not only protect your data, but will provide a platform where you can be in control of your progress.
	If you do not have a Microsoft healthvault account, you will be able to create one at the Microsoft website. 
				</p>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<!-- form -->
					
					<table width="100%">
						<FORM  id ="indexform">
								<tr>
									<td>
										ID Number (or e-mail Address)
									</td>
									<td>
										<INPUT id = "inputBox" TYPE=TEXT NAME="pin" VALUE="" SIZE=15>
									</td>
								</tr>
	

								
								<tr>
									<td colspan="2">

									<input id = "submitBut" type=button 
											value="Microsoft Healthvault Login">
											
											
									<script type="text/javascript">
									var button= document.getElementById('submitBut');
									button.onclick= function(){
    								var text= document.getElementById('inputBox').value;
    								if(text==null || text.trim()==""){
    								window.alert(" Please make sure that you enter a valid pin number");
    								}
    								else{
   					 				location.href='<%= request.getContextPath() %>/things/home?pin='+text;
   					 				}
										}
									</script>
	
									</td>
								</tr>				
						</table>
					
					<!-- end form -->
				</td>
			</tr>
			
			
					<tr>
				<td colspan="2">
				<p align="left" style = "color: #888888;font-size:70%;">
				<a href="" >Can't access your account?</a>
				</p>
				</td>
			</tr>
			
			
					<tr>
				<td colspan="2">
				<p align="left" style = "color: #888888;font-size:70%;">
					<a href=" ">Don't have a SLAM SDQ Tracker account?</a>
				</p>
				</td>
			</tr>
			
		</table>
		
		</td>
		
		<td width="10%">
			&nbsp;
		</td>
	
	</tr>
	
	<tr bgcolor="#efefef" align="right" color="#888888" >
		<td colspan="4" align="right">
			<table height="100%" border=0>
				   <tr >
				  	  <td >
  					  	  <p align="left" style = "color: #888888;font-size:70%;">
					  	 	  <A HREF = "#">Contact Us</A></td>
									  </p>
						  <TD> &nbsp; </TD>
					  <td>
 					  	  <p align="left" style = "color: #888888;font-size:70%;">
					  	  <A HREF = "#">Terms of User</A> </td>
							  </P>
					   <TD> &nbsp; </TD>
					  <td> 
   				  	  <p align="left" style = "color: #888888;font-size:70%;">
				  	 <A HREF = "#">Privacy and Cookies</A> </td>
						  </p>
				  </tr>
			</table>
		</td>
	</tr>
</table>

</body>




</html>

