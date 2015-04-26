

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 * Servlet implementation class DbSession
 */
@WebServlet("/DbSession")
public class DbSession extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DbSession() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out=response.getWriter();
		HttpSession session = request.getSession();
	    SessionConnection sessionConnection = (SessionConnection) session.getAttribute("sessionconnection");
	    Connection connection = null;
		
		if (sessionConnection != null) {
		      connection = sessionConnection.getConnection();
		    }
		
		if(connection==null){
			String uname=request.getParameter("uname");
			String pass=request.getParameter("password");
			DbConnect obj=new DbConnect(uname, pass);
			connection=obj.login();
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
				response.sendRedirect("index.html");
			}
		}
		else{
			response.sendRedirect("main.html");
		}
	}

}

class SessionConnection implements HttpSessionBindingListener {

	  Connection connection;

	  public SessionConnection() {
	    connection = null;
	  }

	  public SessionConnection(Connection connection) {
	    this.connection = connection;
	  }

	  public Connection getConnection() {
	    return connection;
	  }

	  public void setConnection(Connection connection) {
	    this.connection = connection;
	  }

	  public void valueBound(HttpSessionBindingEvent event) {
	    if (connection != null) {
	      System.out.println("Binding a valid connection");
	    } else {
	      System.out.println("Binding a null connection");
	    }
	  }

	  public void valueUnbound(HttpSessionBindingEvent event) {
	    if (connection != null) {
	      System.out
	          .println("Closing the bound connection as the session expires");
	      try {
	        connection.close();
	      } catch (SQLException ignore) {
	      }
	    }
	  }
	}
