package org.example;

public class HTML {
	
	public static String getHead(int pin,boolean due,
			String fname, String lname){
	 String HEAD = "";
	HEAD += "<!DOCTYPE html>";
	HEAD += "<html lang=\"en\">";
	HEAD += "<head>";
	HEAD += "<meta charset=\"utf-8\">";
	HEAD +="<title>SDQ Tracker</title>";
	HEAD += "<link href=\"resources/flat-ui.css\" rel=\"stylesheet\">";
	HEAD += "<link href=\"resources/style.css\" rel=\"stylesheet\">"; 
	HEAD += "<table width=\"100%\" border=\"0\">";
	HEAD += "   <tr>";
	HEAD += "   	  <td align=\"left\">";
	HEAD += "<table>";
	HEAD += "	   <tr>";
	HEAD += "	   	   <td>";
	HEAD += "	   	<img src=\"resources/Images/paper-bag.png\" width=\"52\"></img>";
	HEAD += "   </td>";
	HEAD += "   <td>";
	HEAD += "	   	<h5>SDQ Tracker</h5>";
	HEAD += "   </td>";
	HEAD += "	   </tr>";
	HEAD += "	 	</table>";
	HEAD += "</td>";	
	HEAD += "   	  <td align=\"right\" >";
	HEAD += fname+" "+lname;
	HEAD += "</td>";	
	HEAD += "   </tr>";
	HEAD += "<tr><td colspan=2 height=20>";
	
	HEAD += "<div class=\"menu\" width=100%>";
	HEAD += "<table width = 100%><tr><td>&nbsp; &nbsp; </td><td>";
	HEAD += "<img src=\"resources/Images/facebook.png\" height=19></img></td>";
	HEAD += " <td width = 90% height=20>&nbsp;</td><td>  <a href=\"\" >Sign Out</a></td></tr></table>"; 
	HEAD += "</div>";
	

      HEAD += "</td</tr>";
	return HEAD; 
	}
	
	
	public static String getMenu(int pin,boolean due,
			String fname, String lname, int gender_id){

	String MENU = "";
	MENU += " <tr> <!-- BEGIN MAIN PAGE MENU + MAIN PAGE -->";
	MENU += "<!--  MENU GOES HERE-->";
	MENU += "<td width = \"20%\" valign=\"top\">";
	MENU += "<div class=\"pure-menu pure-menu-open\">";
	MENU += "<a style=\"font-weight:bold;font-size:13px\" class=\"pure-menu-heading\">All Items</a>";
	MENU += "<ul>";
	//MENU += "<li><a href=\"#\">View Previous SDQ Entries</a></li>";
	//MENU += "<li><a href=\"#\">View Previous DAWBA Reports </a></li>";
	if(due){
	MENU += "<li style=\"font-weight:bold;color:#FF9966;font-size:13px\"><a style=\"font-weight:bold;color:#FF9966;font-size:13px\" href=\"/action?pin="+pin+"&due="+due+"&fname="+fname+"&lname="+lname+"&gender_id="+gender_id+"\">Actions (Due)</a></li>";
	}
	else{
	MENU += "<li style=\"font-weight:bold;font-size:13px\"><a href=\"/action?pin="+pin+"&due="+due+"&fname="+fname+"&lname="+lname+"&gender_id="+gender_id+"\">Actions (None)</a></li>";
	}
	MENU += "<li style=\"font-weight:bold;font-size:13px\" class=\"pure-menu-heading\">View by SDQ</li>";
	MENU += "<li style=\"font-weight:bold;font-size:13px\"><a href=\"/sdq1?pin="+pin+"&due="+due+"&fname="+fname+"&lname="+lname+"&gender_id="+gender_id+"\">SDQ 1</a></li>";
	MENU += "<li style=\"font-weight:bold;font-size:13px\"><a href=\"/sdq2?pin="+pin+"&due="+due+"&fname="+fname+"&lname="+lname+"&gender_id="+gender_id+"\">SDQ 2</a></li>";
	MENU += "<li style=\"font-weight:bold;font-size:13px\"><a href=\"/sdq3?pin="+pin+"&due="+due+"&fname="+fname+"&lname="+lname+"&gender_id="+gender_id+"\">SDQ 3</a></li>";
	MENU += "<li style=\"font-weight:bold;font-size:13px\"><a href=\"/sdq4?pin="+pin+"&due="+due+"&fname="+fname+"&lname="+lname+"&gender_id="+gender_id+"\">SDQ 4</a></li>";
	MENU += "<li style=\"font-weight:bold;font-size:13px\"><a href=\"/sdq5?pin="+pin+"&due="+due+"&fname="+fname+"&lname="+lname+"&gender_id="+gender_id+"\">SDQ 5</a></li>";
	MENU += "<li style=\"font-weight:bold;font-size:13px\"><a href=\"/sdq6?pin="+pin+"&due="+due+"&fname="+fname+"&lname="+lname+"&gender_id="+gender_id+"\">SDQ 6</a></li>";
	MENU += "</ul>";
	MENU += "</div>";
	MENU += "</td>";
	return MENU; 
	}
		
