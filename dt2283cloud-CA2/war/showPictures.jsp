<%@ page import="java.util.ArrayList" %>
<%@ page import="ie.dit.devaney1.joseph.Image" %>
<% ArrayList<Image> images = (ArrayList) (request.getSession().getAttribute("images")); %>

<html>
<head><title>Show Picture Names</title></head>
<body>

<% 
	for (Image image: images)
	{
		%><p><image src="<%= image.getUrl() %>" alt="<%= image.getName() %>" ><%
			
			if(request.getSession().getAttribute("userID").equals(image.getOwner()) 
				|| request.getSession().getAttribute("userType").equals("admin"))
			{%>
				<form action="deleteImage" method="POST" >
					<input type="hidden" name="blobkey" value="<%= image.getBlobKey() %>" >
					<input type="submit" value="Delete" >
				</form>	
			<% } %>		
		</p><%

	}
	%>
</body>
</html>