<%@ page import="java.util.ArrayList" %>
<%@ page import="ie.dit.devaney1.joseph.Image" %>
<% ArrayList<Image> images = (ArrayList<Image>) (request.getSession().getAttribute("images")); %>

<html>
	<head>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
		<title>Show Pictures</title>
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
    
    
	<div class="container" role="main" >
		<div class="page-header">
			<h1>Uploaded Pictures</h1>
		</div>
		<% 
		if (!images.isEmpty())
		{
			for (Image image: images)
			{
				%><div class="row" >
					<div class="col-md-8" >
						<p><img src="<%= image.getUrl() %>" alt="<%= image.getName() %>" class="img-rounded" >	
						</p>
					</div>
					<div class="col-md-4" >
						<dl class="dl-horizontal">
							<dt>Uploaded By</dt>
							<dd><%= image.getOwnerNickName() %></dd>
							
							<dt>Filename</dt>
							<dd><%= image.getName() %></dd>
							
							<dt>Privacy</dt>
							<% String pri = "";
							if (image.getIsPrivate())
							{
								pri = "Private";
							}
							else
							{
								pri = "Public";
							}%>
							<dd><%= pri %></dd>
						
						<%
						if (request.getSession().getAttribute("session") != null)
						{
							if(request.getSession().getAttribute("userID").equals(image.getOwner()) 
								|| request.getSession().getAttribute("userType").equals("admin"))
							{%>
							<dd>
								<form action="/deleteimage" method="POST" >
									<input type="hidden" name="blobkey" value="<%= image.getBlobKey() %>" >
									<input type="submit" class="btn btn-danger" value="Delete" >
								</form>
							</dd>
							<dd>
								<form action="/serve" method="POST" >
									<input type="hidden" name="blobkey" value="<%= image.getBlobKey() %>" >
									<input type="submit" class="btn btn-info" value="Download" >
								</form>
							</dd>
						<% }
						}%>	
						</dl>
					</div>
				</div>
		<% }
		}%>
	</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</body>
</html>