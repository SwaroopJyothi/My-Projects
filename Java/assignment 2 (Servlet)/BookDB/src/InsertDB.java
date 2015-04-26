

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class InsertDB
 */
@WebServlet("/InsertDB")
public class InsertDB extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertDB() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String fname=request.getParameter("fname");
		String lname=request.getParameter("lname");
		String isbn=request.getParameter("isbn");
		String title=request.getParameter("book");
		String edition=request.getParameter("edition");
		String copyright=request.getParameter("copyright");
		PrintWriter out=response.getWriter(); 
		HttpSession session = request.getSession();
		SessionConnection sessionConnection = (SessionConnection) session.getAttribute("sessionconnection");
	    Connection connection = null;
	    out.println("<html>");
	    String authorID=null;
	    if (sessionConnection != null) {
		      connection = sessionConnection.getConnection();
		      DbConnect obj=new DbConnect();
		     try {
		    	
				 authorID=obj.getauthorIDbyname(connection,fname,lname);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     // out.println("<h1> The value is "+authorID+"</h1></body></html>");
		      
		      if(authorID==null){
		    	 
		    	 
		    	  obj.insertauthors(connection,fname,lname);
		    	  try {
					String id=obj.getauthorIDbyname(connection, fname, lname);
					obj.inserttitles(connection,isbn,title,edition,copyright);
					String authorisbn=obj.isinauthorisbn(connection,id,isbn);
					if(authorisbn.contentEquals("true")){
						obj.insertauthorisbn(connection,id,isbn); 	
					}
				     
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		      
		      }
		      else{
		    	  obj.inserttitles(connection, isbn, title, edition, copyright);
		    	  String authorisbn=obj.isinauthorisbn(connection,authorID,isbn);
		    		if(authorisbn.contentEquals("true")){
						obj.insertauthorisbn(connection,authorID,isbn); 	
					}
				
		      }
		    	  
		      
	    }
	    out.println("<body onload=alert(\"inserted\");window.location.href=\"ViewDB\">");
		out.print("</body></html>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
	}

}
