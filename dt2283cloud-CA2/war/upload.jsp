<%@	page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@	page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%	BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService(); %>
<%
	if(request.getSession().getAttribute("session") == null)
	{
		response.sendRedirect("/picturebox");
	}
%>

<html>
 	<head>
 		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
 		<title>Upload Test</title>
 	</head>
 	<body>
 	<!-- Bootstrap Styling using in this document can be found www.getbootstrap.com/getting-started/ -->

		<nav class="navbar navbar-static-top navbar-inverse" role="navigation">
	      <div class="container">
	        <div class="navbar-header">
	          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
	            <span class="sr-only">Toggle navigation</span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	          </button>
	          <a class="navbar-brand" href="/getblobs">Picture Box</a>
	        </div>
	        <div id="navbar" class="collapse navbar-collapse">
	          <ul class="nav navbar-nav">
	            <li class="active"><a href="#">My Images</a></li>
	            <li><a href="/upload.jsp">Upload</a></li>
	            <li><a href="/picturebox">Login/Logout</a></li>
	          </ul>
	        </div><!-- /.nav-collapse -->
	      </div><!-- /.container -->
	    </nav><!-- /.navbar -->
    
	    <div class="container" >
	    	<div class="row" >
	    		<div class="col-md-8" >
		    		<form action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post" enctype="multipart/form-data">
	    			<div class="input-group input-group-lg" >
	    				<span class="input-group-addon" >
	    					<input type="checkbox" name="isPrivate" value"private" 
							 	<%	if (request.getSession().getAttribute("userType").toString() == "admin")
							 		{
							 			%>checked<%
							 		}
							 	%> >Private
	    				</span>
	    				<input type="text" name="foo" class="form-control" >
	    				<span class="input-group-addon" >
	    					<input type="file" name="myFile">
	    				</span>
	    			</div>
	    			<input type="submit" value="Upload" class="btn btn-primary btn-lg" >
	    			</form>
	    		</div>
	    	</div>
	    </div>
	 	 
	</body>
</html>