package ie.dit.devaney1.joseph;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Guides extends HttpServlet
{
	private static final long	serialVersionUID	= 1L;
	
	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * 
	 * Decides the users status and forwards to relevent User Guide
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws IOException
	{
		HttpSession session = req.getSession();
		
		String userType = "";
		String guide = "";
		
		if (session.getAttribute("userType") == null)
		{
			guide = "user_guides/guest_guide.html";
		}
		else
		{
			userType = (String) session.getAttribute("userType");
		}
		if (userType.equals("admin"))
		{
			guide = "user_guides/admin_guide.html";
		}
		else if (userType.equals("loggedIn"))
		{
			guide = "user_guides/member_guide.html";
		}
		res.sendRedirect(guide);
	}
	
}
