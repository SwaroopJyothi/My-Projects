package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.transform.Result;


public class DbConnect {

	Connection conn1=null;
	Statement stmt=null;
	String user,pass;
	public DbConnect(String string, String pas) {
		// TODO Auto-generated constructor stub
	
			user=string;
			pass=pas;
	}
	public DbConnect() {
		// TODO Auto-generated constructor stub
	}
	public Connection login() {
		// TODO Auto-generated constructor stub
	
			
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn1=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+user, user, pass);
			}catch(Exception e){}
		
		return conn1;
	}


	public ResultSet show_tablename()
	{
		Connection con=login();
		ResultSet rs = null;
		try
		{
			
		stmt = con.createStatement();
	      String sql;
	      sql = "show tables";
	       rs = stmt.executeQuery(sql);
		}catch(Exception e){}
	    return rs;
	}
	public ResultSet gettable(String string) {
		// TODO Auto-generated method stub
		Connection conn=login();
		ResultSet rs1=null;
		try {
			 rs1 = stmt.executeQuery("select * from "+string);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs1;
	}
	
	public int getcolumnnuber(String string) {
		// TODO Auto-generated method stub

		ResultSet rs=null;
		int column = 0;
		try
		{
		rs=gettable(string);
		ResultSetMetaData rsmd = rs.getMetaData();

		 column=rsmd.getColumnCount();
		}catch(Exception e){}
		return column;
	}
	public String operate_row(Connection connection,String sqlStatement) {
		// TODO Auto-generated method stub
		Connection conn=connection;
		String ss=null;
		int i = 0;
		try {
			stmt=conn.createStatement();
			  i =stmt.executeUpdate(sqlStatement);
			  ss="Updated";
			 } catch (SQLException e) {
			// TODO Auto-generated catch block
			ss=e.toString();
		}
		
		return ss;
	}
	public String updaterow(Connection conn,String txt) {
		// TODO Auto-generated method stub
		Connection conn1=conn;
		String ss=null;
		int i=0;
		try{
			stmt=conn1.createStatement();
			  i =stmt.executeUpdate(txt);
			  ss="Row Deleted";
			}
			catch(Exception e){
				ss=e.toString();
			}
		
		return ss;
	}
	public ResultSet undochanges(String table) {
		// TODO Auto-generated method stub
		Connection conn=login();
		ResultSet rs=null;
		try {
			
			conn.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
		
	}
	public ArrayList<String> multiplecolumns(Object[] i) {
		// TODO Auto-generated method stub
		int k=i.length;
		ArrayList<String> colum=new ArrayList<String>();
		
		for(int j=0;j<k;j++){
			ResultSet rs=gettable(i[j].toString());
			try {
				ResultSetMetaData rsmd=rs.getMetaData();
				for(int p=0;p<rsmd.getColumnCount();p++){
					colum.add(i[j].toString()+"."+rsmd.getColumnLabel(p+1));
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return colum;
	}
	public ResultSet getresult(String query) {
		// TODO Auto-generated method stub
		Connection conn=login();
		ResultSet rs1=null;
		try {
			 rs1 = stmt.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs1;}
	
	public ResultSet gettable_conn(Connection connection, String name) {
		// TODO Auto-generated method stub
		ResultSet rs=null;
		try{
			stmt=connection.createStatement();
			rs=stmt.executeQuery("select * from "+name);
		}catch(Exception e){
			
		}
		return rs;
	}
	public ArrayList<String> getisbn(Connection connection, String id) {
		// TODO Auto-generated method stub
	ArrayList<String> isbn=new ArrayList<String>();
	ResultSet rs=null;
	Connection conn=connection;
	try{
		stmt=conn.createStatement();
		rs=stmt.executeQuery("SELECT * from AuthorISBN where AuthorID="+id);
		while(rs.next()){
			isbn.add(rs.getString(2));
		}
	}catch(Exception e){}
		return isbn;
	}
	public ArrayList<String> getauthorID(Connection connection, String string) {
		// TODO Auto-generated method stub
		ArrayList<String> authorID=new ArrayList<String>();
		ResultSet rs=null;
		String isbn=string;
		Connection conn=connection;
		try{
			stmt=conn.createStatement();
			rs=stmt.executeQuery("select * from AuthorISBN where ISBN="+isbn);
			while(rs.next()){
				authorID.add(rs.getString(1));
			}
			
		}catch(Exception e){}
		return authorID;
	}
	public String getauthorIDbyname(Connection connection, String fname,
			String lname) throws SQLException {
		// TODO Auto-generated method stub
		String authorid=null;
		ResultSet rs=null;
		Connection conn=connection;
		stmt=conn.createStatement();
		rs=stmt.executeQuery("select * from Authors where FirstName='"+fname+"' and LastName='"+lname+"'");
		while(rs.next()){
			authorid=rs.getString(1);
		}
		return authorid;
	}
	public void insertauthors(Connection connection, String fname, String lname) {
		// TODO Auto-generated method stub
		ResultSet rs=null;
		Connection conn=connection;
		try {
			stmt=conn.createStatement();
			stmt.executeUpdate("insert into Authors (FirstName,LastName) values ('"+fname+"','"+lname+"')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void inserttitles(Connection connection, String isbn, String title,
			String edition, String copyright) {
		// TODO Auto-generated method stub
		
		ResultSet rs=null;
		Connection conn=connection;
		try {
			stmt=conn.createStatement();
			stmt.executeUpdate("insert into Titles (ISBN,Title,EditionNumber,Copyright) values ('"+isbn+"','"+title+"','"+edition+"','"+copyright+"')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void insertauthorisbn(Connection connection, String id, String isbn) {
		// TODO Auto-generated method stub
		ResultSet rs=null;
		Connection conn=connection;
		try {
			stmt=conn.createStatement();
			stmt.executeUpdate("insert into AuthorISBN (AuthorID,ISBN) values ('"+id+"','"+isbn+"')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void deleteauthor(Connection connection, String id) {
		// TODO Auto-generated method stub
		ResultSet rs=null;
		Connection conn=connection;
		try {
			stmt=conn.createStatement();
			stmt.executeUpdate("delete from AuthorISBN where AuthorID='"+id+"'");
			stmt.executeUpdate("delete from Authors where AuthorID='"+id+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void deletebook(Connection connection, String string) {
		// TODO Auto-generated method stub
		ResultSet rs=null;
		Connection conn=connection;
		try {
			stmt=conn.createStatement();
			stmt.executeUpdate("delete from AuthorISBN where ISBN='"+string+"'");
			stmt.executeUpdate("delete from Titles where ISBN='"+string+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public ResultSet getalldata(Connection connection) {
		// TODO Auto-generated method stub
		ResultSet rs=null;
		Connection conn=connection;
		String sql="select Authors.AuthorID,Authors.FirstName,Authors.LastName,Titles.ISBN,Titles.Title,Titles.EditionNumber,Titles.Copyright ";
		sql=sql+"FROM AuthorISBN INNER JOIN Authors ON Authors.AuthorID=AuthorISBN.AuthorID INNER JOIN Titles on Titles.ISBN=AuthorISBN.ISBN;";
		try {
			stmt=conn.createStatement();
		rs=stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public int isinauthorisbn(Connection connection, String id, String isbn) {
		// TODO Auto-generated method stub
		ResultSet rs=null;
		int i=0;
		try {
			stmt=connection.createStatement();
			rs=stmt.executeQuery("select * from AuthorISBN where AuthorID='"+id+"' and ISBN='"+isbn+"'");
			while(rs.next()){
				i++;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	
}
