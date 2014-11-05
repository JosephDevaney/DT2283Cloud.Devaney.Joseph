package ie.dit.devaney1.joseph;

import java.io.IOException;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class CloudLabTest1Servlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String aString = req.getParameter("A");
		String bString = req.getParameter("B");
		String cString = req.getParameter("C");
		String dString = req.getParameter("D");
		
		if(checkParameters(aString, bString, cString, dString) == false)
		{
			aString = getServletConfig().getInitParameter("A");
			bString = getServletConfig().getInitParameter("B");
			cString = getServletConfig().getInitParameter("C");
			dString = getServletConfig().getInitParameter("D");
		}
		
		
		
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}

	private boolean checkParameters(String a, String b, String c, String d) 
	{
		int num;
		if (a == null || a == "")
		{
			return false;
		}
		if (b == null || b == "")
		{
			return false;
		}
		if (c == null || c == "")
		{
			return false;
		}
		if (d == null || d == "")
		{
			return false;
		}
		
		try {
			num = Integer.parseInt(a);
			num = Integer.parseInt(b);
			num = Integer.parseInt(c);
			num = Integer.parseInt(d);
			
			
		} catch (NumberFormatException e) {
			return false;
		}
		
		return true;
	}
}
