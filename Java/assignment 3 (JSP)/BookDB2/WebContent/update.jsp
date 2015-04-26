<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.sql.*,java.io.*,java.text.*,java.util.*" %> 
<%@page import="java.util.*" %> 
<%@ page import="db.DbConnect" %>
<%@ page import="db.SessionConnection" %>
<%@ taglib uri="WEB-INF/tlds/mytags.tld" prefix="up" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <script type='text/javascript' src='http://code.jquery.com/jquery-1.8.3.js'></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 
<title>Updated</title>
</head>

<%
String name=request.getParameter("table");
String newvalue=request.getParameter("value");
String row=request.getParameter("row");
String column=request.getParameter("col");
session = request.getSession();
SessionConnection sessionConnection = (SessionConnection) session.getAttribute("sessionconnection");
Connection connection = null;
if (sessionConnection != null) {
      connection = sessionConnection.getConnection();
}
%>

<up:update connection="<%=connection%>" column="<%=column%>" table="<%=name%>" value="<%=newvalue%>" row="<%=row%>"></up:update>

<body >
<%
response.sendRedirect("displaytable.jsp?table="+name);
%>
</body>
</html>