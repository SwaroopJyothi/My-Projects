<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.sql.*,java.io.*,java.text.*,java.util.*" %> 
<%@page import="java.util.*" %> 
<%@ page import="db.DbConnect" %>
<%@ page import="db.SessionConnection" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String name=request.getParameter("table");
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	 <script type='text/javascript' src='http://code.jquery.com/jquery-1.8.3.js'></script>
	   <link rel="stylesheet" type="text/css" href="css/demobbutton.css" />
        <link rel="stylesheet" type="text/css" href="css/style4button.css" />
		<link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
	 <script src="lib/sweet-alert.min.js"></script>
<link rel="stylesheet" type="text/css" href="lib/sweet-alert.css">
	 
	   <style type='text/css'>
    tr { cursor: default; }
.highlight { background:#bf5c71; }
  </style>
	 
	 
	 <script type='text/javascript'>//<![CDATA[ 
var index;
$(window).load(function(){
	

$(function() {
    
    /* Get all rows from your 'table' but not the first one 
     * that includes headers. */
    var rows = $('tr').not(':first');
    
    /* Create 'click' event handler for rows */
    rows.on('click', function(e) {
        
        /* Get current row */
        var row = $(this);
		index=$(this).attr('id');
		/* Check if 'Ctrl', 'cmd' or 'Shift' keyboard key was pressed
         * 'Ctrl' => is represented by 'e.ctrlKey' or 'e.metaKey'
         * 'Shift' => is represented by 'e.shiftKey' */
        if ((e.ctrlKey || e.metaKey) || e.shiftKey) {
            /* If pressed highlight the other row that was clicked */
            row.addClass('highlight');
        } else {
            /* Otherwise just highlight one row and clean others */
            rows.removeClass('highlight');
            row.addClass('highlight');
        }
        
    });
    
    /* This 'event' is used just to avoid that the table text 
     * gets selected (just for styling). 
     * For example, when pressing 'Shift' keyboard key and clicking 
     * (without this 'event') the text of the 'table' will be selected.
     * You can remove it if you want, I just tested this in 
     * Chrome v30.0.1599.69 */
    $(document).bind('selectstart dragstart', function(e) { 
        e.preventDefault(); return false; 
    });
    
});
});//]]>  

function fnselect(){
	if(index!=null)
	{
	swal({
		  title: "Are you sure?",
		  text: "You will not be able to recover this imaginary file!",
		  type: "warning",
		  showCancelButton: true,
		  confirmButtonColor: "#DD6B55",
		  confirmButtonText: "Yes, delete it!",
		  cancelButtonText: "No, cancel plx!",
		  closeOnConfirm: false,
		  closeOnCancel: false
		},
		function(isConfirm){
		  if (isConfirm) {
		    swal("Deleted!", "Row "+index+" is deleted", "success");
		    window.location.href="deletetable.jsp?table=<%=name%>&row="+index;
		    
		  } else {
			    swal("Cancelled", "No row deleted :)", "error");
			    window.location.href="displaytable.jsp?table=<%=name%>";
		  }
		});
	}
	 
}

</script>
	 
	 
	 <script type='text/javascript'>
$(function () {
    $("td").dblclick(function () {
	  var colIndex = $(this).prevAll().length;
    var rowIndex = $(this).parent('tr').prevAll().length;
    var OriginalContent = $(this).text();
         
        $(this).addClass("cellEditing");
        $(this).html("<input type='text' value='" + OriginalContent + "' />");
        $(this).children().first().focus();
 
        $(this).children().first().keypress(function (e) {
            if (e.which == 13) {
                var newContent = $(this).val();
                $(this).parent().text(newContent);
                $(this).parent().removeClass("cellEditing");
	//window.alert(newContent+"  "+rowIndex+"  "+colIndex);
	swal("Good job!", "You Updated the value!", "success");
	window.location.href="update.jsp?table=<%=name%>&value="+newContent+"&row="+rowIndex+"&col="+colIndex
      
            }
        });
         
    $(this).children().first().blur(function(){
        $(this).parent().text(OriginalContent);
        $(this).parent().removeClass("cellEditing");
    });
    });
});

</script>
	 
	 
	<style type='text/css'>
	tr { cursor: default; }
	.highlight { background:#bf5c71; }
	</style>
	<link rel="stylesheet" type="text/css" href="css/table.css">
<title>Insert title here</title>
</head>
<body>
<table align="center" style="margin: 0px auto;" id="user_entries">
<tr>

<%
session = request.getSession();
SessionConnection sessionConnection = (SessionConnection) session.getAttribute("sessionconnection");
Connection connection = null;
ResultSetMetaData rsm=null;
	if (sessionConnection != null) {
	    connection = sessionConnection.getConnection();
	DbConnect obj=new DbConnect();
	ResultSet rs=obj.gettable_conn(connection, name);
	  try {
   	   rsm=rs.getMetaData();
   	  for(int i=1;i<=rsm.getColumnCount();i++){
   		%>  
   		<th><%=rsm.getColumnName(i)%></th>
   	  <%
   	  }
   	  %>
   	  </tr>
   	  <%
   	  int row=1;
		while(rs.next()){
			%>
	    	 <tr id = <%=row%>>
			<%
	    	 for(int i=1;i<=rsm.getColumnCount();i++){
	    	%>	 
			<td ><%=rs.getString(rsm.getColumnName(i))%></td>
			<%
	    	 }
			%>
	    	 </tr>
			<%
	    	 row++;
	    }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
%>
</table>
<br>

  <p>
                        <a href="#" id="tst" value="OK" onclick="fnselect()" class="a_demo_four">
                            Delete this row
                        </a>
                    </p>
</body>
</html>