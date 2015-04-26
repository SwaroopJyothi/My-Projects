package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Update extends TagSupport{

	private String table;
	private Connection conn;
	private String value;
	private String row;
	private String column;
	
	 public void setTable(String table){
		  this.table=table;
	  }
	public void setConnection(Connection conn){
		this.conn=conn;
	}
	public void setValue(String value){
		this.value=value;
	}
	public void setRow(String row){
		this.row=row;
	}
	public void setColumn(String column){
		this.column=column;
	}
public int doStartTag() throws JspException {  
    JspWriter out=pageContext.getOut();  
    try{  
    	ResultSetMetaData rsm=null;
	    String update="";
	    String ss="";
    	if (conn != null) {
		      
		DbConnect obj=new DbConnect();
		 ResultSet rs=obj.gettable_conn(conn, table);
		 try {
			 int j=1;
			rsm=rs.getMetaData();
			 update="update "+table+" set "+rsm.getColumnName(Integer.parseInt(column)+1)+" = '"+value+"' where "+rsm.getColumnName(1)+" = ";
			while(rs.next()){
				if(j==Integer.parseInt(row)){
					update=update+" '"+rs.getString(1)+"' ";
					break;
				}
				j++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   ss= obj.operate_row(conn, update);
		 
	    }
    }catch(Exception e){e.printStackTrace();}  
      
    return SKIP_BODY;  
}  

 
}
