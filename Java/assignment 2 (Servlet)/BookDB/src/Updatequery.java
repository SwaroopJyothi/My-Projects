

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Updatequery
 */
@WebServlet("/Updatequery")
public class Updatequery extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Updatequery() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String name=request.getParameter("table");
		String ne=request.getParameter("value");
		String rowindex=request.getParameter("row");
		String colindex=request.getParameter("col");
		out.println("<html>");
		out.println("<head><script type=\"text/javascript\" src=\"http://code.jquery.com/jquery-1.8.3.min.js\"></script>");
		HttpSession session = request.getSession();
		SessionConnection sessionConnection = (SessionConnection) session.getAttribute("sessionconnection");
	    Connection connection = null;
	    ResultSetMetaData rsm=null;
	    String update="";
	    String ss="";
	    if (sessionConnection != null) {
		      connection = sessionConnection.getConnection();
		DbConnect obj=new DbConnect();
		 ResultSet rs=obj.gettable_conn(connection, name);
		 try {
			 int j=1;
			rsm=rs.getMetaData();
			 update="update "+name+" set "+rsm.getColumnName(Integer.parseInt(colindex)+1)+" = '"+ne+"' where "+rsm.getColumnName(1)+" = ";
			while(rs.next()){
				if(j==Integer.parseInt(rowindex)){
					update=update+" '"+rs.getString(1)+"' ";
					break;
				}
				j++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   ss= obj.operate_row(connection, update);
		 
	    }
		out.println("<body onload=alert(\"updated\");window.location.href=\"/BookDB/Tables?table="+name+"\">");
		out.print("</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
