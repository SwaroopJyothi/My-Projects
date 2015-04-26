<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.sql.*,java.io.*,java.text.*,java.util.*" %> 
<%@page import="java.util.*" %> 
<%@ page import="db.DbConnect" %>
<%@ page import="db.SessionConnection" %>
<!DOCTYPE html>
<html lang="en">
    <head>
		<meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
        <title>Circle Hover Effects with CSS Transitions</title>
        <meta name="description" content="Circle Hover Effects with CSS Transitions" />
        <meta name="keywords" content="circle, border-radius, hover, css3, transition, image, thumbnail, effect, 3d" />
        <meta name="author" content="Codrops" />
        <link rel="shortcut icon" href="../favicon.ico"> 
        <link rel="stylesheet" type="text/css" href="css/demo1.css" />
		<link rel="stylesheet" type="text/css" href="css/common.css" />
        <link rel="stylesheet" type="text/css" href="css/style6.css" />
		<link href='http://fonts.googleapis.com/css?family=Open+Sans:300,700' rel='stylesheet' type='text/css' />
		<script type="text/javascript" src="js/modernizr.custom.79639.js"></script> 
		<!--[if lte IE 8]><style>.main{display:none;} .support-note .note-ie{display:block;}</style><![endif]-->
    </head>
    <body>
    <%
    session = request.getSession();
    SessionConnection sessionConnection = (SessionConnection) session.getAttribute("sessionconnection");
    Connection connection = null;
	int authors=0;
	int titles=0;
	int total=0;
    if (sessionConnection != null) {
          connection = sessionConnection.getConnection();
          DbConnect obj=new DbConnect();
          ResultSet rs=obj.gettable_conn(connection, "Authors");
          while(rs.next()){
        	  authors++;
          }
          ResultSet rs1=obj.gettable_conn(connection, "Titles");
          while(rs1.next()){
        	  titles++;
          }
          ResultSet rs2=obj.gettable_conn(connection, "AuthorISBN");
          while(rs2.next()){
        	  total++;
          }
        }
    %>
        <div class="container">
		
			<!-- Codrops top bar -->
            <div class="codrops-top">
                
                <div class="clr"></div>
            </div><!--/ Codrops top bar -->
			
			<header>
			
				
			</header>
			
			<section class="main">
			
				<ul class="ch-grid">
					<li>
						<div class="ch-item ch-img-1">				
							<div class="ch-info-wrap">
								<div class="ch-info">
									<div class="ch-info-front ch-img-1"></div>
									<div class="ch-info-back">
									<h3><font size="5">Authors</font></h3>
										<p> <font size="5"><%=authors%></font> </p>
									</div>	
								</div>
							</div>
						</div>
					</li>
					<li>
						<div class="ch-item ch-img-2">
							<div class="ch-info-wrap">
								<div class="ch-info">
									<div class="ch-info-front ch-img-2"></div>
									<div class="ch-info-back">
									<h3><font size="5">Books</font></h3>
										<p><font size="5"><%=titles%></font></p>
									</div>
								</div>
							</div>
						</div>
					</li>
					<li>
						<div class="ch-item ch-img-3">
							<div class="ch-info-wrap">
								<div class="ch-info">
									<div class="ch-info-front ch-img-3"></div>
									<div class="ch-info-back">
											<h3><font size="5">Total</font></h3>
										<p><font size="5"><%=total%></font></p>
									</div>
								</div>
							</div>
						</div>
					</li>
				</ul>
				
			</section>
        </div>
    </body>
</html>