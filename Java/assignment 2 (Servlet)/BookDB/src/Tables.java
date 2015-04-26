

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Tables
 */
@WebServlet("/Tables")
public class Tables extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Tables() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out=response.getWriter();
		
		String name=request.getParameter("table");
		out.println("<html>");
		out.println("<head><script type=\"text/javascript\" src=\"http://code.jquery.com/jquery-1.8.3.min.js\"></script>");
		
		out.println("<style type='text/css'>");
		out.println("tr { cursor: default; }");
		out.println(".highlight { background:#bf5c71; }");
		out.println("</style>");
				
		out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"table.css\">");
		
		out.println("<script type=\"text/javascript\" >");
		out.println("$(function () {");
		out.println("    $(\"td\").dblclick(function () {");
		out.println("	  var colIndex = $(this).prevAll().length;");
		out.println(" var rowIndex = $(this).parent('tr').prevAll().length;");
		out.println(" var OriginalContent = $(this).text();");
		out.println("$(this).addClass(\"cellEditing\");");
		out.println("$(this).html(\"<input type='text' value='\" + OriginalContent + \"' />\");");
		out.println("$(this).children().first().focus();");
		out.println("$(this).children().first().keypress(function (e) {");
		out.println("if (e.which == 13) {");
		out.println("var newContent = $(this).val();");
		out.println("$(this).parent().text(newContent);");
		out.println("$(this).parent().removeClass(\"cellEditing\");");
		out.println("window.location.href=\"/BookDB/Updatequery?table="+name+"&value=\"+newContent+\"&row=\"+rowIndex+\"&col=\"+colIndex");
		out.println("}");
		out.println("});");
		out.println("$(this).children().first().blur(function(){");
		out.println("$(this).parent().text(OriginalContent);");
		out.println("$(this).parent().removeClass(\"cellEditing\");");
		out.println("});");
		out.println("});");
		out.println("});");
		out.println("</script>");
		
		out.println("<script type='text/javascript'>");
		out.println("var index;");
		out.println("$(window).load(function(){");
		out.println("$(function() {");
		out.println("var rows = $('tr').not(':first');");
		out.println("rows.on('click', function(e) {");
		out.println("var row = $(this);");
		out.println("index=$(this).attr('id');");
		out.println("if ((e.ctrlKey || e.metaKey) || e.shiftKey) {");
		out.println("row.addClass('highlight');");
		out.println("} else {");
		out.println("rows.removeClass('highlight');");
		out.println("row.addClass('highlight');");
		out.println("} });");
		out.println("$(document).bind('selectstart dragstart', function(e) {");
		out.println("e.preventDefault(); return false;");
		out.println("}); }); });");
		out.println("function fnselect(){");
		out.println("var answer = confirm (\"Are you Sure to Delete\")");
		out.println("if(answer){");
		out.println("window.location.href=\"/BookDB/Delete?table="+name+"&value=\"+index");
		out.println("}");
		out.println("else{window.location.href=\"/BookDB/Tables?table="+name+"\";}}");
		out.println("</script>");
		
		out.println("</head><body>");
		
		out.println("<table id=\"user_entries\" class=\"display\" cellspacing=\"0\" width=\"100%\" border=\"1\">");
		out.println("<tr>");
		HttpSession session = request.getSession();
		//String conn=(String) session.getAttribute("uname");
		//String conn2=(String) session.getAttribute("pass");
		SessionConnection sessionConnection = (SessionConnection) session.getAttribute("sessionconnection");
	    Connection connection = null;
	    ResultSetMetaData rsm=null;
	    if (sessionConnection != null) {
		      connection = sessionConnection.getConnection();
		DbConnect obj=new DbConnect();
		      ResultSet rs=obj.gettable_conn(connection, name);
		      try {
		    	   rsm=rs.getMetaData();
		    	  for(int i=1;i<=rsm.getColumnCount();i++){
		    		  out.println("<th>"+rsm.getColumnName(i)+"</th>");
		    	  }
		    	  out.println("</tr>");
		    	  int row=1;
				while(rs.next()){
			    	  out.println("<tr id = "+row+">");
					for(int i=1;i<=rsm.getColumnCount();i++){
					out.println("<td >"+rs.getString(rsm.getColumnName(i))+"</td>");
					}
					out.println("</tr>");
					row++;
			    }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					out.print("</table>");
					out.println("<br>");
					out.println("<br>");
					out.println("<input type=\"button\" id=\"tst\" value=\"Delete\" onclick=\"fnselect()\" />");
			  	out.println("</body></html>");

		}
		

		
		}
		


			/*
			*/



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
