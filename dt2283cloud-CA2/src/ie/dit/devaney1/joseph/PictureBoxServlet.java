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
	/** 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * 
	 * Allows a user to login/logout
	 * 
	 * <p>
	 * This method handles both the login and logout operations for the user.
	 * If the user is not logged in when they click Login/Logout then this will redirect them to a login page, which returns to this page.
	 * Then this servlet will redirect to /getblobs and on to showPictures.jsp
	 * 
	 * </p>
	 * If the user is logged in when they click for this servlet, the session variables will be removed.
	 * Set a session variable to "logging out". Servlet will call the logout URL.
	 * The logout URL will redirect back to this servlet and with the session set to "logging out" it will redirect to "/getblobs"
	 * <p>
	 * 
	 * @param req HttpServletRequest object
	 * @param res HttpServletResponse object
	 * 
	 * @throws IOException
	 * 
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException
	{
		HttpSession session = req.getSession();
		User user = null;
		UserService userService = UserServiceFactory.getUserService(); 	

		Principal myPrincipal = req.getUserPrincipal(); 				
		String emailAddress = null; 

		String thisURL = req.getRequestURI(); 
		String loginURL = userService.createLoginURL(thisURL); 
		String logoutURL = userService.createLogoutURL(thisURL); 

		resp.setContentType("text/html"); 
				
		if (session.getAttribute("loggingOut") != null)	// User has just logged out
		{
			session.removeAttribute("loggingOut");
			resp.sendRedirect("/getblobs");
			return;
		}
		else if (myPrincipal == null) // User not logged in and is trying to
		{
			resp.sendRedirect(loginURL);
			return;
		}

		if (myPrincipal != null && session.getAttribute("session") == null) // User is Logging in; Set session variables
		{
			session.setAttribute("session", session.getId());
			
			user = userService.getCurrentUser();
			emailAddress = user.getEmail();
			
			if (emailAddress.equals("joe.devaney1@gmail.com") || emailAddress.equals("mark.deegan@dit.ie")) // Admin users
			{
				session.setAttribute("userType", "admin");
			}
			else	// Regular Users
			{
				session.setAttribute("userType", "loggedIn");
			}
			session.setAttribute("userID", user.getUserId());
			resp.sendRedirect("/getblobs");
			return;
		} // end if logged in
		else if (myPrincipal != null && session.getAttribute("session") != null)	// User is logged in and wants to logout
		{
			// Remove Session variables
			session.removeAttribute("session");
			session.removeAttribute("userType");
			session.removeAttribute("userID");
			
			// Set Session variable to tell this service user is logging out when it redirects here
			session.setAttribute("loggingOut", true);
			resp.sendRedirect(logoutURL);
			return;
		}
	}
}
