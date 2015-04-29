<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.sql.*,java.io.*,java.text.*,java.util.*" %> 
<%@page import="java.util.*" %> 
<%@ page import="db.SessionConnection" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
session = request.getSession();
SessionConnection sessionConnection = (SessionConnection) session.getAttribute("sessionconnection");
Connection connection = null;

if (sessionConnection != null) {

	connection = sessionConnection.getConnection();

	session.removeAttribute("sessionconnection");
	response.sendRedirect("index.jsp");
}


%>
</body>
</html>