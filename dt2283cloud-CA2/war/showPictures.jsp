<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.appengine.api.images.Image" %>
<% ArrayList<String> names = (ArrayList)(request.getSession().getAttribute("names")); %>
<% ArrayList<String> images = (ArrayList)(request.getSession().getAttribute("images")); %>

<html>
<head>Show Picture Names</head>
<body>

<% 
	int i = 0;
	for (String name : names)
	{
		String image = images.get(i++);
		%><p><image src="<%= image %>" alt="<%= name %>" ></p><%
		
	}
	%>
</body>
</html>