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
</head>
<body>
        <jsp:useBean id="mybean" scope="session" class="db.ConnectionBean" />
        <jsp:setProperty name="mybean" property="username" />
        <jsp:setProperty name="mybean" property="password"/>
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
	uname=mybean.getUsername();
	 pass = mybean.getPassword();
	connection = mybean.dbConnect();
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
		response.sendRedirect("index.jsp");
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