	public static String getMain( int pin, boolean due, String fname,
			String lname, int gender_id , String text){
	String MAIN = "";
	MAIN+= "<!-- MAIN PAGE GOES HERE  -->";
	MAIN+= " <td style=\"width=:80%;vertical-align:top\">";
	MAIN+= text;
	MAIN+= "</td>";

	return MAIN; 	  
	}
	
	public static String getFooter(){
		String FOOTER="";

		FOOTER += "<!-- End of Page - Footer -->";
		FOOTER += "</tr>";
		FOOTER += "</table>";
		FOOTER += "</head>";
		FOOTER += "<body>";
		FOOTER += "</body>";
		FOOTER += "</html>";

return FOOTER; 
	}
	
	public static String []  Questions(int gender_id){
	      String questions [] = new String [25];

	questions[0] =" Considerate of other people's feelings";
	questions[1] = "Restless, overactive, cannot stay still for long";
	questions[2] = "Often complains of headaches, stomach-aches or sickness";
	questions[3] = "Shares readily with other young people, for example CD's, games, food";
	if(gender_id ==2)
	questions[4] = "Often loses her temper";
	else
		questions[4] = "Often loses his temper";		
	questions[5] = "Would rather be alone than with other young people";	
	questions[6] = "Generally well behaved, usually does what adults request";
	questions[7] = "Many worries or often seems worried";
	questions[8] = "Helpful if someone is hurt, upset or feeling ill";
	questions[9] = "Constantly fidgeting or squirming	";
	questions[10] = "Has at least one good friend	";
	questions[11] = "Often fights with other young people or bullies them";	
	questions[12] = "Often unhappy, depressed or tearful	";
	questions[13] = "Generally liked by other young people	";
	if(gender_id ==2)
		questions[14] = "Easily distracted, her concentration wanders	";
	else
		questions[14] = "Easily distracted, his concentration wanders	";
	questions[15] = "Nervous in new situations, easily loses confidence	";
	questions[16] = "Kind to younger children	";
	questions[17] = "Often lies or cheats	";
	questions[18] = "Picked on or bullied by other young people";
	questions[19] = "Often offers to help others (parents, teachers, children)	";
	questions[20] = "Thinks things out before acting";
	questions[21] = "Steals from home, school or elsewhere";
	questions[22] = "Gets along better with adults than with other young people";
	questions[23] = "Many fears, easily scared	";
	questions[24] = "Good attention span, sees chores or homework through to the end	";

	return questions; 
	}
	
	public static String []  QuestionsImpact(int gender_id){
	      String questions [] = new String [8];

	 	 if(gender_id ==2)
			 questions[0] = "Does she have emotional, concentration, behavioural or social difficulties?";
		    		else
		    questions[0] = "Does he have emotional, concentration, behavioural or social difficulties?";

		questions[1] = "How long have these difficulties been present?";
		
		
		 if(gender_id ==2)
			 questions[2] = "Do these difficulties upset or distress her?";
		    		else
		    questions[2] = "Do these difficulties upset or distress him?";

		 
		 if(gender_id ==2)
			 questions[3] = "Do these difficulties interfere with her everyday life at home?";
		    		else
		    questions[3] = "Do these difficulties interfere with his everyday life at home?";

		 if(gender_id ==2)
			 questions[4] = "Do these difficulties affect her friendship with others?";
		    		else
		    questions[4] = "Do these difficulties affect his friendship with others?";

		 if(gender_id ==2)
			 questions[5] = "Do these difficulties interfere with her classroom learning?";
		    		else
		    questions[5] = "Do these difficulties interfere with his classroom learning?";

		 
		 if(gender_id ==2)
			 questions[6] = "Do these difficulties affect her leisure activities?";
		    		else
		    questions[6] = "Do these difficulties affect his leisure activities?";
	 
		questions[7] = "Do these difficulties put a burden on you or the family as a whole?";
		
	return questions; 
	}

}
