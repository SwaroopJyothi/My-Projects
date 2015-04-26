<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@page import="java.sql.*,java.io.*,java.text.*,java.util.*" %> 
<%@page import="java.util.*" %> 
<%@ page import="db.DbConnect" %>
<%@ page import="db.SessionConnection" %>
<%@ taglib uri="WEB-INF/tlds/mytags.tld" prefix="ins" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <script src="lib/sweet-alert.min.js"></script>
<link rel="stylesheet" type="text/css" href="lib/sweet-alert.css">
	 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<%
String fname=request.getParameter("fname");
String lname=request.getParameter("lname");
String isbn=request.getParameter("isbn");
String title=request.getParameter("book");
String edition=request.getParameter("edition");
String copyright=request.getParameter("copyright");
session = request.getSession();
SessionConnection sessionConnection = (SessionConnection) session.getAttribute("sessionconnection");
Connection connection = null;
String authorID=null;

if (sessionConnection != null) {
      connection = sessionConnection.getConnection();
}

%>
<body  >
<ins:insert lname="<%=lname%>" connection="<%=connection%>" isbn="<%=isbn%>" copyright="<%=copyright%>" edition="<%=edition%>" title="<%=title%>" fname="<%=fname%>"></ins:insert>
<script>
swal("Good job!", "You inserted successfully!", "success")
</script>
<%
response.sendRedirect("viewall.jsp");
%>
</body>
</html>