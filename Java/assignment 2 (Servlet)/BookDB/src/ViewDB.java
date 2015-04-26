

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
 * Servlet implementation class ViewDB
 */
@WebServlet("/ViewDB")
public class ViewDB extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewDB() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out=response.getWriter();
		out.println("<html>");
		out.println("<head>");
		
		out.println("<style type='text/css'>");
		out.println("tr { cursor: default; }");
		out.println(".highlight { background: #000000; }");
		out.println("</style>");
		
		out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"table.css\">");
		
		out.print("</head>");
		
		out.println("<body>");
		out.println("<table id=\"user_entries\" class=\"display\" cellspacing=\"0\" width=\"100%\" border=\"1\">");
		
		HttpSession session = request.getSession();
		SessionConnection sessionConnection = (SessionConnection) session.getAttribute("sessionconnection");
	    Connection connection = null;
	    if (sessionConnection != null) {
	        connection = sessionConnection.getConnection();
	        DbConnect obj=new DbConnect();
	        ResultSet  rs=obj.getalldata(connection);
	        try {
				ResultSetMetaData rsmd=rs.getMetaData();
				out.println("<tr>");
				for(int i=1;i<=rsmd.getColumnCount();i++){
					out.println("<th>"+rsmd.getColumnName(i)+"</th>");
				}
				out.println("</tr>");
				
				while(rs.next()){
					out.println("<tr>");
					for(int i=1;i<=rsmd.getColumnCount();i++){
						out.println("<td>"+rs.getString(i)+"</td>");
						
					}
					out.println("</tr>");
		        }
	        } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        
	    }
		
		
		out.println("</body>");
		out.println("</html>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
