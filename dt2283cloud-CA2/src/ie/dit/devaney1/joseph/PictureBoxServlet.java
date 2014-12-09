package ie.dit.devaney1.joseph;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class PictureBoxServlet extends HttpServlet
{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException
	{
		HttpSession session = req.getSession();
		User user = null;
		UserService userService = UserServiceFactory.getUserService(); 	// Create a UserServiceFactory object

		Principal myPrincipal = req.getUserPrincipal(); 				// Get the request User Principal
		String emailAddress = null; // Create null string reference

		String thisURL = req.getRequestURI(); // Get the requested URI
		String loginURL = userService.createLoginURL(thisURL); // Create a URL to login and store this in a string
		String logoutURL = userService.createLogoutURL(thisURL); // Create a URL to logout and store this in a string

		resp.setContentType("text/html"); // Set the context of the response to html text now
				
		if (session.getAttribute("loggingOut") != null)
		{
			session.removeAttribute("loggingOut");
			resp.sendRedirect("/getblobs");
			return;
		}
		else if (myPrincipal == null) // If line 20 returned a null reference instead of a Principal object indicating not logged in
		{
			resp.sendRedirect(loginURL);
			return;
		} // end if not logged in

		if (myPrincipal != null && session.getAttribute("session") == null) // if line 20 returned a reference to an object, indicating user is logged in
		{
			session.setAttribute("session", session.getId());
			
			user = userService.getCurrentUser();
			emailAddress = user.getEmail();
			
			if (emailAddress.equals("joe.devaney1@gmail.com") || emailAddress.equals("mark.deegan@dit.ie"))
			{
				session.setAttribute("userType", "admin");
			}
			else
			{
				session.setAttribute("userType", "loggedIn");
			}
			session.setAttribute("userID", user.getUserId());
			resp.sendRedirect("/getblobs");
			return;
		} // end if logged in
		else if (myPrincipal != null && session.getAttribute("session") != null)
		{
			session.removeAttribute("session");
			session.removeAttribute("userType");
			session.removeAttribute("userID");
			session.setAttribute("loggingOut", true);
			resp.sendRedirect(logoutURL);
			return;
		}
	}
}
