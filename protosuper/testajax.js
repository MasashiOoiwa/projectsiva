var xmlhttp = this.XMLHttpRequest ? new XMLHttpRequest() :
　 　 　 　 　new ActiveXObject("Msxml2.XMLHTTP") || 
　 　 　 　 　new ActiveXObject("Microsoft.XMLHTTP");
　
function getFortuneCookie() {
　 var target = this.XMLHttpRequest ?
　 　document.getElementById("result") :
　 　document.all("result");
	target.innerHTML = "[ここに結果が表示されます。]";
　   xmlhttp.open("GET", "fortune.jsp");
　   xmlhttp.onreadystatechange = function() {
　 　 if (xmlhttp.readyState == 4 &&
　 　 　xmlhttp.status == 200) {
　 　 　 　 　target.innerHTML = xmlhttp.responseText;
　 }
};
　 xmlhttp.send("");
}