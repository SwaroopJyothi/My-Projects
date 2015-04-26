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
	window.alert(newContent+"  "+rowIndex+"  "+colIndex);
	window.location.href="/BookDB/Updatequery?table="+Authors+"&value="+newContent+"&row="+rowIndex+"&col="+colIndex
            }
        });
         
    $(this).children().first().blur(function(){
        $(this).parent().text(OriginalContent);
        $(this).parent().removeClass("cellEditing");
    });
    });
});
