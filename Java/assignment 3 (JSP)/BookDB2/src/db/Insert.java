package db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Insert extends TagSupport{

	private Connection conn;
	private String fname;
	private String lname;
	private String isbn;
	private String title;
	private String edition;
	private String copyright;
	
	public void setConnection(Connection conn){
		this.conn=conn;
	}
	public void setFname(String fname){
		this.fname=fname;
	}
	public void setLname(String lname){
		this.lname=lname;
	}
	public void setIsbn(String isbn){
		this.isbn=isbn;
	}
	public void setTitle(String title){
		this.title=title;
	}
	public void setEdition(String edition){
		this.edition=edition;
	}
	public void setCopyright(String copyright){
		this.copyright=copyright;
	}
	
	public int doStartTag() throws JspException {

		String authorID="";
		JspWriter out=pageContext.getOut(); 
		DbConnect obj=new DbConnect();
		 try {
			
			 authorID=obj.getauthorIDbyname(conn,fname,lname);
		  
		  if(authorID==null){
			 
			 
			  obj.insertauthors(conn,fname,lname);
			  try {
				String id=obj.getauthorIDbyname(conn, fname, lname);
				obj.inserttitles(conn,isbn,title,edition,copyright);
				obj.insertauthorisbn(conn,id,isbn); 	
				
			} catch
			(SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
		  }
		  else{
			  obj.inserttitles(conn, isbn, title, edition, copyright);
			  int authorisbn=obj.isinauthorisbn(conn,authorID,isbn);
				if(authorisbn==0){
					obj.insertauthorisbn(conn,authorID,isbn); 	
				}
			
		  }
		 }catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 return SKIP_BODY; 

	}
	
}
