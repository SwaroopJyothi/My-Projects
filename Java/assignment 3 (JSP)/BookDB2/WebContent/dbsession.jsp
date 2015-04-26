<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.sql.*,java.io.*,java.text.*,java.util.*" %> 
<%@page import="java.util.*" %> 
<%@ page import="db.DbConnect" %>
<%@ page import="db.SessionConnection" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
%>
</head>
<body>
<%
String uname="";
String pass="";
session = request.getSession();
SessionConnection sessionConnection = (SessionConnection) session.getAttribute("sessionconnection");
Connection connection = null;

if (sessionConnection != null) {
      connection = sessionConnection.getConnection();
    }
if(connection==null){
	 uname=request.getParameter("uname");
	pass=request.getParameter("password");
	DbConnect obj=new DbConnect(uname, pass);
	connection=obj.login();
	if (connection != null) {
          // store the connection
		session.setAttribute("uname",uname );
		session.setAttribute("pass", pass);
          sessionConnection = new SessionConnection();
          sessionConnection.setConnection(connection);
          session.setAttribute("sessionconnection",sessionConnection);
          response.sendRedirect("main.html");
          return;
        }
	else{
		response.sendRedirect("index.html");
	}
}

else{
	response.sendRedirect("main.html");
}
%>

<%

%>
</body>
</html>
