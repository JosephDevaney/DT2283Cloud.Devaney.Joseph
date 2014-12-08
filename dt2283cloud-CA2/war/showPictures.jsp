<%@ page import="java.util.ArrayList" %>
<%@ page import="ie.dit.devaney1.joseph.Image" %>
<% ArrayList<Image> images = (ArrayList) (request.getSession().getAttribute("images")); %>

<html>
	<head>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
		<title>Show Picture Names</title>
	</head>
<body>
	<div class="container" >
	<% 
		for (Image image: images)
		{
			%><p><image src="<%= image.getUrl() %>" alt="<%= image.getName() %>" ><%
				
				if(request.getSession().getAttribute("userID").equals(image.getOwner()) 
					|| request.getSession().getAttribute("userType").equals("admin"))
				{%>
					<form action="/deleteimage" method="POST" >
						<input type="hidden" name="blobkey" value="<%= image.getBlobKey() %>" >
						<input type="submit" value="Delete" >
					</form>	
				<% } %>		
			</p><%
	
		}
		%>
	</div>
	
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</body>
</html>