package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Search extends SimpleTagSupport {
	 private String sql;
	private Connection conn;
		public void setSql(java.lang.String value) {
	        this.sql = value;
	    }
		public void setConnection(Connection conn){
			this.conn=conn;
		}
		
		public void doTag() throws JspException { 
			JspWriter out=getJspContext().getOut();
			try{
				Statement st = conn.createStatement();
	            ResultSet rs = st.executeQuery(sql);
	            JspContext ctx = getJspContext();
	            
	            ResultSetMetaData rsmd = rs.getMetaData();
	            int count = rsmd.getColumnCount();
	            JspFragment f=getJspBody();
	            while ( rs.next()) {
	                for(int i = 1 ; i <= count ; i ++ ) {
	                   // Create an attribute for each column. 
	                   // Column name is attribute name and value is attribute's value.
	                   ctx.setAttribute( rsmd.getColumnName(i), rs.getString(i));
	                }                      
	                // process the body of the tag and send result to JSPWriter
	                f.invoke(out);
	            }
	            rs.close();
	            
			}catch(Exception e){}
		}

}
