$(document).ready( function() {
$("input").click(function(event) {
    getFortuneCookie();
    console.log(event.target);
    if(event.target.value=="classid"){
        document.getElementById("class").innerHTML='<p>class</p>1<input type="radio" name="class" id="1">2<input type="radio" name="class" id="2"> 3<input type="radio" name="class" id="3" checked>';
        var classid= $("#class input:checked")[0].id;
        data["class"]=classid;
    }
});
});
function getFortuneCookie() {
 var a=[];
 
 var data={
    "query":document.getElementById("selectq").querySelectorAll("option")[document.getElementById("selectq").selectedIndex].value,
    "table":document.getElementById("selectt").querySelectorAll("option")[document.getElementById("selectt").selectedIndex].value,
    "col":[],
    traditional: true,
};
len = $("#check input").length
for (var i = 0; i < len; i++) {
    console.log($("#check input")[i].checked);
    if($("#check input")[i].checked == true){
        data.col.push($("#check input")[i].value);
    }
};
console.log(data.col);
jQuery.ajax({
 type: "POST",
 url: "ServletConnect",
 data: data,
success: function(j_data){
   document.getElementById("result").innerHTML=j_data;
}
});


}