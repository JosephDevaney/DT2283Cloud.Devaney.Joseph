<%@	page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@	page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%	BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService(); %>

<html>
 	<head>
 		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
 		<title>Upload Test</title>
 	</head>
 	<body>
	 	<form action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post" enctype="multipart/form-data">
			 <input type="text" name="foo">
			 <input type="file" name="myFile">
			 <input type="checkbox" name"isPrivate" value"private" 
			 	<%	if (request.getSession().getAttribute("userType").toString() != null)
			 		{
			 			%>checked<%
			 		}
			 	%> >Private </br>
			 <input type="submit" value="Submit">
		 </form>
	 </body>
</html>