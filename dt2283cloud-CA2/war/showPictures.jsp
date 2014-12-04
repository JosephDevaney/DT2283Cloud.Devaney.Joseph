<%@ page import="java.util.ArrayList" %>

<html>
<head>Show Picture Names</head>
<body>

<% 
	
	ArrayList<String> names = (ArrayList)(request.getSession().getAttribute("names"));
	for (String name : names)
	{
		%><p><%= name %></p><%
	}
	%>
</body>
</html>