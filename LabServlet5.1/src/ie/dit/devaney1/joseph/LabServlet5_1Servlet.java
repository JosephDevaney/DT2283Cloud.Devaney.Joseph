package ie.dit.devaney1.joseph;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.*;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class LabServlet5_1Servlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");	//Set the context of the response to plain text
		resp.getWriter().println("Has it changed?");	//Get a writer to print the response
		
		UserService userService = UserServiceFactory.getUserService();	//Create a UserServiceFactory object
		
		Principal myPrincipal = req.getUserPrincipal();	//Get the request User Principal
		String emailAddress = null;	//Create null string reference
		
		String thisURL = req.getRequestURI();	//Get the requested URI
		String loginURL = userService.createLoginURL(thisURL);	//Create a URL to login and store this in a string
		String logoutURL = userService.createLogoutURL(thisURL);	//Create a URL to logout and store this in a string
		
		resp.setContentType("text/html");	//Set the context of the response to html text now
		
		if(myPrincipal == null) //If line 20 returned a null reference instead of a Principal object indicating not logged in
		{
			resp.getWriter().println("<p>You are Not Logged In time</p>");	//Inform User they are not logged in
			resp.getWriter().println("<p>You can <a href=\""+loginURL+"\">sign in here</a>.</p>");	//Give them a link to login at
		} // end if not logged in
		
		if(myPrincipal !=null) //if line 20 returned a reference to an object, indicating user is logged in
		{
			emailAddress = myPrincipal.getName();	//Get the name from the Principal object and store it
			resp.getWriter().println("<p>You are Logged in as (email): "+emailAddress+"</p>");	//Inform user they are logged in
			resp.getWriter().println("<p>You can <a href=\"" + logoutURL +"\">sign out</a>.</p>");	//Give them a link to logout
		} // end if logged in
	}
}
