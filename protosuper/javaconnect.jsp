<%@ page contentType="text/html; charset=utf-8" %>

<html>
<head>
<title>result</title>
</head>
<body>
<% String str = request.getParameter("test");
		out.println("お前が入力したのは");
		out.println(str);
		out.println("だ");
 %>
</body>
</html>