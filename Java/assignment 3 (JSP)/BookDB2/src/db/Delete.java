package db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Delete extends TagSupport{

	private String table;
	private String row;
	private Connection conn;
	public void setTable(String table){
		this.table=table;
	}
	public void setRow(String row){
		this.row=row;
	}
	public void setConnection(Connection conn){
		this.conn=conn;
	}
	public int doStartTag() throws JspException {  
	    JspWriter out=pageContext.getOut();
	    
	    if(table.contains("Authors")){
			    if(conn!=null)
			    {
			      DbConnect obj=new DbConnect();
			      ResultSet rs=obj.gettable_conn(conn, table);
			      int j=1;
			      ArrayList<String> deletelist=new ArrayList<String>();
			      try {
					while(rs.next()){
						  if(j==Integer.parseInt(row)){
							
							  String id=rs.getString(1);
							//  out.println("<h1>delete from authorisbn "+id+"</h1>");
							  ArrayList<String> isbn=obj.getisbn(conn,id);
							  for(int k=0;k<isbn.size();k++){
								  ArrayList<String> author=obj.getauthorID(conn,isbn.get(k));
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
							  obj.deleteauthor(conn,id);
							  for(int i=0;i<deletelist.size();i++){
								  obj.deletebook(conn, deletelist.get(i));
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
		
	    if(table.contains("Titles")){
			 if (conn != null) {
		          DbConnect obj=new DbConnect();
			      ResultSet rs=obj.gettable_conn(conn, table);
			      int j=1;
			      ArrayList<String> deletelist=new ArrayList<String>();
			      try {
					while(rs.next()){
					
						 if(j==Integer.parseInt(row)){
								
							  String isbn=rs.getString(1);
							//  out.println("<h1>delete from authorisbn "+isbn+"</h1>");
							  ArrayList<String> authorID=obj.getauthorID(conn, isbn);
							  for(int k=0;k<authorID.size();k++){
								ArrayList<String> isbnID=obj.getisbn(conn, authorID.get(k));
								if(isbnID.size()<=1){
									//obj.deletebook(connection, isbn);
								//	out.println("<h1>Delete this author "+authorID.get(k)+"</h1>");	
									//obj.deleteauthor(connection, authorID.get(k));
									deletelist.add(authorID.get(k));
								}
																
							  }
							  obj.deletebook(conn, isbn);
							  for(int i=0;i<deletelist.size();i++){
								  obj.deleteauthor(conn, deletelist.get(i));
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
	    
	    return SKIP_BODY;  
	}
}
