

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Homepage
 */
@WebServlet("/Homepage")
public class Homepage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Homepage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int au=0;
		int tit=0;
		int entries=0;
		PrintWriter out=response.getWriter();
		HttpSession session = request.getSession();
		SessionConnection sessionConnection = (SessionConnection) session.getAttribute("sessionconnection");
	    Connection connection = null;
	    String authorID=null;
	    DbConnect obj=new DbConnect();
	    if (sessionConnection != null) {
		      connection = sessionConnection.getConnection();
		      ResultSet author=obj.gettable_conn(connection, "Authors");
		      try {
				while(author.next()){
					  au++;
				  }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      ResultSet titles=obj.gettable_conn(connection, "Titles");
		      try {
					while(titles.next()){
						  tit++;
					  }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		      ResultSet authorisbn=obj.gettable_conn(connection, "AuthorISBN");
		      try {
					while(authorisbn.next()){
						  entries++;
					  }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    }
	    
	    out.println("<!doctype html>");
	    out.println("<html><head>");
	    out.println("<meta charset=\"utf-8\"><meta name=\"description\" content=\"\">");
	    out.println("<meta name=\"keywords\" content=\"\"><meta name=\"author\" content=\"Pragmatic Mates s.r.o. - http://pragmaticmates.com\">");
	    out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
	    out.println("<link href=\"http://fonts.googleapis.com/css?family=Raleway:400,700\" rel=\"stylesheet\" type=\"text/css\">");
	    out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/bootstrap.min.css\">");
	    out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/demo.css\">");
	    out.println("</head><body>");
	    out.println("<div class=\"countdown countdown-container container\">");
	    out.println("<div class=\"clock row\"><div class=\"clock-item clock-days countdown-time-value col-sm-6 col-md-3\">");
	    out.println("<div class=\"wrap\"><div class=\"inner\"><div id=\"canvas-days\" class=\"clock-canvas\"></div>");
	    out.println("<div class=\"text\"><p class=\"val\">"+au+"</p><p class=\"type-days type-time\">Authors</p>");
	    out.println("</div></div></div></div>");
	    out.println("<div class=\"clock-item clock-hours countdown-time-value col-sm-6 col-md-3\">");
	    out.println("<div class=\"wrap\"><div class=\"inner\"><div id=\"canvas-hours\" class=\"clock-canvas\"></div>");
	    out.println("<div class=\"text\"><p class=\"val\">"+tit+"</p><p class=\"type-hours type-time\">Books</p>");
	    out.println("</div></div></div></div>");
	    out.println("<div class=\"clock-item clock-minutes countdown-time-value col-sm-6 col-md-3\">");
	    out.println("<div class=\"wrap\"><div class=\"inner\"><div id=\"canvas-minutes\" class=\"clock-canvas\"></div>");
	    out.println("<div class=\"text\"><p class=\"val\">"+entries+"</p><p class=\"type-minutes type-time\">Total Entries</p>");
	    out.println("</div></div></div></div></body></html>");
	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
