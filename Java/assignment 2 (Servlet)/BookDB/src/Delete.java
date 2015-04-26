

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Delete
 */
@WebServlet("/Delete")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Delete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out =response.getWriter();
		String name=request.getParameter("table");
		String row=request.getParameter("value");
		out.println("<html>");
		out.println("<head><script type=\"text/javascript\" src=\"http://code.jquery.com/jquery-1.8.3.min.js\"></script>");
		out.println("</head>");
	//	out.println("<body><FONT COLOR=\"#ffffff\">");
	//	out.println("<h1>hello "+name+" "+row+"</h1>");
		if(name.contains("Authors")){
			HttpSession session = request.getSession();
			SessionConnection sessionConnection = (SessionConnection) session.getAttribute("sessionconnection");
		    Connection connection = null;
			if (sessionConnection != null) {
			      connection = sessionConnection.getConnection();
			    if(connection!=null)
			    {
			      DbConnect obj=new DbConnect();
			      ResultSet rs=obj.gettable_conn(connection, name);
			      int j=1;
			      ArrayList<String> deletelist=new ArrayList<String>();
			      try {
					while(rs.next()){
						  if(j==Integer.parseInt(row)){
							
							  String id=rs.getString(1);
							//  out.println("<h1>delete from authorisbn "+id+"</h1>");
							  ArrayList<String> isbn=obj.getisbn(connection,id);
							  for(int k=0;k<isbn.size();k++){
								  ArrayList<String> author=obj.getauthorID(connection,isbn.get(k));
								  if(author.size()==1){
							//	out.println("<h1>delete this book "+isbn.get(k)+"</h1>");
								  //obj.deleteauthor(connection,id);
								deletelist.add(isbn.get(k));
								//obj.deletebook(connection,isbn.get(k));
								  }
								  else{
									//  obj.deleteauthor(connection,id);  
								  }
							  }
							  obj.deleteauthor(connection,id);
							  for(int i=0;i<deletelist.size();i++){
								  obj.deletebook(connection, deletelist.get(i));
							  }
							break;
						  }
						  j++;
					  }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		  }
		}
		if(name.contains("Titles")){
			HttpSession session = request.getSession();
			SessionConnection sessionConnection = (SessionConnection) session.getAttribute("sessionconnection");
		    Connection connection = null;
		    if (sessionConnection != null) {
		        connection = sessionConnection.getConnection();
			      DbConnect obj=new DbConnect();
			      ResultSet rs=obj.gettable_conn(connection, name);
			      int j=1;
			      ArrayList<String> deletelist=new ArrayList<String>();
			      try {
					while(rs.next()){
					
						 if(j==Integer.parseInt(row)){
								
							  String isbn=rs.getString(1);
							//  out.println("<h1>delete from authorisbn "+isbn+"</h1>");
							  ArrayList<String> authorID=obj.getauthorID(connection, isbn);
							  for(int k=0;k<authorID.size();k++){
								ArrayList<String> isbnID=obj.getisbn(connection, authorID.get(k));
								if(isbnID.size()<=1){
									//obj.deletebook(connection, isbn);
								//	out.println("<h1>Delete this author "+authorID.get(k)+"</h1>");	
									//obj.deleteauthor(connection, authorID.get(k));
									deletelist.add(authorID.get(k));
								}
																
							  }
							  obj.deletebook(connection, isbn);
							  for(int i=0;i<deletelist.size();i++){
								  obj.deleteauthor(connection, deletelist.get(i));
							  }
							  
							  break;
						  }
						  j++;
					  }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    }
		out.println("<body onload=alert(\"updated\");window.location.href=\"/BookDB/Tables?table="+name+"\">");
		out.print("</body></html>");
		}
//		

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
