function post() {
destination_url = "InsertTest";

input_column_length = document.querySelectorAll("form input").length-1;

var colnameArr = [];
var input_dataArr = [];

for(var colnum=0;colnum<input_column_length;colnum++){
	colnameArr[colnum] = null;
	colnameArr[colnum] = document.querySelectorAll("form input")[colnum].id;
	colnameArr[colnum];
}

console.log(colnameArr);

var data={};
for(var inputnum=0;inputnum<input_column_length;inputnum++){
	input_dataArr[inputnum] = document.getElementById(colnameArr[inputnum]).value;
	console.log(document.getElementById(colnameArr[inputnum]).value);
	console.log(inputnum);
	data[inputnum] ==input_dataArr[inputnum];
}

jQuery.ajax({
 type: "POST",
 url: destination_url,
 data : data,
	success: function(j_data){
   		document.getElementById("result").innerHTML=j_data;
	}
});

}


$(document).ready( function() {
	$("button").click(function(event) {
		post();
	});
});
