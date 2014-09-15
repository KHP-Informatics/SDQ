<head><style>
ul {
    float: left;
    width: 100%;
    padding: 0;
    margin: 0;
    list-style-type: none;
}

a {
    float: left;
    width: 6em;
    text-decoration: none;
    color: white;
    background-color: purple;
    padding: 0.2em 0.6em;
    border-right: 1px solid white;
}

a:hover {
    background-color: fuchsia;
}

li {
    display: inline;
}
</style>
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

<table width="100%" border="0">

<tr align="right">
<td> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</td>
<td align="right" valign="top">
<ul>
               <li><a href="<%= request.getContextPath() %>/things/home">Home<span class="navbar-unread"></span></a>
                <a href="<%= request.getContextPath() %>/things/help">Help<span class="navbar-unread">1</span></a>
                <a href="<%= request.getContextPath() %>/things/privacy">Privacy<span class="navbar-unread">1</span></a>
                <a href="<%= request.getContextPath() %>/things/service">Service Agreement<span class="navbar-unread">1</span></a>
                <a href="<%= request.getContextPath() %>/things/signout">Log Out<span class="navbar-unread">1</span></a></li>
    
               </ul>

</td>
<td rowspan="2" align="right">
<img src="<c:url value="/resources/paper-bag.svg" />" alt="" />
</td>
</tr>
<tr>
  


 <td colspan="2" align="right"><%= "You are logged in as: " + personName %></td>
      


</tr>

  
